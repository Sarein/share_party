package com.partymakers.shareparty.domain.usecases.port;

import com.partymakers.shareparty.domain.entity.PartyRoom;
import org.springframework.data.repository.CrudRepository;

public interface PartyRoomRepository extends CrudRepository<PartyRoom, Long> {
}
