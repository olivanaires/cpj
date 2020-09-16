package br.com.ota.cpjbackend.repository;

import br.com.ota.cpjbackend.model.Honorary;
import br.com.ota.cpjbackend.model.vo.PaymentResponse;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface HonoraryRepository extends BaseRepository<Honorary, Long> {

    @Query(value = "select h from Honorary h where h.payed = false and extract('month' from h.date) = extract( 'month' from now())")
    List<Honorary> findAllByDateMonth();

    List<Honorary> findAllByContractId(Long contractId);

    @Query(value = "select new br.com.ota.cpjbackend.model.vo.PaymentResponse(t) from Honorary t where t.contract.id = ?1")
    List<PaymentResponse> findAllPaymentsByContractId(Long contractId);
}
