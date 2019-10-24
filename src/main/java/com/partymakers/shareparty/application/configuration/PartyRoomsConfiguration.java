package com.partymakers.shareparty.application.configuration;

import com.partymakers.shareparty.data.persistence.party.PartyRoomPersistanceRepository;
import com.partymakers.shareparty.data.persistence.party.impl.PartyRoomRepositoryImpl;
import com.partymakers.shareparty.domain.friends.port.FriendsRepository;
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository;
import com.partymakers.shareparty.domain.party.usecase.AddPartyExpense;
import com.partymakers.shareparty.domain.party.usecase.CreatePartyRoom;
import com.partymakers.shareparty.domain.party.usecase.GetPartiesList;
import com.partymakers.shareparty.domain.party.usecase.GetPartyExpenses;
import com.partymakers.shareparty.domain.party.usecase.GetPartyFriends;
import com.partymakers.shareparty.domain.party.usecase.InviteFriend;
import com.partymakers.shareparty.domain.party.usecase.KickFiend;
import com.partymakers.shareparty.domain.party.usecase.impl.AddPartyExpenseImpl;
import com.partymakers.shareparty.domain.party.usecase.impl.CreatePartyRoomImpl;
import com.partymakers.shareparty.domain.party.usecase.impl.GetPartiesListImpl;
import com.partymakers.shareparty.domain.party.usecase.impl.GetPartyExpensesImpl;
import com.partymakers.shareparty.domain.party.usecase.impl.GetPartyFriendsImpl;
import com.partymakers.shareparty.domain.party.usecase.impl.InviteFriendImpl;
import com.partymakers.shareparty.domain.party.usecase.impl.KickFiendImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.partymakers.shareparty.data.persistence.party")
public class PartyRoomsConfiguration {

    @Bean
    PartyRoomRepository partyRoomRepository(PartyRoomPersistanceRepository repository)
    {
        return new PartyRoomRepositoryImpl(repository);
    }

    @Bean
    CreatePartyRoom createPartyRoom(@Autowired PartyRoomPersistanceRepository repository)
    {
        return new CreatePartyRoomImpl(partyRoomRepository(repository));
    }


    @Bean
    InviteFriend inviteFriend(@Autowired PartyRoomPersistanceRepository partyRoomRepository, @Autowired FriendsRepository friendsRepository){
        return new InviteFriendImpl(partyRoomRepository(partyRoomRepository), friendsRepository);
    }

    @Bean
    KickFiend kickFriend(@Autowired PartyRoomPersistanceRepository partyRoomRepository){
        return new KickFiendImpl(partyRoomRepository(partyRoomRepository));
    }

    @Bean
    AddPartyExpense addPartyExpense(@Autowired PartyRoomPersistanceRepository partyRoomRepository){
        return new AddPartyExpenseImpl(partyRoomRepository(partyRoomRepository));
    }

    @Bean
    GetPartiesList getPartyRoomList(@Autowired PartyRoomPersistanceRepository partyRoomRepository) {
        return new GetPartiesListImpl(partyRoomRepository(partyRoomRepository));
    }

    @Bean
    GetPartyFriends getPartyFriends(@Autowired PartyRoomPersistanceRepository partyRoomRepository) {
        return new GetPartyFriendsImpl(partyRoomRepository(partyRoomRepository));
    }

    @Bean
    GetPartyExpenses getPartyExpenses(@Autowired PartyRoomPersistanceRepository partyRoomRepository) {
        return new GetPartyExpensesImpl(partyRoomRepository(partyRoomRepository));
    }

}
