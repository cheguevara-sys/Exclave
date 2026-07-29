package io.nekohasekai.sagernet.fmt.ssh

import android.util.Log
import com.github.shadowssocks.injector.PayloadParser
import com.github.shadowssocks.injector.PreconnectedSocketFactory
import com.github.shadowssocks.injector.SshInjector
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import io.nekohasekai.sagernet.fmt.SSSHBean
import io.nekohasekai.sagernet.ktx.logs
import java.net.Socket

class SSHFmt {

    companion object {
        private const val TAG = "SSHFmt"
    }

    /**
     * Create an SSH connection with optional payload injection
     */
    fun createConnection(bean: SSSHBean): Session? {
        return try {
            // Check if payload injection is enabled
            if (bean.isUsePayload() && bean.getPayload().isNotEmpty()) {
                Log.d(TAG, "Payload injection enabled")
                connectWithPayload(bean)
            } else {
                Log.d(TAG, "Standard SSH connection (no payload)")
                connectStandard(bean)
            }
        } catch (e: Exception) {
            Log.e(TAG, "SSH connection failed", e)
            null
        }
    }

    /**
     * Connect with payload injection through remote proxy
     */
    private fun connectWithPayload(bean: SSSHBean): Session? {
        Log.d(TAG, "Connecting with payload injection...")
        Log.d(TAG, "Remote Proxy: ${bean.getRemoteProxy()}:${bean.getRemoteProxyPort()}")

        // 1. Inject payload through remote proxy
        val injector = SshInjector()
        val socket = injector.connectWithPayload(
            proxyHost = bean.getRemoteProxy(),
            proxyPort = bean.getRemoteProxyPort(),
            sshHost = bean.serverAddress,
            sshPort = bean.serverPort,
            payloadTemplate = bean.getPayload()
        )

        if (socket == null) {
            Log.e(TAG, "Payload injection failed - falling back to standard connection")
            return connectStandard(bean)
        }

        Log.d(TAG, "Payload injection successful, establishing SSH session...")

        // 2. Use the socket for SSH
        return try {
            val jsch = JSch()
            val session = jsch.getSession(bean.username, bean.serverAddress, bean.serverPort)
            session.setPassword(bean.password)
            session.setSocketFactory(PreconnectedSocketFactory(socket))

            // Disable strict host key checking for testing
            val config = java.util.Properties()
            config.put("StrictHostKeyChecking", "no")
            session.setConfig(config)

            session.connect(bean.timeout)

            Log.d(TAG, "SSH session established successfully with payload injection")
            session

        } catch (e: Exception) {
            Log.e(TAG, "SSH session establishment failed", e)
            socket.close()
            null
        }
    }

    /**
     * Standard SSH connection (without payload)
     */
    private fun connectStandard(bean: SSSHBean): Session? {
        Log.d(TAG, "Establishing standard SSH connection...")
        return try {
            val jsch = JSch()
            val session = jsch.getSession(bean.username, bean.serverAddress, bean.serverPort)
            session.setPassword(bean.password)

            // Disable strict host key checking for testing
            val config = java.util.Properties()
            config.put("StrictHostKeyChecking", "no")
            session.setConfig(config)

            session.connect(bean.timeout)

            Log.d(TAG, "SSH session established successfully")
            session

        } catch (e: Exception) {
            Log.e(TAG, "Standard SSH connection failed", e)
            null
        }
    }

    /**
     * Disconnect an SSH session
     */
    fun disconnect(session: Session?) {
        try {
            if (session != null && session.isConnected) {
                session.disconnect()
                Log.d(TAG, "SSH session disconnected")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error disconnecting SSH session", e)
        }
    }

    /**
     * Check if a session is connected
     */
    fun isConnected(session: Session?): Boolean {
        return session != null && session.isConnected
    }
}
