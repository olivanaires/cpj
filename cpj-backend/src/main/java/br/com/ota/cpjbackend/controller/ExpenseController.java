package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.model.Contract;
import br.com.ota.cpjbackend.model.Expense;
import br.com.ota.cpjbackend.model.vo.MessageResponse;
import br.com.ota.cpjbackend.repository.ContractRepository;
import br.com.ota.cpjbackend.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseRepository expenseRepository;
    private final ContractRepository contractRepository;
    private final MessagePropertie messagePropertie;

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody Expense expense) {
        expenseRepository.save(expense);
        return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.created.success", "model.expense")));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<Expense> allExpenses = expenseRepository.findAll();
        return ResponseEntity.ok(allExpenses);
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<?> load(@PathVariable Long id) {
        Expense expense = expenseRepository.getOne(id);
        return ResponseEntity.ok(expense);
    }

    @GetMapping("/listByContract/{contractId}")
    public ResponseEntity<?> listByContract(@PathVariable Long contractId) {
        List<Expense> allByCoAndContractId = expenseRepository.findAllByContractId(contractId);
        return ResponseEntity.ok(allByCoAndContractId);
    }

}
