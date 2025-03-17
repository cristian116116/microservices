package com.devsu.hackerearth.backend.account.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
public class Account extends Base {
    private String number;
    private String type;
    private double initialAmount;
    private boolean isActive;

    @Column(name = "client_id")
    private Long clientId;
}
