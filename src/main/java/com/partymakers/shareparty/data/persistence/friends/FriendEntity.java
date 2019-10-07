package com.partymakers.shareparty.data.persistence.friends;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "friends")
@Data
public class FriendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "e_mail")
    private String eMail;


    public FriendEntity(String name, String nickName, String eMail) {
        this.name = name;
        this.nickName = nickName;
        this.eMail = eMail;
    }

    public FriendEntity(int id, String name, String nickName, String eMail) {
        this.id  = id;
        this.name = name;
        this.nickName = nickName;
        this.eMail = eMail;
    }

}
