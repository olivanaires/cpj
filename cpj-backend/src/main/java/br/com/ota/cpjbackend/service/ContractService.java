package br.com.ota.cpjbackend.service;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.exception.AppException;
import br.com.ota.cpjbackend.model.Client;
import br.com.ota.cpjbackend.model.Contract;
import br.com.ota.cpjbackend.model.Lawyer;
import br.com.ota.cpjbackend.model.vo.ContractRequest;
import br.com.ota.cpjbackend.repository.ClientRepository;
import br.com.ota.cpjbackend.repository.ContractRepository;
import br.com.ota.cpjbackend.repository.FileRepository;
import br.com.ota.cpjbackend.repository.LawyerRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final MessagePropertie messagePropertie;
    private final ContractRepository contractRepository;
    private final ClientRepository clientRepository;
    private final LawyerRepository lawyerRepository;
    private final FileRepository fileRepository;

    public Long create(ContractRequest contractRequest) throws AppException {

        try {
            List<Client> customers = clientRepository.findAllByCpfCnpjIn(contractRequest.getContractors());
            List<Lawyer> lawyers = lawyerRepository.findAllByOabNumberIn(contractRequest.getHired());

            Contract contract;
            if (Objects.isNull(contractRequest.getId())) {
                contract = contractRequest.toContract(new Contract());
            } else {
                contract = contractRepository.findById(contractRequest.getId())
                        .orElseThrow(() -> new NotFoundException(messagePropertie.getMessage("message.model.not.found", "model.contract")));
                contractRequest.toContract(contract);
            }

            contract.setHired(new HashSet<>(lawyers));
            contract.setContractors(new HashSet<>(customers));
            Contract entity = contractRepository.save(contract);
            return entity.getId();
        } catch (Exception e) {
            throw new AppException(messagePropertie.getMessage("message.error"));
        }
    }

    public List<Contract> list() {
        return contractRepository.findAll();
    }

    public Contract load(String id) throws AppException {
        return contractRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new AppException(messagePropertie.getMessage("message.model.not.found", "model.contract")));
    }

    public void remove(Long id) throws AppException {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new AppException(messagePropertie.getMessage("message.model.not.found", "model.contract")));

        if (Objects.nonNull(contract.getFileId())) {
            fileRepository.deleteById(contract.getFileId());
        }

        contractRepository.delete(contract);
    }

    public List<Contract> contractsWithPaymentForCurrentlyMonth() {
        return contractRepository.findAllWithPaymentForCurrentlyMonth();
    }
}
