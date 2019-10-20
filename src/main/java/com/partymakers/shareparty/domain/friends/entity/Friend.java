package com.partymakers.shareparty.domain.friends.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"name", "nickName", "eMail"})
public class Friend {
    final String name;
    final String nickName;
    final String eMail;
}
