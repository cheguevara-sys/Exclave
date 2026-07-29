package com.github.shadowssocks.injector

import java.net.Socket
import java.io.InputStream

class SshInjector {

    fun connectWithPayload(
        proxyHost: String,
        proxyPort: Int,
        sshHost: String,
        sshPort: Int,
        payloadTemplate: String
    ): Socket? {
        return try {
            // 1. Connect to remote proxy
            val proxySocket = Socket(proxyHost, proxyPort)
            val outputStream = proxySocket.getOutputStream()

            // 2. Format the payload
            val formattedPayload = PayloadParser.format(
                template = payloadTemplate,
                host = sshHost,
                port = sshPort.toString()
            )

            // 3. Send the payload
            outputStream.write(formattedPayload.toByteArray())
            outputStream.flush()

            // 4. Read response (like the Python get_resp() method)
            val inputStream = proxySocket.getInputStream()
            val buffer = ByteArray(1024)
            val bytesRead = inputStream.read(buffer)
            val response = String(buffer, 0, bytesRead)

            // 5. Check if response indicates success
            if (response.contains("HTTP/1.1 200") ||
                response.contains("Connection established") ||
                response.contains("SSH")) {
                // Success! Return the socket for SSH to use
                return proxySocket
            }

            proxySocket.close()
            null

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
