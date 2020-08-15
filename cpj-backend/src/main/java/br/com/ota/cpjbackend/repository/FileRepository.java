package br.com.ota.cpjbackend.repository;

import br.com.ota.cpjbackend.model.File;

public interface FileRepository extends BaseRepository<File, Long> {

    void deleteById( Long id );

}
