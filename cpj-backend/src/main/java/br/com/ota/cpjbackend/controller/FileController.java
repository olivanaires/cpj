package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.model.Contract;
import br.com.ota.cpjbackend.model.File;
import br.com.ota.cpjbackend.model.vo.MessageResponse;
import br.com.ota.cpjbackend.repository.ContractRepository;
import br.com.ota.cpjbackend.repository.FileRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final MessagePropertie messagePropertie;
    private final FileRepository fileRepository;
    private final ContractRepository contractRepository;


    @PostMapping("/uploadContractFile")
    public ResponseEntity<MessageResponse> uploadContractFile(@RequestParam("file") MultipartFile fileRequest, @RequestParam("contractId") Long contractId) {
        try {
            Contract contract = contractRepository.findById(contractId)
                    .orElseThrow(() -> new NotFoundException(messagePropertie.getMessage("message.model.not.found", "model.contract")));

            File file;
            if (Objects.isNull(contract.getFileId())) {
                file = new File(fileRequest.getOriginalFilename(), fileRequest.getContentType(), fileRequest.getBytes());
                file = fileRepository.save(file);
                contract.setFileId(file.getId());
                contractRepository.save(contract);
            } else {
                file = fileRepository.findById(contract.getFileId())
                        .orElseThrow(() -> new NotFoundException(messagePropertie.getMessage("message.model.not.found", "model.file")));
                file.setName(fileRequest.getOriginalFilename());
                file.setMimetype(fileRequest.getContentType());
                file.setContent(fileRequest.getBytes());
                fileRepository.save(file);
            }

            return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.upload.success")));
        } catch (IOException | NotFoundException ex) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(ex.getMessage()));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<File> files = fileRepository.findAll();
        return ResponseEntity.ok(files);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<?> download(@PathVariable(required = true, value = "fileId") Long fileId) {

        try {
            File file = fileRepository.findById(fileId)
                    .orElseThrow(() -> new NotFoundException(messagePropertie.getMessage("message.model.not.found", "model.file")));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename=\"" + file.getName() + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(file.getContent().length)
                    .body(file.getContent());
        } catch (NotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
