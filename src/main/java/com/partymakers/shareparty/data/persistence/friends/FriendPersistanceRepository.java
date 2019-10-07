package com.partymakers.shareparty.data.persistence.friends;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendPersistanceRepository extends JpaRepository<FriendEntity, Long> {
}
