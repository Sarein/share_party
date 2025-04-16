package com.partymakers.shareparty.data.persistence.friends.entity;

import com.partymakers.shareparty.data.persistence.party.entity.PartyRoomEntity;
import com.partymakers.shareparty.domain.friends.entity.Friend;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "friends")
@Data
@EqualsAndHashCode(of = {"nickName", "name", "eMail"})
public class FriendEntity{

    @Id
    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "name")
    private String name;

    @Column(name = "e_mail")
    private String eMail;

    @ManyToMany(mappedBy = "invitedFriends", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PartyRoomEntity> partyRooms = new HashSet<>();

    public FriendEntity(String name, String nickName, String eMail) {
        this.name = name;
        this.nickName = nickName;
        this.eMail = eMail;
    }

    public FriendEntity() {
    }

    static public FriendEntity toPersistence(Friend domainEntity) {
        return new FriendEntity(domainEntity.getName(), domainEntity.getNickName(), domainEntity.getEMail());
    }


    public Friend toDomain() {
        return new Friend(name, nickName, eMail);
    }
}
