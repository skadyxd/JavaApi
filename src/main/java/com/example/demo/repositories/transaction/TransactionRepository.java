package com.example.demo.repositories.transaction;

import com.example.demo.model.Transaction;

import java.util.List;
public interface TransactionRepository {
    List<Transaction> getTransactions();
    Transaction getTransaction(int transactionId);
    void createTransaction(Transaction transaction);
    void updateTransaction(Transaction transaction, int transactionId);
    void deleteTransaction(int transactionId);
}
