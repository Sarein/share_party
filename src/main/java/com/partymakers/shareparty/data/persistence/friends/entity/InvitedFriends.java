package com.partymakers.shareparty.data.persistence.friends.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "friends_invite")
@Data
public class InvitedFriends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "friend_nick_name")
    String friendName;

    @Column(name = "party_room_id")
    Long   roomId;

    public InvitedFriends(String friendName, Long roomId) {
        this.friendName = friendName;
        this.roomId = roomId;
    }
}
