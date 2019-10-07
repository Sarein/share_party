package com.partymakers.shareparty.data.persistence;

public interface PersistenceMapper <T, U> {
    U toDomain(T persistenceEntity);
}

