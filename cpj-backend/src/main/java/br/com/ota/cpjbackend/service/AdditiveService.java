package br.com.ota.cpjbackend.service;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.exception.AppException;
import br.com.ota.cpjbackend.model.Additive;
import br.com.ota.cpjbackend.repository.AdditiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdditiveService {

    private final AdditiveRepository additiveRepository;
    private final MessagePropertie messagePropertie;

    public void create(Additive additive) {
        additiveRepository.save(additive);
    }

    public Additive load(String id) throws AppException {
        return additiveRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new AppException(messagePropertie.getMessage("message.model.not.found", "model.additive")));
    }

    public List<Additive> list() {
        return additiveRepository.findAll();
    }

    public void remove(Long id) throws AppException {
        Additive additive = load(id.toString());
        additiveRepository.delete(additive);
    }

    public List<Additive> fidByContractId(Long contractId) {
        return additiveRepository.findByContractIdOrderBySignatureDate(contractId);
    }

}
