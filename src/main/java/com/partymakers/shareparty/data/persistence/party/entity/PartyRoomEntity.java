package com.partymakers.shareparty.data.persistence.party.entity;

import com.partymakers.shareparty.data.persistence.friends.entity.FriendEntity;
import com.partymakers.shareparty.domain.party.entity.PartyRoom;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "party_room")
@Data
@EqualsAndHashCode(of = {"id", "name"})
public class PartyRoomEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "partyroom_friends",
        joinColumns = {@JoinColumn(name = "partyroom_id")},
        inverseJoinColumns = {@JoinColumn(name = "friend_nick_name")})
    private Set<FriendEntity> invitedFriends = new HashSet<>();

    public PartyRoomEntity() {
    }

    public PartyRoomEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public PartyRoomEntity(long id, String name, Set<FriendEntity> invitedFriends) {
        this.id = id;
        this.name = name;
        this.invitedFriends = invitedFriends;
    }

    public static PartyRoomEntity toPersistence(PartyRoom domainEntity) {
        return new PartyRoomEntity(domainEntity.getDescription().getId(),
                                   domainEntity.getDescription().getName(),
            domainEntity.getFriends().stream().map(
                    (friend) -> FriendEntity.toPersistence(friend)).collect(Collectors.toSet()));
    }


    public PartyRoom toDomain() {
         return new PartyRoom(id, name,
             invitedFriends.stream().map(
                (friend) -> friend.toDomain()).collect(Collectors.toSet()));
    }
}
