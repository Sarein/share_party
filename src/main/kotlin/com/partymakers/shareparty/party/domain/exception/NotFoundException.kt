package com.partymakers.shareparty.party.domain.exception

class NotFoundException : RuntimeException {
    constructor() : super()
    constructor(description: String) : super(description)
} 