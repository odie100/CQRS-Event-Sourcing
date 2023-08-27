package com.unidev.cqrs.commonapi.commands;

public class DebitAccountCommand extends BaseCommand<String>{
    private final double amount;
    private final String currency;

    public DebitAccountCommand(String id, double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
