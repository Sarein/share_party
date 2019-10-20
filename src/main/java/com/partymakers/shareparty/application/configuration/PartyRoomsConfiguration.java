package com.partymakers.shareparty.application.configuration;

import com.partymakers.shareparty.data.persistence.party.PartyExpensesEntityRepository;
import com.partymakers.shareparty.data.persistence.party.PartyRoomPersistanceRepository;
import com.partymakers.shareparty.data.persistence.party.entity.PartyRoomEntity;
import com.partymakers.shareparty.data.persistence.party.impl.ExpensesRepositoryImpl;
import com.partymakers.shareparty.data.persistence.party.impl.PartyRoomRepositoryImpl;
import com.partymakers.shareparty.domain.expenses.usecase.AddExpense;
import com.partymakers.shareparty.domain.friends.port.FriendsRepository;
import com.partymakers.shareparty.domain.party.port.PartyRoomRepository;
import com.partymakers.shareparty.domain.party.usecase.AddPartyExpense;
import com.partymakers.shareparty.domain.party.usecase.CreatePartyRoom;
import com.partymakers.shareparty.domain.party.usecase.GetPartiesList;
import com.partymakers.shareparty.domain.party.usecase.GetPartyFriends;
import com.partymakers.shareparty.domain.party.usecase.InviteFriend;
import com.partymakers.shareparty.domain.party.usecase.KickFiend;
import com.partymakers.shareparty.domain.party.usecase.RemovePartyExpense;
import com.partymakers.shareparty.domain.party.usecase.impl.AddPartyExpenseImpl;
import com.partymakers.shareparty.domain.party.usecase.impl.CreatePartyRoomImpl;
import com.partymakers.shareparty.domain.party.usecase.impl.GetPartiesListImpl;
import com.partymakers.shareparty.domain.party.usecase.impl.GetPartyFriendsImpl;
import com.partymakers.shareparty.domain.party.usecase.impl.InviteFriendImpl;
import com.partymakers.shareparty.domain.party.usecase.impl.KickFiendImpl;
import com.partymakers.shareparty.domain.party.usecase.impl.RemovePartyExpenseImpl;
import com.partymakers.shareparty.domain.usecases.party.port.PartyExpensesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
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
    PartyExpensesRepository partyExpensesRepository(PartyExpensesEntityRepository repository) {
        return new ExpensesRepositoryImpl(repository);
    }

    @Bean
    AddPartyExpense addPartyExpense(@Autowired PartyExpensesEntityRepository partyExpensesRepository,
                                    @Autowired AddExpense addExpenses){
        return new AddPartyExpenseImpl(partyExpensesRepository(partyExpensesRepository), addExpenses);
    }

    @Bean
    RemovePartyExpense removePartyExpense(@Autowired PartyExpensesEntityRepository partyExpensesRepository){
        return new RemovePartyExpenseImpl(partyExpensesRepository(partyExpensesRepository));
    }

    @Bean
    GetPartiesList getPartyRoomList(@Autowired PartyRoomPersistanceRepository partyRoomRepository) {
        return new GetPartiesListImpl(partyRoomRepository(partyRoomRepository));
    }

    @Bean
    GetPartyFriends getPartyFriends(@Autowired PartyRoomPersistanceRepository partyRoomRepository) {
        return new GetPartyFriendsImpl(partyRoomRepository(partyRoomRepository));
    }

}
