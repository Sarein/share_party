package com.partymakers.shareparty.data.persistence.party;

import com.partymakers.shareparty.data.persistence.party.entity.PartyExpensesEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PartyExpensesEntityRepository extends JpaRepository<PartyExpensesEntity, Long> {

    @Modifying
    @Transactional
    @Query("delete from #{#entityName} u where u.expenseId=?1 and u.roomId=?2")
    void delete(long expenseId, long roomId);
}
