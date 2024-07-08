package kotaseries

import kotlinx.datetime.Instant

internal fun Instant.toTimestamp() = (this.toEpochMilliseconds() / 1000).toString()

internal fun <K, V> mapOfNotNullValues(vararg entries: Pair<K, V>) = buildMap {
    entries.forEach { (k, v) ->
        if (v != null) {
            this[k] = v.toString()
        }
    }
}
