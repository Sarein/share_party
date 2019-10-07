package com.partymakers.shareparty.data.persistence.party.mapping;

import com.partymakers.shareparty.data.persistence.party.PartyRoomEntity;
import com.partymakers.shareparty.domain.entity.PartyRoom;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class PartyRoomMapping_old {

    public static PartyRoom  transform(@NotNull PartyRoomEntity partyRoomEntity) {
        return new PartyRoom(partyRoomEntity.getId(), partyRoomEntity.getName());
    }

    public static Iterable<PartyRoom> transformFromDbEntity(Iterable<PartyRoomEntity> partyRoomCollection) {
        final List<PartyRoom> partyRoomList = new ArrayList<>();
        for (PartyRoomEntity partyRoomEntity : partyRoomCollection) {
            final PartyRoom partyRoom = transform(partyRoomEntity);
            if (partyRoom != null) {
                partyRoomList.add(partyRoom);
            }
        }

        return partyRoomList;
    }

    public static  PartyRoomEntity transform(@NotNull PartyRoom  partyRoomEntity) {
        return new PartyRoomEntity(partyRoomEntity.getId(), partyRoomEntity.getName());
    }

    public static Iterable<PartyRoomEntity> transformFromDomainEntity(Iterable<PartyRoom> partyRoomCollection) {
    final List<PartyRoomEntity> partyRoomList = new ArrayList<>();
    for (PartyRoom partyRoomEntity : partyRoomCollection) {
        final PartyRoomEntity partyRoom = transform(partyRoomEntity);
        if (partyRoom != null) {
            partyRoomList.add(partyRoom);
        }
    }

    return partyRoomList;
}
}
