package com.partymakers.shareparty.application.party.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.partymakers.shareparty.domain.party.entity.PartyRoomDescription;

import java.util.Set;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Parties {
    @JsonProperty("parties")
    private final Set<PartyRoomDescription> partyRoomDescriptions;
}
