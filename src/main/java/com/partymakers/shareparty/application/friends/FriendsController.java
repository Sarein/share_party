package com.partymakers.shareparty.application.friends;

import com.partymakers.shareparty.domain.friends.entity.Friend;
import com.partymakers.shareparty.domain.friends.usecase.RegisterFriend;
import com.partymakers.shareparty.application.V1Controller;
import com.partymakers.shareparty.application.friends.dto.FriendDescription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FriendsController extends V1Controller{

    @Autowired
    private final RegisterFriend registerUseCase;

    @PostMapping("/friends")
    @ApiOperation(value = "Invites friend to the party")
    ResponseEntity<?> registerFriend(@RequestBody FriendDescription request){
        registerUseCase.registerFriend(new Friend(request.getName(), request.getNickName(), request.getEMail()));

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
