package com.devsu.hackerearth.backend.account.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.devsu.hackerearth.backend.account.model.Account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AccountDto {

	@NotNull(message = "ID cannot be null")
	private Long id;

	@NotBlank(message = "Number cannot be blank")
	@Size(min = 6, max = 20, message = "Number must be between 6 and 20 characters")
	private String number;

	@NotBlank(message = "Type cannot be blank")
	@Pattern(regexp = "Savings|Checking", message = "Type must be 'Savings' or 'Checking'")
	private String type;

	@PositiveOrZero(message = "Initial amount must be zero or positive")
	private double initialAmount;

	private boolean isActive;

	@NotNull(message = "Client ID cannot be null")
	private Long clientId;

	public static AccountDto fromEntity(Account account) {
		return AccountDto.builder()
				.id(account.getId())
				.number(account.getNumber())
				.type(account.getType())
				.initialAmount(account.getInitialAmount())
				.isActive(account.isActive())
				.clientId(account.getClientId())
				.build();
	}
}
