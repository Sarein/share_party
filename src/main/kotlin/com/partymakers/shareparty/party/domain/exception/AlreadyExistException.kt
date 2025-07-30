package com.partymakers.shareparty.party.domain.exception

internal class AlreadyExistException : RuntimeException {
    constructor() : super()
    constructor(description: String) : super(description)
} 