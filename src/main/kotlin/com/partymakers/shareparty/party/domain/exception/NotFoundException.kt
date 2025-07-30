package com.partymakers.shareparty.party.domain.exception

internal class NotFoundException : RuntimeException {
    constructor() : super()
    constructor(description: String) : super(description)
} 