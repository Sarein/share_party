package com.partymakers.shareparty.party.domain.exception

class AlreadyExistException : RuntimeException {
    constructor() : super()
    constructor(description: String) : super(description)
} 