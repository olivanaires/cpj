package br.com.ota.cpjbackend.repository;

import br.com.ota.cpjbackend.model.Additive;

import java.util.List;

public interface AdditiveRepository extends BaseRepository<Additive, Long> {

    List<Additive> findByContractIdOrderBySignatureDate(Long contractId);

}
