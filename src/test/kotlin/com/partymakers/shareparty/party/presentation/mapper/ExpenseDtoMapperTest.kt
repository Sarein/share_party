package com.partymakers.shareparty.party.presentation.mapper

import com.partymakers.shareparty.party.domain.entity.Expense
import com.partymakers.shareparty.party.presentation.dto.ExpenseDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ExpenseDtoMapperTest {

    private val mapper = ExpenseDtoMapper()

    @Test
    fun `map ExpenseDt with valid id EXPECT Expense`() {
        // given
        val expenseDto = ExpenseDto(
            id = "123",
            name = "Test Expense",
            cost = 100,
            count = 2.5
        )

        // when
        val result = mapper.toModel(expenseDto)

        // then
        assertThat(result).isEqualTo(
            Expense(
                id = 123L,
                name = "Test Expense",
                cost = 100,
                count = 2.5
            )
        )
    }

    @Test
    fun `map ExpenseDto with null id EXPECT Expense`() {
        // given
        val expenseDto = ExpenseDto(
            id = null,
            name = "Test Expense",
            cost = 100,
            count = 2.5
        )

        // when
        val result = mapper.toModel(expenseDto)

        // then
        assertThat(result).isEqualTo(
            Expense(
                id = null,
                name = "Test Expense",
                cost = 100,
                count = 2.5
            )
        )
    }

    @Test
    fun `map ExpenseDto with invalid id string EXPECT Expense`() {
        // given
        val expenseDto = ExpenseDto(
            id = "invalid",
            name = "Test Expense",
            cost = 100,
            count = 2.5
        )

        // when
        val result = mapper.toModel(expenseDto)

        // then
        assertThat(result).isEqualTo(
            Expense(
                id = null,
                name = "Test Expense",
                cost = 100,
                count = 2.5
            )
        )
    }
}