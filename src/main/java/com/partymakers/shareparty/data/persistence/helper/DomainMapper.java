package com.partymakers.shareparty.data.persistence.helper;

public interface DomainMapper<T, U> {
    U toPersistence(T domainEntity);
}
