package com.partymakers.shareparty.party.data.repository

import com.partymakers.shareparty.party.data.dto.PartyRoomDescriptionEntity
import com.partymakers.shareparty.party.data.dto.PartyRoomEntity
import com.partymakers.shareparty.party.data.mapper.EntityMapper
import com.partymakers.shareparty.party.data.mapper.PartyRoomMapper
import com.partymakers.shareparty.party.domain.entity.Expense
import com.partymakers.shareparty.party.domain.entity.PartyRoom
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import com.partymakers.shareparty.party.domain.repository.PartyRoomRepository
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
internal class PartyRoomRepositoryImpl(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
    private val entityMapper: EntityMapper,
    private val partyRoomMapper: PartyRoomMapper,
) : PartyRoomRepository {

    override fun create(partyName: String): Long {
        val parameters = mapOf(
            PARTY_ROOM_NAME to partyName,
        )

        return jdbcTemplate.queryForObject(
            ADD_PARTY_ROOM_SQL,
            parameters
        ) { rs, _ -> rs.getLong(PARTY_ROOM_ID) } ?: 0L
    }

    override fun addFriend(roomId: Long, friendNickName: String) {
        val parameters = mapOf(
            FRIENDS_PARTY_ROOM_ROOM_ID to roomId,
            FRIENDS_PARTY_ROOM_FRIEND_NICK_NAME to friendNickName,
        )

        jdbcTemplate.update(ADD_FRIEND_SQL, parameters)
    }

    override fun deleteFriend(roomId: Long, friendNickName: String) {
        val parameters = mapOf(
            FRIENDS_PARTY_ROOM_ROOM_ID to roomId,
            FRIENDS_PARTY_ROOM_FRIEND_NICK_NAME to friendNickName,
        )

        jdbcTemplate.update(DELETE_FRIEND_SQL, parameters)
    }

    override fun addFriends(roomId: Long, friendNickNames: List<String>): PartyRoom? {
        if (findById(roomId) == null) {
            throw NotFoundException("Room id: ${roomId} does not exist")
        }

        val parameters = mapOf(
            PARTY_ROOM_ID to roomId,
            FRIENDS_NICK_NAMES to friendNickNames
        )

        val dto = jdbcTemplate.query(ADD_FRIENDS_SQL, parameters, rowToPartyRoomMapper).firstOrNull()
        return dto?.let { partyRoomMapper.toEntity(it) }
    }

    override fun existsById(id: Long): Boolean =
        jdbcTemplate.queryForObject(EXISTS_BY_ID_SQL, mapOf(PARTY_ROOM_ID to id), Boolean::class.java)
            ?: false

    override fun addExpense(roomId: Long, expense: Expense): Long {
        val parameters = mapOf(
            EXPENSE_NAME to expense.name,
            EXPENSE_COST to expense.cost,
            EXPENSE_COUNT to expense.count,
            PARTY_ROOM_ID to roomId,
        )

        val longMapper = RowMapper { rs, _ ->
            rs.getLong("expense_id")
        }

        val expenseId = jdbcTemplate.query(ADD_EXPENSE_SQL, parameters, longMapper).firstOrNull()!!
        return expenseId
    }


    override fun deleteExpense(roomId: Long, expenseId: Long) {
        val parameters = mapOf(
            PARTY_ROOM_ID to roomId,
            EXPENSE_ID to expenseId,
        )

        jdbcTemplate.update(DELETE_EXPENSE_SQL, parameters)
    }

    override fun findById(roomId: Long): PartyRoom? {
        val parameters = mapOf(
            PARTY_ROOM_ID to roomId,
        )

        val dto = jdbcTemplate.query(GET_PARTY_ROOM_BY_ID_SQL, parameters, rowToPartyRoomMapper).firstOrNull()

        return dto?.let {
           val entutu =  partyRoomMapper.toEntity(it)
            entutu
        }
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

    override fun deleteAll() {
        jdbcTemplate.update(DELETE_ALL_ROOMS_SQL, emptyMap<String, Unit>())
    }

    private companion object {
        const val EXPENSE_ID = "expense_id"
        const val EXPENSE_NAME = "name"
        const val EXPENSE_COST = "cost"
        const val EXPENSE_COUNT = "count"
        const val FRIENDS_PARTY_ROOM_FRIEND_NICK_NAME = "friend_nick_name"
        const val FRIENDS_PARTY_ROOM_ROOM_ID = "party_room_id"
        const val PARTY_ROOM_NAME = "name"
        const val PARTY_ROOM_ID = "room_id"
        const val FRIENDS_NICK_NAMES = "friend_nick_names"


        val ADD_EXPENSE_SQL = """        
            INSERT INTO expenses (name, cost, count, room_id)
                VALUES (:$EXPENSE_NAME, :$EXPENSE_COST, :$EXPENSE_COUNT, :$PARTY_ROOM_ID)
            RETURNING expense_id          
        """.trimIndent()

        val DELETE_EXPENSE_SQL = """
            DELETE FROM expenses
                WHERE room_id = :$PARTY_ROOM_ID and expense_id = :$EXPENSE_ID
        """.trimIndent()

        val ADD_FRIEND_SQL = """           
             INSERT INTO party_room_friends (party_room_id, friend_nick_name)
             VALUES (:$FRIENDS_PARTY_ROOM_ROOM_ID, :$FRIENDS_PARTY_ROOM_FRIEND_NICK_NAME)
             ON CONFLICT DO NOTHING         
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
            RETURNING *
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

        val DELETE_ALL_ROOMS_SQL = """
            DELETE FROM party_room  
        """.trimIndent()

        val DELETE_FRIEND_SQL = """        
            DELETE FROM party_room_friends 
            WHERE party_room_id = :$FRIENDS_PARTY_ROOM_ROOM_ID AND friend_nick_name = :$FRIENDS_PARTY_ROOM_FRIEND_NICK_NAME                 
        """.trimIndent()
    //AND EXISTS (SELECT 1 FROM party_room_friends WHERE friend_nick_name = :$FRIENDS_PARTY_ROOM_FRIEND_NICK_NAME)

        const val EXISTS_BY_ID_SQL = """
            SELECT EXISTS(SELECT 1
            FROM party_room as pr
            WHERE pr.room_id = :$PARTY_ROOM_ID)
        """
    }
}