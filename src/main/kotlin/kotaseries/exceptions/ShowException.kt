package kotaseries.exceptions

data class ShowException(val code: Int, override val message: String) : RuntimeException(message)
