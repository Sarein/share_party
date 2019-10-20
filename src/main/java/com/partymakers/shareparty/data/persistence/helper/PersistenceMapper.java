package com.partymakers.shareparty.data.persistence.helper;

public interface PersistenceMapper <T, U> {
    U toDomain(T persistenceEntity);
}

