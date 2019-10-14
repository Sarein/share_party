package com.partymakers.shareparty.application.configuration;

import com.partymakers.shareparty.data.persistence.DomainMapper;
import com.partymakers.shareparty.data.persistence.PersistenceMapper;
import com.partymakers.shareparty.data.persistence.RepositoryFacade;
import com.partymakers.shareparty.data.persistence.friends.entity.FriendEntity;
import com.partymakers.shareparty.data.persistence.friends.entity.FriendMapping;
import com.partymakers.shareparty.domain.entity.Friend;
import com.partymakers.shareparty.domain.usecases.friends.RegisterFriend;
import com.partymakers.shareparty.domain.usecases.friends.impl.RegisterFriendImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

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
    CrudRepository<Friend, String> friendsRepository(JpaRepository<FriendEntity, String> repository)
    {
        return new RepositoryFacade<>(repository, friendsDomainMapper(), friendsPersistenceMapper());
    }

    @Bean
    RegisterFriend registerFriend(@Autowired JpaRepository<FriendEntity, String> repository)
    {
        return new RegisterFriendImpl(friendsRepository(repository));
    }

}
