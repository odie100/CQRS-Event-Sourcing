package com.unidev.cqrs.commonapi.events;

import lombok.Getter;

@Getter
public class AccountCreditedEvent extends BaseEvent<String> {
    private final double amount;
    private final String currency;

    public AccountCreditedEvent(String id, double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }

}
