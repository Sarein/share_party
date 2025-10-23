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
            E_MAIL to friend.mail,
        )

        return jdbcTemplate.queryForObject(UPDATE_FRIENDS_SQL, values, rowToFriendsMapper)
    }

    override fun findOneById(nickName: String): Friend? {
        val values = mapOf(
            NICK_NAME to nickName,
        )

        return jdbcTemplate.queryForObject(FIND_FRIENDS_BY_NICK_NAME_SQL, values, rowToFriendsMapper)
    }

    override fun deleteAll() {
        jdbcTemplate.update(DELETE_ALL_FRIENDS_SQL, emptyMap<String, Unit>())
    }

    private companion object {
        private const val NAME = "name"
        private const val NICK_NAME = "nick_name"
        private const val E_MAIL = "e_mail"

        val rowToFriendsMapper = RowMapper { rs, _ ->
            Friend(
                nickName = rs.getString(NICK_NAME),
                name = rs.getString(NAME),
                mail = rs.getString(E_MAIL),
            )
        }

        const val UPDATE_FRIENDS_SQL = """
            INSERT INTO friends ($NAME, $NICK_NAME, $E_MAIL)
            VALUES (:$NAME, :$NICK_NAME, :$E_MAIL)
            ON CONFLICT (nick_name) DO NOTHING
            RETURNING *
        """

        const val FIND_FRIENDS_BY_NICK_NAME_SQL = """
            SELECT *
            FROM friends
            WHERE $NICK_NAME=:$NICK_NAME
            LIMIT 1        
        """

        val DELETE_ALL_FRIENDS_SQL = """
            DELETE FROM friends  
        """.trimIndent()
    }
}