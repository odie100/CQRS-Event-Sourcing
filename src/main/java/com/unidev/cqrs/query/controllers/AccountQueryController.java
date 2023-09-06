package com.unidev.cqrs.query.controllers;

import com.unidev.cqrs.commonapi.queries.GetAllAccountQuery;
import com.unidev.cqrs.commonapi.queries.GetAnAccountQuery;
import com.unidev.cqrs.query.entities.Account;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/query/accounts")
@AllArgsConstructor @Slf4j
public class AccountQueryController {

    private final QueryGateway queryGateway;

    @GetMapping("/allAccounts")
    public List<Account> accountList(){
        return queryGateway.query(new GetAllAccountQuery(), ResponseTypes.multipleInstancesOf(Account.class)).join();
    }

    @GetMapping("/{accountId}")
    public Account getAccount(@PathVariable String accountId){
        return queryGateway.query(new GetAnAccountQuery(accountId), ResponseTypes.instanceOf(Account.class)).join();
    }
}
