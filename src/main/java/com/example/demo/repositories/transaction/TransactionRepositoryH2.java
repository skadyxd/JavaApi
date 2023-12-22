package com.example.demo.repositories.transaction;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepositoryH2 implements TransactionRepository {

    private static final String CREATE = """
            INSERT INTO TRANSACTIONS (TRANSACTION_ID, TITLE, PUBLICATION_DATE, AMOUNT)
            VALUES (:transactionId, :title, :publicationDate, :amount)
            """;

    private static final String UPDATE = """
        UPDATE TRANSACTIONS
        SET TITLE = :title,
            PUBLICATION_DATE = :publicationDate,
            AMOUNT = :amount
        WHERE TRANSACTION_ID = :transactionId
        """;


    private final RowMapper<Transaction> rowMapper = new DataClassRowMapper<>(Transaction.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public TransactionRepositoryH2(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Transaction> getTransactions() {
        return jdbcTemplate.query("SELECT * FROM TRANSACTIONS", rowMapper);
    }

    public Transaction getTransaction(int transactionId) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM TRANSACTIONS WHERE TRANSACTION_ID = ?", rowMapper, transactionId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Transaction with id = [" + transactionId + "] not found", e);
        }
    }

    public void createTransaction(Transaction transaction) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(transaction);
        namedParameterJdbcTemplate.update(CREATE, parameterSource);
    }

    public void updateTransaction(Transaction transaction, int transactionId) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(transaction);
        namedParameterJdbcTemplate.update(UPDATE, parameterSource);
    }

    public void deleteTransaction(int transactionId) {
        jdbcTemplate.update("DELETE FROM TRANSACTIONS WHERE TRANSACTION_ID  = ?", transactionId);
    }
}
