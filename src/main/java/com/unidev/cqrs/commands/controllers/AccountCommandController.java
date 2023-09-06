package com.unidev.cqrs.commands.controllers;

import com.unidev.cqrs.commonapi.commands.CreateAccountCommand;
import com.unidev.cqrs.commonapi.commands.CreditAccountCommand;
import com.unidev.cqrs.commonapi.commands.DebitAccountCommand;
import com.unidev.cqrs.commonapi.dtos.CreateAccountRequestDTO;
import com.unidev.cqrs.commonapi.dtos.CreditAccountDTO;
import com.unidev.cqrs.commonapi.dtos.DebitAccountDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/commands/account")
@AllArgsConstructor
public class AccountCommandController {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    @PostMapping(path = "/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO createAccountRequestDTO){

        return commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                createAccountRequestDTO.getInitial_balance(),
                createAccountRequestDTO.getCurrency()
        ));
    }

    @PutMapping(path = "/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountDTO creditAccountDTO){
        return commandGateway.send( new CreditAccountCommand(
                creditAccountDTO.getAccountId(),
                creditAccountDTO.getAmount(),
                creditAccountDTO.getCurrency()
        ));
    }

    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountDTO debitAccountDTO){
        return commandGateway.send( new DebitAccountCommand(
                debitAccountDTO.getAccountId(),
                debitAccountDTO.getAmount(),
                debitAccountDTO.getCurrency()
        ));
    }

    @GetMapping("/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }


}
