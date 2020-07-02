package br.com.ota.cpjbackend.repository;

import br.com.ota.cpjbackend.model.Lawyer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LawyerRepository extends BaseRepository<Lawyer, Long> {

    boolean existsByOabNumberAndIdNot(String oabNumber, Long id);

    boolean existsByOabNumber(String oabNumber);

    List<Lawyer> findAllByOabNumberIn(List<String> oabNumberList);


}
