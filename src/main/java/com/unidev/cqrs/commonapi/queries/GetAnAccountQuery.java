package com.unidev.cqrs.commonapi.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAnAccountQuery {
    private final String accountId;
}
