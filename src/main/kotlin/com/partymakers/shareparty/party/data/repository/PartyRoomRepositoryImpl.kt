package com.partymakers.shareparty.party.data.repository

import com.partymakers.shareparty.party.data.dto.PartyRoomDescriptionEntity
import com.partymakers.shareparty.party.data.dto.PartyRoomEntity
import com.partymakers.shareparty.party.data.mapper.EntityMapper
import com.partymakers.shareparty.party.data.mapper.PartyRoomMapper
import com.partymakers.shareparty.party.domain.entity.Expense
import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
internal class PartyRoomRepositoryImpl(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
    private val entityMapper: EntityMapper,
    private val partyRoomMapper: PartyRoomMapper,
) : PartyRoomRepository {

    override fun create(partyName: String) {
        val parameters = mapOf(
            PARTY_ROOM_NAME to partyName,
        )

        jdbcTemplate.update(ADD_PARTY_ROOM_SQL, parameters)
    }

    override fun addFriend(roomId: Long, friendNickName: String): PartyRoom? {
        val parameters = mapOf(
            FRIENDS_PARTY_ROOM_ROOM_ID to roomId,
            FRIENDS_PARTY_ROOM_FRIEND_NICK_NAME to friendNickName,
        )

        val dto = jdbcTemplate.queryForObject(ADD_FRIEND_SQL, parameters, rowToPartyRoomMapper)
        return dto?.let { partyRoomMapper.toEntity(it) }
    }

    override fun deleteFriend(roomId: Long, friendNickName: String): PartyRoom? {
        val parameters = mapOf(
            FRIENDS_PARTY_ROOM_ROOM_ID to roomId,
            FRIENDS_PARTY_ROOM_FRIEND_NICK_NAME to friendNickName,
        )

        val dto = jdbcTemplate.queryForObject(DELETE_FRIEND_SQL, parameters, rowToPartyRoomMapper)
        return dto?.let { partyRoomMapper.toEntity(it) }
    }

    override fun addFriends(roomId: Long, friendNickNames: List<String>): PartyRoom? {
        val parameters = mapOf(
            PARTY_ROOM_ID to roomId,
            FRIENDS_NICK_NAMES to friendNickNames
        )

        val dto = jdbcTemplate.query(ADD_FRIENDS_SQL, parameters, rowToPartyRoomMapper).firstOrNull()
        return dto?.let { partyRoomMapper.toEntity(it) }
    }

    override fun addExpense(roomId: Long, expense: Expense): PartyRoom? {
        val parameters = mapOf(
            EXPENSE_COST to expense.cost,
            EXPENSE_COUNT to expense.count,
            PARTY_ROOM_ID to roomId,
        )

        val dto = jdbcTemplate.queryForObject(ADD_EXPENSE_SQL, parameters, rowToPartyRoomMapper)
        return dto?.let { partyRoomMapper.toEntity(it) }
    }

    override fun deleteExpense(roomId: Long, expenseId: Long): PartyRoom? {
        val parameters = mapOf(
            PARTY_ROOM_ID to roomId,
            EXPENSE_ID to expenseId,
        )

        val dto = jdbcTemplate.queryForObject(DELETE_EXPENSE_SQL, parameters, rowToPartyRoomMapper)
        return dto?.let { partyRoomMapper.toEntity(it) }
    }

    override fun findById(roomId: Long): PartyRoom? {
        val parameters = mapOf(
            PARTY_ROOM_ID to roomId,
        )

        val dto = jdbcTemplate.query(GET_PARTY_ROOM_BY_ID_SQL, parameters, rowToPartyRoomMapper).firstOrNull()

        return dto?.let { partyRoomMapper.toEntity(it) }
    }

    val rowToPartyRoomMapper = RowMapper { rs, _ ->

        val description = PartyRoomDescriptionEntity(
            id = rs.getLong(PARTY_ROOM_ID),
            name = rs.getString(PARTY_ROOM_NAME)
        )

        val friends = entityMapper.parseFriendList(rs.getString("friends"))
        val expense = entityMapper.parseExpensesList(rs.getString("expenses"))
        PartyRoomEntity(
            description = description,
            friends = friends.toSet(),
            expenses = expense,
        )
    }

    override fun findAll(): List<PartyRoom> {
        val dto = jdbcTemplate.query(GET_ALL_PARTY_ROOMS_SQL, rowToPartyRoomMapper)

        return dto.map(partyRoomMapper::toEntity)
    }

    override fun deleteById(roomId: Long) {
        val parameters = mapOf(
            PARTY_ROOM_ID to roomId,
        )

        jdbcTemplate.update(DELETE_ROOM_BY_ID_SQL, parameters)
    }

    private companion object {
        const val EXPENSE_ID = "expense_id"
        const val EXPENSE_NAME = "expense_name"
        const val EXPENSE_COST = "cost"
        const val EXPENSE_COUNT = "count"
        const val FRIENDS_PARTY_ROOM_FRIEND_NICK_NAME = "friend_nick_name"
        const val FRIENDS_PARTY_ROOM_ROOM_ID = "party_room_id"
        const val PARTY_ROOM_NAME = "name"
        const val PARTY_ROOM_ID = "room_id"
        const val FRIENDS_NICK_NAMES = "friend_nick_names"


        val ADD_EXPENSE_SQL = """
            WITH inserted_expense AS (
                INSERT INTO expenses (name, cost, count, room_id)
                VALUES (:$EXPENSE_NAME, :$EXPENSE_COST, :$EXPENSE_COUNT, :$PARTY_ROOM_ID)
                RETURNING room_id
            )
            SELECT
                pr.room_id,
                pr.name,
                (SELECT COALESCE(json_agg(room_friends), '[]'::json)
                FROM party_room_friends prf
                JOIN friends room_friends
                    ON prf.friend_nick_name = room_friends.nick_name
                WHERE prf.party_room_id = pr.room_id) as friends,
                (SELECT COALESCE(json_agg(room_expenses), '[]'::json)
                FROM (
                    SELECT
                        e.expense_id,
                        e.name,
                        e.cost,
                        e.count
                    FROM expenses e
                    WHERE e.room_id = pr.room_id
                ) as room_expenses) as expenses
            FROM party_room as pr
            WHERE
                pr.room_id = (SELECT room_id FROM inserted_expense)
        """.trimIndent()

        val DELETE_EXPENSE_SQL = """
            WITH delete_expense AS (
                DELETE FROM expense 
                WHERE room_id = :$PARTY_ROOM_ID and expense_id = :$EXPENSE_ID
                RETURNING room_id
            )
            SELECT
                pr.room_id,
                pr.name,
                (SELECT COALESCE(json_agg(room_friends), '[]'::json)
                FROM party_room_friends prf
                JOIN friends room_friends
                    ON prf.friend_nick_name = room_friends.nick_name
                WHERE prf.party_room_id = pr.room_id) as friends,
                (SELECT COALESCE(json_agg(room_expenses), '[]'::json)
                FROM (
                    SELECT
                        e.expense_id,
                        e.name,
                        e.cost,
                        e.count
                    FROM expenses e
                    WHERE e.room_id = pr.room_id
                ) as room_expenses) as expenses
            FROM party_room as pr
            WHERE
                pr.room_id = (SELECT room_id FROM delete_expense)
        """.trimIndent()

        val ADD_FRIEND_SQL = """
            WITH add_friend AS (
                INSERT INTO party_room_friends (party_room_id, friend_nick_name)
                VALUES (:$FRIENDS_PARTY_ROOM_ROOM_ID, :$FRIENDS_PARTY_ROOM_FRIEND_NICK_NAME)
                ON CONFLICT DO NOTHING
                RETURNING room_id
            )
            SELECT
                pr.room_id,
                pr.name,
                (SELECT COALESCE(json_agg(room_friends), '[]'::json)
                FROM party_room_friends prf
                JOIN friends room_friends
                    ON prf.friend_nick_name = room_friends.nick_name
                WHERE prf.party_room_id = pr.room_id) as friends,
                (SELECT COALESCE(json_agg(room_expenses), '[]'::json)
                FROM (
                    SELECT
                        e.expense_id,
                        e.name,
                        e.cost,
                        e.count
                    FROM expenses e
                    WHERE e.room_id = pr.room_id
                ) as room_expenses) as expenses
            FROM party_room as pr
            WHERE
                pr.room_id = (SELECT room_id FROM add_friend)
        """.trimIndent()

        val ADD_FRIENDS_SQL = """
             WITH new_friends AS (
                INSERT INTO party_room_friends (party_room_id, friend_nick_name)
                SELECT :$PARTY_ROOM_ID, unnest(:$FRIENDS_NICK_NAMES)
                ON CONFLICT (party_room_id, friend_nick_name) DO NOTHING
            )
            SELECT
                pr.room_id,
                pr.name,
                (SELECT COALESCE(json_agg(room_friends), '[]'::json)
                FROM party_room_friends prf
                JOIN friends room_friends
                    ON prf.friend_nick_name = room_friends.nick_name
                WHERE prf.party_room_id = pr.room_id) as friends,
                (SELECT COALESCE(json_agg(room_expenses), '[]'::json)
                FROM (
                    SELECT
                        e.expense_id,
                        e.name,
                        e.cost,
                        e.count
                    FROM expenses e
                    WHERE e.room_id = pr.room_id
                ) as room_expenses) as expenses
            FROM party_room as pr
            WHERE pr.room_id = :$PARTY_ROOM_ID
        """.trimIndent()

        val ADD_PARTY_ROOM_SQL = """
            INSERT INTO party_room (name)
            VALUES(:$PARTY_ROOM_NAME)
        """.trimIndent()

        val GET_PARTY_ROOM_BY_ID_SQL = """          
            SELECT
                pr.room_id,
                pr.name,
                (SELECT COALESCE(json_agg(room_friends), '[]'::json)
                FROM party_room_friends prf
                JOIN friends room_friends 
                    ON prf.friend_nick_name = room_friends.nick_name
                WHERE prf.party_room_id = pr.room_id) as friends,
                (SELECT COALESCE(json_agg(room_expenses), '[]'::json)
                FROM (
                    SELECT
                        e.expense_id,
                        e.name,
                        e.cost,
                        e.count
                    FROM expenses e                
                    WHERE e.room_id = pr.room_id
                ) as room_expenses) as expenses                                                
            FROM party_room as pr
            WHERE 
                pr.room_id = :$PARTY_ROOM_ID 
        """.trimIndent()

        val GET_ALL_PARTY_ROOMS_SQL = """
            SELECT
                pr.room_id,
                pr.name,
                (SELECT COALESCE(json_agg(room_friends), '[]'::json)
                FROM party_room_friends prf
                JOIN friends room_friends 
                    ON prf.friend_nick_name = room_friends.nick_name
                WHERE prf.party_room_id = pr.room_id) as friends,
                (SELECT COALESCE(json_agg(room_expenses), '[]'::json)
                FROM (
                    SELECT
                        e.expense_id,
                        e.name,
                        e.cost,
                        e.count
                    FROM expenses e
                    WHERE e.room_id = pr.room_id
                ) as room_expenses) as expenses                                                
            FROM party_room as pr                             
        """.trimIndent()

        val DELETE_ROOM_BY_ID_SQL = """
            DELETE FROM party_room 
            WHERE room_id = :$PARTY_ROOM_ID
        """.trimIndent()

        val DELETE_FRIEND_SQL ="""
            WITH delete_friend AS (
                DELETE FROM party_room_friends 
                WHERE party_room_id = :$FRIENDS_PARTY_ROOM_ROOM_ID AND friend_nick_name = :$FRIENDS_PARTY_ROOM_FRIEND_NICK_NAME
                RETURNING room_id
            )
            SELECT
                pr.room_id,
                pr.name,
                (SELECT COALESCE(json_agg(room_friends), '[]'::json)
                FROM party_room_friends prf
                JOIN friends room_friends
                    ON prf.friend_nick_name = room_friends.nick_name
                WHERE prf.party_room_id = pr.room_id) as friends,
                (SELECT COALESCE(json_agg(room_expenses), '[]'::json)
                FROM (
                    SELECT
                        e.expense_id,
                        e.name,
                        e.cost,
                        e.count
                    FROM expenses e
                    WHERE e.room_id = pr.room_id
                ) as room_expenses) as expenses
            FROM party_room as pr
            WHERE
                pr.room_id = (SELECT room_id FROM delete_friend)
        """.trimIndent()
    }
}