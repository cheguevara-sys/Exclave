package com.github.shadowssocks.injector

import com.jcraft.jsch.SocketFactory
import java.net.Socket

class PreconnectedSocketFactory(
    private val socket: Socket
) : SocketFactory {

    override fun createSocket(host: String, port: Int): Socket {
        return socket
    }

    override fun createSocket(
        host: String,
        port: Int,
        localHost: String,
        localPort: Int,
        timeout: Int
    ): Socket {
        return socket
    }
}
