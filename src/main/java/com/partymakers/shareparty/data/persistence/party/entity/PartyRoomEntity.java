package com.partymakers.shareparty.data.persistence.party.entity;

import com.partymakers.shareparty.data.persistence.friends.entity.FriendEntity;
import com.partymakers.shareparty.domain.party.entity.PartyRoom;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "party_room")
@Data
@EqualsAndHashCode(of = {"id", "name"})
@NoArgsConstructor
@ToString(of ={"id", "name"})
public class PartyRoomEntity{

    @Id
    @Column(name = "room_id")
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

    @OneToMany(mappedBy = "partyRoomEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpenseEntity> expenses = new ArrayList<>();


    private PartyRoomEntity(long id, String name, Set<FriendEntity> invitedFriends) {
        this.id = id;
        this.name = name;
        this.invitedFriends = invitedFriends;
    }

    public static PartyRoomEntity toPersistence(PartyRoom domainEntity) {
         PartyRoomEntity roomEntity =  new PartyRoomEntity(domainEntity.getDescription().getId(),
                                   domainEntity.getDescription().getName(),
            domainEntity.getFriends().stream().map(
                    (friend) -> FriendEntity.toPersistence(friend)).collect(Collectors.toSet()));

        roomEntity.setExpenses(domainEntity.getExpenses().stream().map(
            (expense) -> ExpenseEntity.toPersistence(expense, roomEntity)).collect(Collectors.toList()));

        return roomEntity;
    }

    public PartyRoom toDomain() {
         return new PartyRoom(id, name,
             invitedFriends.stream().map(
                friend -> friend.toDomain()).collect(Collectors.toSet()),
             expenses.stream().map(
                 expense -> expense.toDomain()).collect(Collectors.toList()));
    }
}
