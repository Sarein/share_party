package com.partymakers.shareparty.data.persistence.friends.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "friends")
@Data
public class FriendEntity {

    @Id
    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "name")
    private String name;


    @Column(name = "e_mail")
    private String eMail;

    public FriendEntity()
    {

    }

    public FriendEntity(String name, String nickName, String eMail) {
        this.name = name;
        this.nickName = nickName;
        this.eMail = eMail;
    }

}
