package br.com.ota.cpjbackend.repository;

import br.com.ota.cpjbackend.model.Client;
import br.com.ota.cpjbackend.model.Contract;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends BaseRepository<Contract, Long> {

}
