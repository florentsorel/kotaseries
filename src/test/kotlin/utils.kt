import java.nio.file.Files
import java.nio.file.Paths

fun getResourceAsString(file: String): String {
    val resource = {}.javaClass.classLoader.getResource("dataset/${file}")
        ?: throw IllegalArgumentException("file not found")

    return Files.readString(Paths.get(resource.toURI()))
}
