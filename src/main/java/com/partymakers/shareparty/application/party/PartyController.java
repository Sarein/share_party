package com.partymakers.shareparty.application.party;

import com.partymakers.shareparty.application.V1Controller;
import com.partymakers.shareparty.application.party.dto.InvitedFriendDescription;
import com.partymakers.shareparty.application.party.dto.PartyRoomDescription;
import com.partymakers.shareparty.application.party.dto.FullPartyInfo;
import com.partymakers.shareparty.application.party.dto.Parties;
import com.partymakers.shareparty.application.party.dto.Expense;
import com.partymakers.shareparty.application.party.dto.PartyExpenses;
import com.partymakers.shareparty.application.party.dto.PartyFriends;
import com.partymakers.shareparty.domain.party.usecase.AddPartyExpense;
import com.partymakers.shareparty.domain.party.usecase.CreatePartyRoom;
import com.partymakers.shareparty.domain.party.usecase.GetPartiesList;
import com.partymakers.shareparty.domain.party.usecase.GetParty;
import com.partymakers.shareparty.domain.party.usecase.GetPartyExpenses;
import com.partymakers.shareparty.domain.party.usecase.GetPartyFriends;
import com.partymakers.shareparty.domain.party.usecase.InviteFriend;
import com.partymakers.shareparty.domain.party.usecase.KickFiend;
import com.partymakers.shareparty.domain.party.usecase.RemovePartyExpense;
import com.partymakers.shareparty.domain.party.usecase.exception.AlreadyExistException;
import com.partymakers.shareparty.domain.party.usecase.exception.NotFoundException;

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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @ApiOperation(value = "Creates new the party room")
    public ResponseEntity<?> createPartyRoom(@RequestBody PartyRoomDescription request){

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
    @ApiOperation(value = "Returns list of existing party rooms", response = Parties.class)
    public ResponseEntity<?> getPartiesRoom(){
        try {
            return new ResponseEntity<>(new Parties(getPartiesList.getPartyList()), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/party/{partyId}/friend")
    @ApiOperation(value = "Invites friends to the party")
    ResponseEntity<?> inviteFriendToParty(@PathVariable("partyId") Long partyId, @RequestBody InvitedFriendDescription request) {

        try {
            inviteUseCase.inviteFriend(request.getNickName(), partyId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (AlreadyExistException e){
            return new ResponseEntity<Void>(HttpStatus.ALREADY_REPORTED);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/party/{partyId}/friend")
    @ApiOperation(value = "Returns friends of the party", response = PartyFriends.class)
    ResponseEntity<?> getPartyFriend(@PathVariable("partyId") Long partyId) {

        try {
            return new ResponseEntity<>(new PartyFriends(getPartyFriends.getPartyFriends(partyId)), HttpStatus.OK);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/party/{partyId}/friend")
    @ApiOperation(value = "Removes friend from the party")
    ResponseEntity<?> kickFriend(@PathVariable("partyId") Long partyId, @RequestBody InvitedFriendDescription request) {
        try {
            kickUseCase.kickFriend(request.getNickName(), partyId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/party/{partyId}/expense")
    @ApiOperation(value = "Adds expense to the party")
    ResponseEntity<?> addPartyExpense(@PathVariable("partyId") Long partyId,
                                      @RequestBody Expense request) {
        try {
            partyExpense.addPartyExpense(
                new com.partymakers.shareparty.domain.party.entity.Expense(request.getName(),
                    request.getCost(),
                    request.getCount()),partyId);

            return new ResponseEntity<Void>(HttpStatus.CREATED);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/party/{partyId}/expense")
    @ApiOperation(value = "Returns the party expenses", response = Expense.class)
    ResponseEntity<?> getPartyExpenses(@PathVariable("partyId") Long partyId) {

        try {
            return new ResponseEntity<>(
                new PartyExpenses(getPartyExpenses.getPartyExpenses(partyId)),
                HttpStatus.OK);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/party/{partyId}/expense")
    @ApiOperation(value = "Removes the party expense")
    ResponseEntity<?> removePartyExpense(@PathVariable("partyId") Long partyId,
                                         @ApiParam(value = "Full description of expense", required = true) @RequestBody Expense request) {
        try {
            removePartyExpense.removePartyExpense(partyId, new com.partymakers.shareparty.domain.party.entity.Expense(
                request.getName(),
                request.getCost(),
                request.getCount()));

            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/party/{partyId}")
    @ApiOperation(value = "Returns full party information", response = FullPartyInfo.class)
    ResponseEntity<?> getParty(@PathVariable("partyId") Long partyId) {
        try {
            return new ResponseEntity<>(new FullPartyInfo(getParty.getParty(partyId)), HttpStatus.OK);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

