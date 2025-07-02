package com.partymakers.shareparty.data.persistence.helper

interface DomainMapper<T, U> {
    fun toPersistence(domainEntity: T): U
}