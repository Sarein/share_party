package com.partymakers.shareparty.domain.usecases.expenses.port;

public interface ExpensesRepository {
    void expencRepository(String nickName, Long partyRoom);
    void deleteFriend(String nickName, Long partyRoom);
}
