package com.partymakers.shareparty.application.party;

import com.partymakers.shareparty.application.V1Controller;
import com.partymakers.shareparty.application.party.dto.*;
import com.partymakers.shareparty.domain.party.usecase.*;
import com.partymakers.shareparty.domain.party.usecase.exception.AlreadyExistException;
import com.partymakers.shareparty.domain.party.usecase.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class PartyController extends V1Controller {

    @Autowired
    private final CreatePartyRoom createPartyRoom;
    @Autowired
    private final InviteFriend inviteUseCase;
    @Autowired
    private final KickFriend kickUseCase;
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
    public ResponseEntity<?> createPartyRoom(@RequestBody PartyRoomDescription request) {

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
    public ResponseEntity<?> getPartiesRoom() {
        try {
            return new ResponseEntity<>(new Parties(getPartiesList.getPartyList()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/party/{partyId}/friend")
    ResponseEntity<?> inviteFriendToParty(@PathVariable("partyId") Long partyId, @RequestBody InvitedFriendDescription request) {

        try {
            inviteUseCase.inviteFriend(request.getNickName(), partyId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (AlreadyExistException e) {
            return new ResponseEntity<Void>(HttpStatus.ALREADY_REPORTED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/party/{partyId}/friend")
    ResponseEntity<?> getPartyFriend(@PathVariable("partyId") Long partyId) {

        try {
            return new ResponseEntity<>(new PartyFriends(getPartyFriends.getPartyFriends(partyId)), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/party/{partyId}/friend")
    ResponseEntity<?> kickFriend(@PathVariable("partyId") Long partyId, @RequestBody InvitedFriendDescription request) {
        try {
            kickUseCase.kickFriend(request.getNickName(), partyId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/party/{partyId}/expense")
    ResponseEntity<?> addPartyExpense(@PathVariable("partyId") Long partyId,
                                      @RequestBody Expense request) {
        try {
            partyExpense.addPartyExpense(
                    new com.partymakers.shareparty.domain.party.entity.Expense(request.getName(),
                            request.getCost(),
                            request.getCount()), partyId);

            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/party/{partyId}/expense")
    ResponseEntity<?> getPartyExpenses(@PathVariable("partyId") Long partyId) {

        try {
            return new ResponseEntity<>(
                    new PartyExpenses(getPartyExpenses.getPartyExpenses(partyId)),
                    HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/party/{partyId}/expense")
    ResponseEntity<?> removePartyExpense(@PathVariable("partyId") Long partyId, @RequestBody Expense request) {
        try {
            removePartyExpense.removePartyExpense(partyId, new com.partymakers.shareparty.domain.party.entity.Expense(
                    request.getName(),
                    request.getCost(),
                    request.getCount()));

            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/party/{partyId}")
    ResponseEntity<?> getParty(@PathVariable("partyId") Long partyId) {
        try {
            return new ResponseEntity<>(new FullPartyInfo(getParty.getParty(partyId)), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

