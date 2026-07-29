import java.io.File

object RootCAAsserts {
    fun downloadRootCAAsserts(outputDir: File) {
        // Stub — no actual download
        outputDir.mkdirs()
    }

    fun getRootCAAsserts(): List<String> {
        return emptyList()
    }
}