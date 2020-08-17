package br.com.ota.cpjbackend.repository;

import br.com.ota.cpjbackend.model.Client;
import br.com.ota.cpjbackend.model.Contract;
import br.com.ota.cpjbackend.model.Lawyer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ContractRepository extends BaseRepository<Contract, Long> {

    List<Contract> findAllByContractorsContains( Client contractor );

    List<Contract> findAllByHiredContains( Lawyer lawyer );

    @Query(value = "select cont from Contract cont where cont.endDate > now() and (cont.paymentType = 'MONTHLY' or (cont.paymentType = 'YEARLY' and extract( 'month' from cont.paymentDate) = extract( 'month' from now())))")
    List<Contract> findAllWithPaymentForCurrentlyMonth();

    @Query(nativeQuery = true, value = "select cont from contracts cont where end_date > now() and (payment_type = 'MONTHLY' or (payment_type = 'YEARLY' and extract('month' from payment_date) = ?1))")
    List<Contract> findAllWithPaymentByMonth(int month);

}
