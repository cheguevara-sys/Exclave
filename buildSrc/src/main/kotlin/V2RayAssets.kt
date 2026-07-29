import org.w3c.dom.Element
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.zip.ZipFile
import javax.xml.parsers.DocumentBuilderFactory

object V2RayAssets {

    fun downloadAndExtractV2RayCore(version: String, outputDir: File) {
        val url = "https://github.com/v2fly/v2ray-core/releases/download/v$version/v2ray-linux-64.zip"
        val zipFile = File(outputDir, "v2ray-core.zip")

        // Download
        downloadFile(url, zipFile)

        // Extract
        ZipFile(zipFile).use { zip ->
            zip.entries().asSequence().forEach { entry ->
                val targetFile = File(outputDir, entry.name)
                if (entry.isDirectory) {
                    targetFile.mkdirs()
                } else {
                    targetFile.parentFile?.mkdirs()
                    zip.getInputStream(entry).use { input ->
                        FileOutputStream(targetFile).use { output ->
                            input.copyTo(output)
                        }
                    }
                }
            }
        }

        zipFile.delete()
    }

    private fun downloadFile(urlString: String, outputFile: File) {
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connect()

        val inputStream = connection.inputStream
        val outputStream = FileOutputStream(outputFile)

        val buffer = ByteArray(4096)
        var bytesRead: Int  // ← Fixed: Variable declared but not initialized

        try {
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {  // ← Fixed: bytesRead initialized here
                outputStream.write(buffer, 0, bytesRead)
            }
        } finally {
            inputStream.close()
            outputStream.close()
        }
    }

    fun parseV2RayConfig(configFile: File): Map<String, String> {
        val result = mutableMapOf<String, String>()
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val document = builder.parse(configFile)

        val root = document.documentElement
        val nodes = root.childNodes

        for (i in 0 until nodes.length) {
            val node = nodes.item(i)
            if (node is Element) {
                val tagName = node.localName  // ← Fixed: Use localName instead of tagName
                val textContent = node.textContent ?: ""
                result[tagName] = textContent
            }
        }

        return result
    }
}