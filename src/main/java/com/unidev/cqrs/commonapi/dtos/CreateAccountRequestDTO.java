package com.unidev.cqrs.commonapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class CreateAccountRequestDTO {
    private double initial_balance;
    private String currency;
}
