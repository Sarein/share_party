package com.partymakers.shareparty.application.configuration;

import com.partymakers.shareparty.data.persistence.DomainMapper;
import com.partymakers.shareparty.data.persistence.PersistenceMapper;
import com.partymakers.shareparty.data.persistence.RepositoryFacade;
import com.partymakers.shareparty.data.persistence.party.InvitedFriendsPersistenceRepository;
import com.partymakers.shareparty.data.persistence.party.PartyExpensesEntityRepository;
import com.partymakers.shareparty.data.persistence.party.entity.PartyRoomEntity;
import com.partymakers.shareparty.data.persistence.party.impl.ExpensesRepositoryImpl;
import com.partymakers.shareparty.data.persistence.party.impl.InvitedFriendsRepositoryImpl;
import com.partymakers.shareparty.data.persistence.party.mapping.PartyRoomMapping;
import com.partymakers.shareparty.domain.party.entity.PartyRoom;
import com.partymakers.shareparty.domain.expenses.usecase.AddExpense;
import com.partymakers.shareparty.domain.party.usecase.AddPartyExpense;
import com.partymakers.shareparty.domain.party.usecase.CreatePartyRoom;
import com.partymakers.shareparty.domain.party.usecase.InviteFriend;
import com.partymakers.shareparty.domain.party.usecase.KickFiend;
import com.partymakers.shareparty.domain.party.usecase.RemovePartyExpense;
import com.partymakers.shareparty.domain.usecases.party.impl.AddPartyExpenseImpl;
import com.partymakers.shareparty.domain.usecases.party.impl.CreatePartyRoomImpl;
import com.partymakers.shareparty.domain.usecases.party.impl.InviteFriendImpl;
import com.partymakers.shareparty.domain.usecases.party.impl.KickFiendImpl;
import com.partymakers.shareparty.domain.usecases.party.impl.RemovePartyExpenseImpl;
import com.partymakers.shareparty.domain.usecases.party.port.InvitedFriendsRepository;
import com.partymakers.shareparty.domain.usecases.party.port.PartyExpensesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

@Configuration
@EnableJpaRepositories("com.partymakers.shareparty.data.persistence.party")
public class PartyRoomsConfiguration {

    static final PartyRoomMapping partyRoomMapping =  new PartyRoomMapping();

    @Bean
    PersistenceMapper<PartyRoomEntity, PartyRoom> partyPersistenceMapper()
    {
        return partyRoomMapping;
    }

    @Bean
    DomainMapper<PartyRoom, PartyRoomEntity> partyDomainMapper()
    {
        return partyRoomMapping;
    }

    @Bean
    CrudRepository<PartyRoom, Long> partyRoomRepository(JpaRepository<PartyRoomEntity, Long> repository)
    {
        return new RepositoryFacade<PartyRoom, PartyRoomEntity, Long>(repository, partyDomainMapper(), partyPersistenceMapper());
    }

    @Bean
    CreatePartyRoom createPartyRoom(@Autowired JpaRepository<PartyRoomEntity, Long> repository)
    {
        return new CreatePartyRoomImpl(partyRoomRepository(repository));
    }

    @Bean
    InvitedFriendsRepository invitedFriendsRepository(@Autowired InvitedFriendsPersistenceRepository repository)
    {
        return new InvitedFriendsRepositoryImpl(repository);
    }

    @Bean
    InviteFriend inviteFriend(@Autowired InvitedFriendsPersistenceRepository repository){
        return new InviteFriendImpl(invitedFriendsRepository(repository));
    }

    @Bean
    KickFiend kickFriend(@Autowired InvitedFriendsPersistenceRepository repository){
        return new KickFiendImpl(invitedFriendsRepository(repository));
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


}
