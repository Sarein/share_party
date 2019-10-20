package com.partymakers.shareparty.data.persistence.friends;

import com.partymakers.shareparty.data.persistence.friends.entity.FriendEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendPersistenceRepository extends JpaRepository<FriendEntity, String> {
}
