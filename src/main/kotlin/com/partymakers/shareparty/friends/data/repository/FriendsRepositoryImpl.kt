package com.partymakers.shareparty.friends.data.repository

import com.partymakers.shareparty.friends.domain.entity.Friend
import com.partymakers.shareparty.friends.domain.repository.FriendsRepository
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class FriendsRepositoryImpl(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) : FriendsRepository {

    override fun save(friend: Friend): Friend? {
        val values = mapOf(
            NAME to friend.name,
            NICK_NAME to friend.nickName,
            E_MAIL to friend.eMail,
        )

        return jdbcTemplate.queryForObject(UPDATE_FRIENDS_SQL, values, rowToFriendsMapper)
    }

    override fun findOneById(nickName: String): Friend? {
        val values = mapOf(
            NICK_NAME to nickName,
        )

        return jdbcTemplate.queryForObject(FIND_FRIENDS_BY_NICK_NAME_SQL, values, rowToFriendsMapper)
    }

    private companion object {
        private const val NAME = "name"
        private const val NICK_NAME = "nick_name"
        private const val E_MAIL = "e_mail"

        val rowToFriendsMapper = RowMapper { rs, _ ->
            Friend(
                nickName = rs.getString(NAME),
                name = rs.getString(NICK_NAME),
                eMail = rs.getString(E_MAIL),
            )
        }

        const val UPDATE_FRIENDS_SQL = """
            INSERT INTO friends ($NAME, $NICK_NAME, $E_MAIL)
            VALUES (:$NAME, :$NICK_NAME, :$E_MAIL)
            RETURNING *
        """

        const val FIND_FRIENDS_BY_NICK_NAME_SQL = """
            SELECT *
            FROM friends
            WHERE $NICK_NAME=:$NICK_NAME
            LIMIT 1
        """
    }
}