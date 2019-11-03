package com.partymakers.shareparty.application.configuration;

import com.partymakers.shareparty.data.persistence.party.PartyRoomPersistenceRepository;
import com.partymakers.shareparty.data.persistence.party.impl.PartyRoomRepositoryImpl;
import com.partymakers.shareparty.domain.friends.port.FriendsRepository;
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository;
import com.partymakers.shareparty.domain.party.usecase.*;
import com.partymakers.shareparty.domain.party.usecase.impl.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.partymakers.shareparty.data.persistence.party")
public class PartyRoomsConfiguration {

    @Bean
    PartyRoomRepository partyRoomRepository(@Autowired PartyRoomPersistenceRepository repository)
    {
        return new PartyRoomRepositoryImpl(repository);
    }


    @Bean
    CreatePartyRoom createPartyRoom(PartyRoomRepository repository)
    {
        return new CreatePartyRoomImpl(repository);
    }


    @Bean
    InviteFriend inviteFriend(PartyRoomRepository repository, @Autowired FriendsRepository friendsRepository){
        return new InviteFriendImpl(repository, friendsRepository);
    }

    @Bean
    KickFiend kickFriend(PartyRoomRepository repository){
        return new KickFiendImpl(repository);
    }

    @Bean
    AddPartyExpense addPartyExpense(PartyRoomRepository repository){
        return new AddPartyExpenseImpl(repository);
    }

    @Bean
    GetPartiesList getPartyRoomList(PartyRoomRepository repository) {
        return new GetPartiesListImpl(repository);
    }

    @Bean
    GetPartyFriends getPartyFriends(PartyRoomRepository repository) {
        return new GetPartyFriendsImpl(repository);
    }

    @Bean
    GetPartyExpenses getPartyExpenses(PartyRoomRepository repository) {
        return new GetPartyExpensesImpl(repository);
    }

    @Bean
    RemovePartyExpense removePartyExpense(PartyRoomRepository repository) {
        return new RemovePartyExpenseImpl(repository);
    }

    @Bean
    GetParty getParty (PartyRoomRepository repository) {
        return new GetPartyImpl(repository);
    }
}
