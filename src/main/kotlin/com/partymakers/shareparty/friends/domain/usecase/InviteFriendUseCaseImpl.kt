package com.partymakers.shareparty.friends.domain.usecase

import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import com.partymakers.shareparty.party.domain.exception.AlreadyExistException
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import com.partymakers.shareparty.friends.domain.repository.FriendsRepository
import org.springframework.stereotype.Service

@Service
class InviteFriendUseCaseImpl(
    private val partyRoomRepository: PartyRoomRepository,
    private val friendsRepository: FriendsRepository,
) : InviteFriendUseCase {

    override fun inviteFriend(nickName: String, partyId: Long) {
        val friend = friendsRepository.findOneById(nickName)

        if (friend == null) throw NotFoundException("Друг не найден")

        val room = partyRoomRepository.findById(partyId)
            .orElseThrow { NotFoundException("Party room не найдена") }

        if (room.friends.contains(friend)) {
            throw AlreadyExistException("Friend ${friend.nickName} already invited")
        }

        val changedRoom = room.copy(friends = room.friends + friend)
        partyRoomRepository.save(changedRoom)
    }
} 