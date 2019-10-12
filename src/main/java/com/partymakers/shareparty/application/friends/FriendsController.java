package com.partymakers.shareparty.application.friends;

import com.partymakers.shareparty.domain.entity.Friend;
import com.partymakers.shareparty.domain.usecases.friends.RegisterFriend;
import com.partymakers.shareparty.application.V1Controller;
import com.partymakers.shareparty.application.friends.dto.CreateFriendRequest;

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

    @PostMapping("/friends")
    ResponseEntity<?> registerFriend(@RequestBody CreateFriendRequest request){
        registerUseCase.registerFriend(new Friend(request.getName(), request.getNickName(), request.getEMail()));

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
