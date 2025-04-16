package com.partymakers.shareparty.application.friends;

import com.partymakers.shareparty.domain.friends.entity.Friend;
import com.partymakers.shareparty.domain.friends.usecase.RegisterFriend;
import com.partymakers.shareparty.application.V1Controller;
import com.partymakers.shareparty.application.friends.dto.FriendDescription;
import com.partymakers.shareparty.domain.party.usecase.exception.AlreadyExistException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FriendsController extends V1Controller{

    @Autowired
    private final RegisterFriend registerUseCase;

    @PostMapping("/friend")
    ResponseEntity<?> registerFriend(@RequestBody FriendDescription request){
        try {
            registerUseCase.registerFriend(new Friend(request.getName(), request.getNickName(), request.getEMail()));
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        }catch(AlreadyExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.ALREADY_REPORTED);
        }
    }
}
