package com.partymakers.shareparty.domain.party.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode(of ={"id", "name"})
public class PartyRoomDescription {
    @Getter
    final Long id;

    @Getter
    final String name;

    public PartyRoomDescription(String name) {
        this.id=0L;
        this.name = name;
    }
}
