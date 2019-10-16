package com.partymakers.shareparty.domain.usecases.party.port;

public interface InvitedFriendsRepository {
    void inviteFriend(String nickName, Long partyRoom);
    void deleteFriend(String nickName, Long partyRoom);
}
