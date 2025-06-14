package com.partymakers.shareparty.domain.party.usecase.exception

class AlreadyExistException : RuntimeException {
    constructor() : super()
    constructor(description: String) : super(description)
} 