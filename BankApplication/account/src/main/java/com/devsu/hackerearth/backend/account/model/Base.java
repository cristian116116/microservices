package com.devsu.hackerearth.backend.account.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Data
@SuperBuilder
@NoArgsConstructor
public class Base {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
}
