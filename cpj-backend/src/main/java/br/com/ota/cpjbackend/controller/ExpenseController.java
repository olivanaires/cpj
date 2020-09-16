package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.exception.AppException;
import br.com.ota.cpjbackend.model.Expense;
import br.com.ota.cpjbackend.model.vo.MessageResponse;
import br.com.ota.cpjbackend.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;
    private final MessagePropertie messagePropertie;

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody Expense expense) {
        expenseService.save(expense);
        return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.created.success", "model.expense")));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<Expense> allExpenses = expenseService.list();
        return ResponseEntity.ok(allExpenses);
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<?> load(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(expenseService.load(id));
        } catch (AppException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/listByContract/{contractId}")
    public ResponseEntity<?> listByContract(@PathVariable Long contractId) {
        List<Expense> allByCoAndContractId = expenseService.listByContract(contractId);
        return ResponseEntity.ok(allByCoAndContractId);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<MessageResponse> remove(@PathVariable Long id) {
        expenseService.remove(id);
        return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.removed.success", "model.expense")));
    }

}
