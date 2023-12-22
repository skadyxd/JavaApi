package com.example.demo.model;

import java.math.BigDecimal;
import java.util.Date;
public record Transaction(int transactionId, String title, Date publicationDate, BigDecimal amount) {
}
