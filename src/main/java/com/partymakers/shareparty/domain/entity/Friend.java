package com.partymakers.shareparty.domain.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Friend {
    final String name;
    final String nickName;
    final String eMail;
}
