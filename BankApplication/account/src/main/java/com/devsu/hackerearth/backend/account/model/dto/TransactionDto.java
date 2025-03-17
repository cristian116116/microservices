package com.devsu.hackerearth.backend.account.model.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import com.devsu.hackerearth.backend.account.model.Transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TransactionDto {

	@NotNull(message = "ID cannot be null")
	private Long id;

	@NotNull(message = "Date cannot be null")
	@PastOrPresent(message = "Date must be in the past or present")
	private Date date;

	@NotBlank(message = "Type cannot be blank")
	@Pattern(regexp = "Deposit|Withdrawal", message = "Type must be 'Deposit' or 'Withdrawal'")
	private String type;

	@Positive(message = "Amount must be positive")
	private double amount;

	@NotNull(message = "Balance cannot be null")
	private double balance;

	@NotNull(message = "Account ID cannot be null")
	private Long accountId;

	public static TransactionDto fromEntity(Transaction transaction) {
		return TransactionDto.builder()
				.id(transaction.getId())
				.date(transaction.getDate())
				.type(transaction.getType())
				.amount(transaction.getAmount())
				.balance(transaction.getBalance())
				.accountId(transaction.getAccountId())
				.build();
	}
}
