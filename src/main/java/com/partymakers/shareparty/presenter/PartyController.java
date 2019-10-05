package com.partymakers.shareparty.presenter;

import com.partymakers.shareparty.domain.entity.PartyRoom;
import com.partymakers.shareparty.domain.usecases.party.CreatePartyRoom;
import com.partymakers.shareparty.presenter.party.dto.CreatePartyRoomRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PartyController {

    CreatePartyRoom m_createPartyRoom;

    PartyController(CreatePartyRoom createPartyRoom){
        m_createPartyRoom = createPartyRoom;
    }

    @PostMapping("/parties")
    public String createPartyRoom(@RequestBody CreatePartyRoomRequest name){


        m_createPartyRoom.createPartyRoom(new PartyRoom(name.getName()));
        return "Hello World!";
    }
}
