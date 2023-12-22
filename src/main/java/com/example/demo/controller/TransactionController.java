package com.example.demo.controller;

import com.example.demo.model.Transaction;
import com.example.demo.repositories.transaction.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("demo/transactions")
public class TransactionController {

    private final TransactionRepository repository;

    @Autowired
    public TransactionController(TransactionRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Transaction> getTransactions() {
        return repository.getTransactions();
    }

    @GetMapping("/{transaction_id}")
    public Transaction getTransaction(@PathVariable("transaction_id") int transactionId) {
        return repository.getTransaction(transactionId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createTransaction(@RequestBody Transaction transaction) {
        repository.createTransaction(transaction);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{transaction_id}")
    public void updateTransaction(@RequestBody Transaction transaction, @PathVariable("transaction_id") int transactionId) {
        repository.updateTransaction(transaction, transactionId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{transaction_id}")
    public void deleteTransaction(@PathVariable("transaction_id") int transactionId) {
        repository.deleteTransaction(transactionId);
    }
}
