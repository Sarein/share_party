package com.partymakers.shareparty.party.domain.usecase

import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.exception.AlreadyExistException
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import com.partymakers.shareparty.friends.domain.repository.FriendsRepository
import com.partymakers.shareparty.party.domain.entity.PartyRoom
import org.springframework.stereotype.Service

@Service
internal class InviteFriendUseCaseImpl(
    private val partyRoomRepository: PartyRoomRepository,
) : InviteFriendUseCase {

    override fun invoke(nickName: String, roomId: Long): PartyRoom =
        partyRoomRepository.addFriend(roomId, nickName)
            ?: throw NotFoundException("Party room or friend does not exist")
} 