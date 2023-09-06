package com.unidev.cqrs.commands.controllers;

import com.unidev.cqrs.commonapi.commands.CreateAccountCommand;
import com.unidev.cqrs.commonapi.dtos.CreateAccountRequestDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "/commands/account")
@AllArgsConstructor
public class AccountCommandController {

    private final CommandGateway commandGateway;

    @PostMapping(path = "/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO createAccountRequestDTO){

        return commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                createAccountRequestDTO.getInitial_balance(),
                createAccountRequestDTO.getCurrency()
        ));
    }


}
