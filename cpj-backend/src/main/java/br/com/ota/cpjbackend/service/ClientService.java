package br.com.ota.cpjbackend.service;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.exception.AppException;
import br.com.ota.cpjbackend.model.Client;
import br.com.ota.cpjbackend.model.Contract;
import br.com.ota.cpjbackend.repository.ClientRepository;
import br.com.ota.cpjbackend.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final MessagePropertie messagePropertie;
    private final ClientRepository clientRepository;
    private final ContractRepository contractRepository;

    public void save(Client client) {
        clientRepository.save(client);
    }

    public List<Client> list() {
        return clientRepository.findAll(Sort.by("clientName"));
    }

    public Client load(String id) throws AppException {
        return clientRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new AppException(messagePropertie.getMessage("message.model.not.found", "model.client")));
    }

    public void remove(Long id) throws AppException {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new AppException(messagePropertie.getMessage("message.model.not.found", "model.client")));
        List<Contract> contractsByContractors = contractRepository.findAllByContractorsContains(client);
        if (CollectionUtils.isEmpty(contractsByContractors)) {
            clientRepository.delete(client);
        } else {
            throw new AppException(messagePropertie.getMessage("message.error.client.with.contract"));
        }

    }

}
