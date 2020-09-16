package br.com.ota.cpjbackend.service;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.exception.AppException;
import br.com.ota.cpjbackend.model.Expense;
import br.com.ota.cpjbackend.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final MessagePropertie messagePropertie;

    public void save(Expense expense) {
        expenseRepository.save(expense);
    }

    public List<Expense> list() {
        return expenseRepository.findAll();
    }

    public Expense load(Long id) throws AppException {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new AppException(messagePropertie.getMessage("message.model.not.found", "model.expense")));
    }

    public List<Expense> listByContract(Long contractId) {
        return expenseRepository.findAllByContractId(contractId);
    }

    public void remove(Long id) {
        expenseRepository.deleteById(id);
    }
}
