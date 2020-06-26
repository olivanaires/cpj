package br.com.ota.cpjbackend.repository;

import br.com.ota.cpjbackend.model.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends BaseRepository<Client, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByCnpj(String cnpj);

    @Query("select c from Client c where c.cpf in ?1 or c.cnpj in ?1")
    List<Client> findAllByCpfInOrCnpjIn(List<String> cpfCnpjList);

}
