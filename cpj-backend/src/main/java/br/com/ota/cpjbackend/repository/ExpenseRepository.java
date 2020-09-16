package br.com.ota.cpjbackend.repository;

import br.com.ota.cpjbackend.model.Expense;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends BaseRepository<Expense, Long> {

    @Query(value = "select e from Expense e where e.refundable = true and e.payed = false and extract('month' from e.date) = extract( 'month' from now())")
    List<Expense> findAllByDateMonth();

    List<Expense> findAllByContractId(Long contractId);
}
