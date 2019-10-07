package com.partymakers.shareparty.data.persistence;

public interface DomainMapper<T, U> {
    U toPersistence(T domainEntity);
}
