package com.devsu.hackerearth.backend.client.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientDto {

	private Long id;

	@NotBlank(message = "DNI cannot be blank")
	@Pattern(regexp = "\\d{7,10}", message = "DNI must be between 7 and 10 digits")
	private String dni;

	@NotBlank(message = "Name cannot be blank")
	@Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
	private String name;

	@NotBlank(message = "Password cannot be blank")
	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	private String password;

	@NotBlank(message = "Gender cannot be blank")
	@Pattern(regexp = "Male|Female|Other", message = "Gender must be 'Male', 'Female', or 'Other'")
	private String gender;

	@Min(value = 18, message = "Age must be at least 18")
	@Max(value = 100, message = "Age must be at most 100")
	private int age;

	@NotBlank(message = "Address cannot be blank")
	@Size(max = 100, message = "Address cannot exceed 100 characters")
	private String address;

	@NotBlank(message = "Phone cannot be blank")
	@Pattern(regexp = "\\d{10}", message = "Phone must be exactly 10 digits")
	private String phone;

	@NotNull(message = "Active status must be specified")
	private boolean isActive;

}
