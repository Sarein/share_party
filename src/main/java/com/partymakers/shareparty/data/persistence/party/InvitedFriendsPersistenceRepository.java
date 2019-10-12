package com.partymakers.shareparty.data.persistence.party;

import com.partymakers.shareparty.data.persistence.friends.entity.InvitedFriends;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface InvitedFriendsPersistenceRepository extends JpaRepository<InvitedFriends, Long> {

    @Modifying
    @Transactional
    @Query("delete from #{#entityName} u where u.friendName=:nickName and u.roomId=:roomId")
    void delete(@Param("nickName") String nickName, @Param("roomId")Long room);
}
