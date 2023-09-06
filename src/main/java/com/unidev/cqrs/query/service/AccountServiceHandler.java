package com.unidev.cqrs.query.service;

import com.unidev.cqrs.commonapi.events.AccountCreatedEvent;
import com.unidev.cqrs.query.entities.Account;
import com.unidev.cqrs.query.repositories.AccountRepository;
import com.unidev.cqrs.query.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountServiceHandler {

    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;

    @EventHandler
    public void on(AccountCreatedEvent event){
        this.accountRepository.save( new Account(
                event.getId(),
                event.getInitial_balance(),
                event.getStatus(),
                event.getCurrency(),
                null
        ) );
    }
}
