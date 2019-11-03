package com.partymakers.shareparty.application.party.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.partymakers.shareparty.domain.party.entity.PartyRoomDescription;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parties {
    @JsonProperty("parties")
    private Set<PartyRoomDescription> partyRoomDescriptions;
}
