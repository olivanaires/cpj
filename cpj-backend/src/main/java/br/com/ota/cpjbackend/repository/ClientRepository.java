package br.com.ota.cpjbackend.repository;

import br.com.ota.cpjbackend.model.Client;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends BaseRepository<Client, Long> {
}
