package com.partymakers.shareparty.data.persistence.party.entity;


import com.partymakers.shareparty.domain.party.entity.Expense;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Table(name = "expenses")
@Entity
@ToString (of = {"id", "name", "cost", "count"})
@EqualsAndHashCode(of = {"id", "name", "cost", "count"})
@NoArgsConstructor
public class ExpenseEntity {
    @Id
    @GeneratedValue
    @Column(name = "expense_id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "cost")
    private int    cost;
    @Column(name = "count")
    private double count;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="room_id", referencedColumnName = "room_id", columnDefinition="integer")
    private PartyRoomEntity partyRoomEntity;

    public ExpenseEntity(String name, int cost, double count, PartyRoomEntity partyRoomEntity) {
        this.name = name;
        this.cost = cost;
        this.count = count;
        this.partyRoomEntity = partyRoomEntity;
    }

    public static ExpenseEntity toPersistence(Expense domainEntity, PartyRoomEntity partyRoomEntity) {
        return new ExpenseEntity(
            domainEntity.getName(),
            domainEntity.getCost(),
            domainEntity.getCount(),
            partyRoomEntity
        );
    }

    public Expense toDomain() {
        return new Expense(name,cost,count);
    }
}
