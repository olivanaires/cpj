package br.com.ota.cpjbackend.repository;

import br.com.ota.cpjbackend.model.Payment;
import br.com.ota.cpjbackend.model.vo.PaymentResponse;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends BaseRepository<Payment, Long>  {

    @Query(value = "select new br.com.ota.cpjbackend.model.vo.PaymentResponse(p.description, p.date, p.paymentValue, true) from Payment p where p.contract.id = ?1")
    List<PaymentResponse> findAllByContractId(Long contractId);

}
