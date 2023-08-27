package com.unidev.cqrs.commonapi.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
public abstract class BaseCommand <T>{

    @TargetAggregateIdentifier
    @Getter
    private final T id;

}
