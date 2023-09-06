package com.unidev.cqrs.query.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unidev.cqrs.commonapi.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class Account {
    @Id
    private String id;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String currency;
    @OneToMany(mappedBy = "account")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Operation> operations;
}
