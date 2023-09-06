package com.unidev.cqrs.commonapi.events;

import com.unidev.cqrs.commonapi.enums.AccountStatus;
import lombok.Getter;

@Getter
public class  AccountCreatedEvent extends BaseEvent<String>{
    private final double initial_balance;
    private final String currency;
    private final AccountStatus status;

    public AccountCreatedEvent(String id, double initial_balance, String currency, AccountStatus status) {
        super(id);
        this.initial_balance = initial_balance;
        this.currency = currency;
        this.status = status;
    }

}
