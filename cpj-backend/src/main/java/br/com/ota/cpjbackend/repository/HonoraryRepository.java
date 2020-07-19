package br.com.ota.cpjbackend.repository;

import br.com.ota.cpjbackend.model.Honorary;

import java.util.List;


public interface HonoraryRepository extends BaseRepository<Honorary, Long> {

    List<Honorary> findAllByContractId(Long contractId);
}
