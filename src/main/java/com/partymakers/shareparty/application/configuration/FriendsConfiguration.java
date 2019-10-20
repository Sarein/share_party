package com.partymakers.shareparty.application.configuration;

import com.partymakers.shareparty.data.persistence.DomainMapper;
import com.partymakers.shareparty.data.persistence.PersistenceMapper;
import com.partymakers.shareparty.data.persistence.friends.FriendPersistenceRepository;
import com.partymakers.shareparty.data.persistence.friends.entity.FriendEntity;
import com.partymakers.shareparty.data.persistence.friends.entity.FriendMapping;
import com.partymakers.shareparty.data.persistence.friends.impl.FriendsRepositoryImpl;
import com.partymakers.shareparty.domain.friends.entity.Friend;
import com.partymakers.shareparty.domain.friends.port.FriendsRepository;
import com.partymakers.shareparty.domain.friends.usecase.RegisterFriend;
import com.partymakers.shareparty.domain.friends.usecase.impl.RegisterFriendImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.partymakers.shareparty.data.persistence.friends")
public class FriendsConfiguration {

    static final FriendMapping friendMapping =  new FriendMapping();

    @Bean
    PersistenceMapper<FriendEntity, Friend> friendsPersistenceMapper()
    {
        return friendMapping;
    }

    @Bean
    DomainMapper<Friend, FriendEntity> friendsDomainMapper()
    {
        return friendMapping;
    }

    @Bean
    FriendsRepository friendsRepository(FriendPersistenceRepository repository)
    {
        return new FriendsRepositoryImpl(repository);
    }

    @Bean
    RegisterFriend registerFriend(@Autowired FriendPersistenceRepository repository)
    {
        return new RegisterFriendImpl(friendsRepository(repository));
    }

}
