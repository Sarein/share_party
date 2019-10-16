package com.partymakers.shareparty.application.party;

import com.partymakers.shareparty.application.party.dto.PartyExpense;
import com.partymakers.shareparty.domain.expenses.entity.Expense;
import com.partymakers.shareparty.domain.party.entity.PartyRoom;
import com.partymakers.shareparty.domain.party.usecase.AddPartyExpense;
import com.partymakers.shareparty.domain.party.usecase.InviteFriend;
import com.partymakers.shareparty.domain.party.usecase.CreatePartyRoom;
import com.partymakers.shareparty.domain.party.usecase.KickFiend;
import com.partymakers.shareparty.application.V1Controller;
import com.partymakers.shareparty.application.friends.dto.InviteFriendRequest;
import com.partymakers.shareparty.application.party.dto.CreatePartyRoomRequest;
import com.partymakers.shareparty.domain.party.usecase.RemovePartyExpense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PartyController extends V1Controller{

    @Autowired
    private final CreatePartyRoom createPartyRoom;
    @Autowired
    private final InviteFriend inviteUseCase;
    @Autowired
    private final KickFiend kickUseCase;
    @Autowired
    private final AddPartyExpense partyExpense;
    @Autowired
    private final RemovePartyExpense removePartyExpense;

    @PostMapping("/party")
    public ResponseEntity<?> createPartyRoom(@RequestBody CreatePartyRoomRequest request){

        URI creationLocation =
            ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(
                    createPartyRoom.createPartyRoom(new PartyRoom(request.getName())))
            .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(creationLocation);

        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    @PostMapping("/party/{partyId}/friends")
    ResponseEntity<?> inviteFriendToParty(@PathVariable("partyId") Long partyId, @RequestBody InviteFriendRequest request) {

        try {
            inviteUseCase.inviteFriend(request.getNickName(), partyId);
        }
        catch (Exception e ) {
            return new ResponseEntity<Void>(HttpStatus.ALREADY_REPORTED);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping("/party/{partyId}/friends")
    ResponseEntity<?> kickFriend(@PathVariable("partyId") Long partyId, @RequestBody InviteFriendRequest request) {

        kickUseCase.kickFriend(request.getNickName(), partyId);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/party/{partyId}/expenses")
    ResponseEntity<?> addPartyExpense(@PathVariable("partyId") Long partyId,
                                      @RequestBody PartyExpense request) {

        URI creationLocation =
            ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{partyId}")
                .buildAndExpand(partyExpense.addPartyExpense(
                    new Expense(request.getName(),
                        request.getCost(),
                        request.getCount()),
                    partyId)).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(creationLocation);

        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/party/{partyId}/expenses/{expenseId}")
    ResponseEntity<?> removePartyExpense(@PathVariable("partyId")   Long partyId,
                                         @PathVariable("expenseId") Long expenseId) {

        removePartyExpense.removePartyExpense(expenseId, partyId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}

