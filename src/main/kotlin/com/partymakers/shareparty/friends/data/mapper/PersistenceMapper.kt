package com.partymakers.shareparty.friends.data.mapper

interface PersistenceMapper<T, U> {
    fun toDomain(persistenceEntity: T): U
}