package com.partymakers.shareparty.data.persistence.party.entity;


import com.partymakers.shareparty.domain.party.entity.Expense;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
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
