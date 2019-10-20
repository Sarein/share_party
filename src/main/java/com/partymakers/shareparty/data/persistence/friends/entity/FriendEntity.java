package com.partymakers.shareparty.data.persistence.friends.entity;

import com.partymakers.shareparty.data.persistence.party.entity.PartyRoomEntity;
import com.partymakers.shareparty.domain.friends.entity.Friend;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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
