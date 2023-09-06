package com.unidev.cqrs.commonapi.events;

import lombok.Getter;

@Getter
public class AccountCreatedEvent extends BaseEvent<String>{
    private final double initial_balance;
    private final String currency;

    public AccountCreatedEvent(String id, double initial_balance, String currency) {
        super(id);
        this.initial_balance = initial_balance;
        this.currency = currency;
    }

}
