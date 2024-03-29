package br.com.ota.cpjbackend.repository;

import br.com.ota.cpjbackend.model.Client;
import br.com.ota.cpjbackend.model.Contract;
import br.com.ota.cpjbackend.model.Lawyer;
import br.com.ota.cpjbackend.model.enums.PaymentType;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractRepository extends BaseRepository<Contract, Long> {

    List<Contract> findAllByPaymentTypeIsIn(PaymentType... paymentTypes);

    List<Contract> findAllByContractorsContains( Client contractor );

    List<Contract> findAllByHiredContains( Lawyer lawyer );

    @Query(value = "select cont from Contract cont where (cont.endDate > now() or cont.endDate is null) and (cont.paymentType in ('MONTHLY', 'MONTHLY_HONORARY') or (cont.paymentType in ('YEARLY', 'YEARLY_HONORARY') and extract( 'month' from cont.paymentDate) = extract( 'month' from now()))) and cont.id not in (select pay.contract.id from Payment pay where extract( 'month' from pay.date) = extract( 'month' from now()))")
    List<Contract> findAllWithPaymentForCurrentlyMonth();

    @Query(value = "select cont from Contract cont where extract( 'month' from cont.endDate) = extract( 'month' from now()) and extract( 'year' from cont.endDate) = extract( 'year' from now())")
    List<Contract> findAllWithEndDateForCurrentlyMonth();

}
