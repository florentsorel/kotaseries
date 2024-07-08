package kotaseries.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

class AliasesSerializer : KSerializer<Map<Int, String>> {
    override val descriptor: SerialDescriptor = MapSerializer(Int.serializer(), String.serializer()).descriptor
    override fun deserialize(decoder: Decoder): Map<Int, String> {
        val input = decoder as JsonDecoder
        return when (val jsonElement = input.decodeJsonElement()) {
            is JsonArray -> {
                if (jsonElement.isEmpty()) {
                    emptyMap()
                } else {
                    throw SerializationException("Expected an empty array or a map, but got a non-empty array")
                }
            }
            is JsonObject -> {
               jsonElement.toMap()
            }
            else -> {
                throw SerializationException("Expected a JSON array or a JSON object")
            }
        }
    }

    override fun serialize(encoder: Encoder, value: Map<Int, String>) {
        encoder.encodeSerializableValue(MapSerializer(Int.serializer(), String.serializer()), value)
    }

    private fun JsonObject.toMap(): Map<Int, String> {
        return this.map { (key, value) ->
            key.toInt() to value.jsonPrimitive.content
        }.toMap()
    }
}
