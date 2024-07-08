package kotaseries.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal class StringAsListSerializer : KSerializer<List<String>> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("StringAsListSerializer", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): List<String> {
        if (!decoder.decodeNotNullMark()) {
            return emptyList()
        }

        val commaSeparatedString = decoder.decodeString()
        return commaSeparatedString.split(",").map { it.trim() }
    }

    override fun serialize(encoder: Encoder, value: List<String>) {
        if (value.isEmpty()) {
            encoder.encodeNull()
        } else {
            val commaSeparatedString = value.joinToString(", ")
            encoder.encodeString(commaSeparatedString)
        }
    }
}
