package com.partymakers.shareparty.presenter;

import com.partymakers.shareparty.data.persistence.PartyRepositoryImpl;
import com.partymakers.shareparty.domain.usecases.party.CreatePartyRoom;
import com.partymakers.shareparty.domain.usecases.party.impl.CreatePartyRoomImpl;
import com.partymakers.shareparty.domain.usecases.port.PartyRoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.partymakers.shareparty.data.persistence")
public class ConfigurePartyController {

    @Bean
    PartyRoomRepository partyRoomRepository(JpaRepository repository)
    {
        return new PartyRepositoryImpl(repository);
    }

    @Bean
    CreatePartyRoom createPartyRoom(@Autowired JpaRepository repository)
    {
        return new CreatePartyRoomImpl(partyRoomRepository(repository));
    }
}
