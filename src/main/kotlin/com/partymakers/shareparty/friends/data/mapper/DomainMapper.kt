package com.partymakers.shareparty.friends.data.mapper

interface DomainMapper<T, U> {
    fun toPersistence(domainEntity: T): U
}