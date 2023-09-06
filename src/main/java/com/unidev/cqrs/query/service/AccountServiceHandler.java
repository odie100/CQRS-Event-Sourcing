package com.unidev.cqrs.query.service;

import com.unidev.cqrs.commonapi.enums.OperationType;
import com.unidev.cqrs.commonapi.events.AccountActivatedEvent;
import com.unidev.cqrs.commonapi.events.AccountCreatedEvent;
import com.unidev.cqrs.commonapi.events.AccountDebitedEvent;
import com.unidev.cqrs.query.entities.Account;
import com.unidev.cqrs.query.entities.Operation;
import com.unidev.cqrs.query.repositories.AccountRepository;
import com.unidev.cqrs.query.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
public class AccountServiceHandler {

    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;

    @EventHandler
    public void on(AccountCreatedEvent event){
        log.info("**********************");
        log.info("AccountCreatedEvent received");
        this.accountRepository.save( new Account(
                event.getId(),
                event.getInitial_balance(),
                event.getStatus(),
                event.getCurrency(),
                null
        ) );
    }

    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("**********************");
        log.info("AccountActivatedEvent received");
        Account account = this.accountRepository.findById((event.getId())).get();
        account.setStatus(event.getStatus());
        this.accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountDebitedEvent event){
        Account account = this.accountRepository.findById(event.getId()).get();
        Operation operation = Operation.builder()
                .account(account)
                .amount(event.getAmount())
                .date(new Date()) //this must be in the write side
                .type(OperationType.DEBIT)
                .build();
        this.operationRepository.save(operation);
        account.setBalance(account.getBalance() - event.getAmount());
        this.accountRepository.save(account);
    }


}
