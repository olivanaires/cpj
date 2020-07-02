package br.com.ota.cpjbackend.repository;

import br.com.ota.cpjbackend.model.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends BaseRepository<Client, Long> {

    List<Client> findAllByCpfCnpjIn(List<String> cpfCnpjList);

}
