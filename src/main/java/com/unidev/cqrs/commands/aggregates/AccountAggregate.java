package com.unidev.cqrs.commands.aggregates;

import com.unidev.cqrs.commonapi.commands.CreateAccountCommand;
import com.unidev.cqrs.commonapi.commands.CreditAccountCommand;
import com.unidev.cqrs.commonapi.commands.DebitAccountCommand;
import com.unidev.cqrs.commonapi.enums.AccountStatus;
import com.unidev.cqrs.commonapi.events.AccountActivatedEvent;
import com.unidev.cqrs.commonapi.events.AccountCreatedEvent;
import com.unidev.cqrs.commonapi.events.AccountCreditedEvent;
import com.unidev.cqrs.commonapi.events.AccountDebitedEvent;
import com.unidev.cqrs.commonapi.exceptions.AmountNegativeException;
import com.unidev.cqrs.commonapi.exceptions.InsufficientBalanceException;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class AccountAggregate {

    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand){
        if(createAccountCommand.getInitial_balance() < 0) throw new RuntimeException("Impossible de créer un compte avec" +
                "un solde initial négatif");

        AggregateLifecycle.apply( new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getInitial_balance(),
                createAccountCommand.getCurrency(),
                AccountStatus.CREATED));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent){
        this.accountId = accountCreatedEvent.getId();
        this.currency = accountCreatedEvent.getCurrency();
        this.balance = accountCreatedEvent.getInitial_balance();
        this.status = AccountStatus.CREATED;
        AggregateLifecycle.apply( new AccountActivatedEvent(
                accountCreatedEvent.getId(),
                AccountStatus.ACTIVATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent accountActivatedEvent){
        this.status = accountActivatedEvent.getStatus();
    }

    @CommandHandler
    public void handle(CreditAccountCommand creditAccountCommand){
        if(creditAccountCommand.getAmount() < 0) throw new AmountNegativeException("Amount should not be negative");
        AggregateLifecycle.apply( new AccountCreditedEvent(
                creditAccountCommand.getId(),
                creditAccountCommand.getAmount(),
                creditAccountCommand.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent accountCreditedEvent){
        this.balance+=accountCreditedEvent.getAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand debitAccountCommand){
        if(debitAccountCommand.getAmount() < 0) throw new AmountNegativeException("Amount should not be negative");
        if(this.balance < debitAccountCommand.getAmount()) throw new InsufficientBalanceException("Balance insufisant");
        AggregateLifecycle.apply( new AccountDebitedEvent(
                debitAccountCommand.getId(),
                debitAccountCommand.getAmount(),
                debitAccountCommand.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent accountDebitedEvent){
        this.balance-=accountDebitedEvent.getAmount();
    }
}
