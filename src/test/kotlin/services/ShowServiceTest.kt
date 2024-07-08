package services

import getResourceAsString
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotaseries.Kotaseries
import kotaseries.exceptions.ShowException
import kotaseries.params.shows.DisplayParams
import kotaseries.params.shows.ListParams
import kotaseries.services.ShowService
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

class ShowServiceTest(
) : FunSpec({
    test("should return a Show") {
        val response = getResourceAsString("shows/display.json")

        val mockHttpClient = mockk<Kotaseries> {
            every { send("GET", "shows/display", DisplayParams(id = 1161)) } returns response
        }

        val sut = ShowService(mockHttpClient, Json {
            namingStrategy = JsonNamingStrategy.SnakeCase
        })

        val result = sut.display(DisplayParams(id = 1161))

        result.title shouldBe "Game of Thrones"
    }

    test("should throw a ShowException") {
        val response = getResourceAsString("shows/display_errors.json")

        val mockHttpClient = mockk<Kotaseries> {
            every { send("GET", "shows/display", DisplayParams()) } returns response
        }

        val sut = ShowService(mockHttpClient, Json {
            namingStrategy = JsonNamingStrategy.SnakeCase
        })

        val exception = shouldThrow<ShowException> {
            sut.display(DisplayParams())
        }
        exception.code shouldBe 4001
        exception.message shouldBe "No series found."
    }

    test("should return a list of Shows") {
        val response = getResourceAsString("shows/list.json")

        val mockHttpClient = mockk<Kotaseries> {
            every { send("GET", "shows/list", any()) } returns response
        }

        val sut = ShowService(mockHttpClient, Json {
            namingStrategy = JsonNamingStrategy.SnakeCase
        })

        val result = sut.list(ListParams(order = ListParams.Order.POPULARITY, limit = 5, platforms = listOf(1, 2), summary = false))
        result.count() shouldBe 5
        result[0].title shouldBe "Mon petit renne"
    }
})
