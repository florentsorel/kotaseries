package serializers

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotaseries.models.Show
import kotaseries.serializers.StringAsListSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy


@Serializable
data class DataClassTest(
    @Serializable(with = StringAsListSerializer::class)
    val tags: List<String>
)

class StringAsListSerializerTest : StringSpec ({
    val json = Json {
        namingStrategy = JsonNamingStrategy.SnakeCase
    }

    "should deserialize a comma-separated string into a list" {
        val input = """{"tags": "a, b, c"}"""
        val result = json.decodeFromString<DataClassTest>(input)
        result shouldBe DataClassTest(listOf("a", "b", "c"))
    }

    "should serialize a list into a comma-separated string" {
        val input = DataClassTest(listOf("a", "b", "c"))
        val result = json.encodeToString(input)
        result shouldBe """{"tags":"a, b, c"}"""
    }

    "should deserialize null value into an empty list" {
        val input = """{"tags": null}"""
        val result = json.decodeFromString<DataClassTest>(input)
        result shouldBe DataClassTest(emptyList())
    }

    "should serialize an empty list into a null value" {
        val input = DataClassTest(emptyList())
        val result = json.encodeToString(input)
        result shouldBe """{"tags":null}"""
    }
})
