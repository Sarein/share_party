package com.partymakers.shareparty.data.persistence.helper

interface PersistenceMapper<T, U> {
    fun toDomain(persistenceEntity: T): U
}