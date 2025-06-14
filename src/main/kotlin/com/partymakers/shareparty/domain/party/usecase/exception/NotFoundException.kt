package com.partymakers.shareparty.domain.party.usecase.exception

class NotFoundException : RuntimeException {
    constructor() : super()
    constructor(description: String) : super(description)
} 