package com.unidev.cqrs.commonapi.commands;

import lombok.Getter;

@Getter
public class CreateAccountCommand extends BaseCommand<String>{
    private final double initial_balance;
    private final String currency;

    public CreateAccountCommand(String id, double initial_balance, String currency) {
        super(id);
        this.initial_balance = initial_balance;
        this.currency = currency;
    }
}
