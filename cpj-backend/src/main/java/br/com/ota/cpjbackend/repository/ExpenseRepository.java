package br.com.ota.cpjbackend.repository;

import br.com.ota.cpjbackend.model.Expense;

import java.util.List;

public interface ExpenseRepository extends BaseRepository<Expense, Long> {

    List<Expense> findAllByContractId(Long contractId);
}
