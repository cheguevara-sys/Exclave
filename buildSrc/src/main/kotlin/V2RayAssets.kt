import java.io.File

object V2RayAssets {
    fun downloadAndExtractV2RayCore(version: String, outputDir: File) {
        // Stub — no actual download
        outputDir.mkdirs()
    }

    fun parseV2RayConfig(configFile: File): Map<String, String> {
        return emptyMap()
    }
}