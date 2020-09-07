package br.com.ota.cpjbackend.repository;

import br.com.ota.cpjbackend.model.File;
import br.com.ota.cpjbackend.model.vo.FileResponse;

import java.util.List;

public interface FileRepository extends BaseRepository<File, Long> {

    void deleteById( Long id );

    List<FileResponse> findAllByContractId(Long contractId);

}
