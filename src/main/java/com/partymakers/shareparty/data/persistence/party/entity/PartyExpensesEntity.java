package com.partymakers.shareparty.data.persistence.party.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "party_expenses")
@Getter
@RequiredArgsConstructor
public class PartyExpensesEntity {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "expenses_id")
    private final long expenseId;

    @Column(name = "party_room_id")
    private final long roomId;
}
