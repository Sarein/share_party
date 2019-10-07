package com.partymakers.shareparty;

import com.partymakers.shareparty.data.persistence.DomainMapper;
import com.partymakers.shareparty.data.persistence.PersistenceMapper;
import com.partymakers.shareparty.data.persistence.RepositoryFacade;
import com.partymakers.shareparty.data.persistence.party.PartyRoomEntity;
import com.partymakers.shareparty.data.persistence.party.mapping.PartyRoomMapping;
import com.partymakers.shareparty.domain.entity.PartyRoom;
import com.partymakers.shareparty.domain.usecases.party.CreatePartyRoom;
import com.partymakers.shareparty.domain.usecases.party.impl.CreatePartyRoomImpl;

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
    CrudRepository<PartyRoom, Long> partyRoomRepository(JpaRepository repository)
    {
        return new RepositoryFacade<PartyRoom, PartyRoomEntity, Long>(repository, partyDomainMapper(), partyPersistenceMapper());
    }

    @Bean
    CreatePartyRoom createPartyRoom(@Autowired JpaRepository repository)
    {
        return new CreatePartyRoomImpl(partyRoomRepository(repository));
    }
}
