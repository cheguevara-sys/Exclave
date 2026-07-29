package com.github.shadowssocks.injector

import java.util.Random

object PayloadParser {

    fun format(
        template: String,
        host: String,
        port: String
    ): String {
        var payload = template

        // Core replacements (from the http-ssl-ssh-injector Python code)
        payload = payload.replace("[crlf]", "\r\n")
        payload = payload.replace("[crlf*2]", "\r\n\r\n")
        payload = payload.replace("[cr]", "\r")
        payload = payload.replace("[lf]", "\n")
        payload = payload.replace("[protocol]", "HTTP/1.0")
        payload = payload.replace("[ua]", "Dalvik/2.1.0 (Linux; U; Android 11)")
        payload = payload.replace("[host]", host)
        payload = payload.replace("[port]", port)
        payload = payload.replace("[host_port]", "$host:$port")
        payload = payload.replace("[https/host]", "https://$host")
        payload = payload.replace("[ssh]", "$host:$port")
        payload = payload.replace("[method]", "CONNECT")
        payload = payload.replace("[raw]", "CONNECT $host:$port HTTP/1.0\r\n\r\n")
        payload = payload.replace("[real_raw]", "CONNECT $host:$port HTTP/1.0\r\n\r\n")
        payload = payload.replace("[netData]", "CONNECT $host:$port HTTP/1.0")
        payload = payload.replace("[realData]", "CONNECT $host:$port HTTP/1.0")
        payload = payload.replace("mip", "127.0.0.1")
        payload = payload.replace("[auth]", "")

        // Handle [rotate=domain1;domain2;...] (not in the original Python script)
        payload = handleRotate(payload)

        // Handle [split] logic
        payload = handleSplit(payload)

        return payload
    }

    private fun handleRotate(input: String): String {
        val pattern = """\[rotate=([^\]]+)\]""".toRegex()
        return pattern.replace(input) { matchResult ->
            val domains = matchResult.groupValues[1].split(";")
            domains.random()
        }
    }

    private fun handleSplit(input: String): String {
        // Basic split handling
        if ("[split]" in input) {
            return input.replace("[split]", "")
        }
        return input
    }
}
