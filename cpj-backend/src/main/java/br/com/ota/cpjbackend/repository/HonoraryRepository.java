package br.com.ota.cpjbackend.repository;

import br.com.ota.cpjbackend.model.Honorary;
import br.com.ota.cpjbackend.model.vo.PaymentResponse;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface HonoraryRepository extends BaseRepository<Honorary, Long> {

    List<Honorary> findAllByContractId(Long contractId);

    @Query(value = "select new br.com.ota.cpjbackend.model.vo.PaymentResponse(t) from Honorary t where t.contract.id = ?1")
    List<PaymentResponse> findAllPaymentsByContractId(Long contractId);
}
