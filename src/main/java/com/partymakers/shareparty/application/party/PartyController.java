package com.partymakers.shareparty.application.party;

import com.partymakers.shareparty.application.V1Controller;
import com.partymakers.shareparty.application.friends.dto.InviteFriendRequest;
import com.partymakers.shareparty.application.party.dto.CreatePartyRoomRequest;
import com.partymakers.shareparty.application.party.dto.PartiesResponse;
import com.partymakers.shareparty.application.party.dto.PartyExpenseRequest;
import com.partymakers.shareparty.application.party.dto.PartyExpensesResponse;
import com.partymakers.shareparty.application.party.dto.PartyFriendsResponse;
import com.partymakers.shareparty.domain.party.entity.Expense;
import com.partymakers.shareparty.domain.party.usecase.AddPartyExpense;
import com.partymakers.shareparty.domain.party.usecase.CreatePartyRoom;
import com.partymakers.shareparty.domain.party.usecase.GetPartiesList;
import com.partymakers.shareparty.domain.party.usecase.GetParty;
import com.partymakers.shareparty.domain.party.usecase.GetPartyExpenses;
import com.partymakers.shareparty.domain.party.usecase.GetPartyFriends;
import com.partymakers.shareparty.domain.party.usecase.InviteFriend;
import com.partymakers.shareparty.domain.party.usecase.KickFiend;
import com.partymakers.shareparty.domain.party.usecase.RemovePartyExpense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final GetPartiesList getPartiesList;
    @Autowired
    private final GetPartyFriends getPartyFriends;
    @Autowired
    private final GetPartyExpenses getPartyExpenses;
    @Autowired
    private final RemovePartyExpense removePartyExpense;
    @Autowired
    private final GetParty getParty;

    @PostMapping("/party")
    public ResponseEntity<?> createPartyRoom(@RequestBody CreatePartyRoomRequest request){

        URI creationLocation =
            ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(
                    createPartyRoom.createPartyRoom(request.getName()))
            .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(creationLocation);

        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/party")
    public ResponseEntity<?> getPartiesRoom(){
        return new ResponseEntity<>(new PartiesResponse(getPartiesList.getPartyList()), HttpStatus.OK);
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

    @GetMapping("/party/{partyId}/friends")
    ResponseEntity<?> getPartyFriend(@PathVariable("partyId") Long partyId) {
        return new ResponseEntity<>(new PartyFriendsResponse(getPartyFriends.getPartyFriends(partyId)), HttpStatus.OK);
    }

    @DeleteMapping("/party/{partyId}/friends")
    ResponseEntity<?> kickFriend(@PathVariable("partyId") Long partyId, @RequestBody InviteFriendRequest request) {

        kickUseCase.kickFriend(request.getNickName(), partyId);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/party/{partyId}/expense")
    ResponseEntity<?> addPartyExpense(@PathVariable("partyId") Long partyId,
                                      @RequestBody PartyExpenseRequest request) {
        partyExpense.addPartyExpense(
            new Expense(request.getName(),
                request.getCost(),
                request.getCount()),partyId);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping("/party/{partyId}/expense")
    ResponseEntity<?> getPartyExpenses(@PathVariable("partyId") Long partyId) {

        return new ResponseEntity<>(
            new PartyExpensesResponse(getPartyExpenses.getPartyExpenses(partyId)),
            HttpStatus.OK);
    }

    @DeleteMapping("/party/{partyId}/expense")
    ResponseEntity<?> removePartyExpense(@PathVariable("partyId")   Long partyId,
                                         @RequestBody PartyExpenseRequest request) {

        removePartyExpense.removePartyExpense(partyId, new Expense(
                                                request.getName(),
                                                request.getCost(),
                                                request.getCount()));

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/party/{partyId}")
    ResponseEntity<?> getParty(@PathVariable("partyId") Long partyId) {
        return new ResponseEntity<>(getParty.getParty(partyId), HttpStatus.OK);
    }
}

