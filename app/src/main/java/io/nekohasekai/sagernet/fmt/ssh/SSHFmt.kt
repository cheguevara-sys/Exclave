package io.nekohasekai.sagernet.fmt

// ... existing imports ...
import com.github.shadowssocks.injector.SshInjector
import com.github.shadowssocks.injector.PreconnectedSocketFactory
import com.jcraft.jsch.JSch

class SSSHFmt {
    // ... existing code ...

    // Your existing connect() method
    private fun connect(bean: SSSHBean) {
        // 1. Check if payload is enabled
        if (bean.usePayload && bean.payload.isNotEmpty()) {
            // 2. Inject payload through remote proxy
            val injector = SshInjector()
            val socket = injector.connectWithPayload(
                proxyHost = bean.remoteProxy,      // From UI: e.g., "viton.com"
                proxyPort = bean.remoteProxyPort,  // From UI: e.g., 80
                sshHost = bean.serverAddress,
                sshPort = bean.serverPort,
                payloadTemplate = bean.payload
            )

            if (socket != null) {
                // 3. Use the socket for SSH
                val jsch = JSch()
                val session = jsch.getSession(bean.username, bean.serverAddress, bean.serverPort)
                session.setSocketFactory(PreconnectedSocketFactory(socket))
                session.setPassword(bean.password)
                session.connect()
                // ... rest of SSH setup ...
                return
            }
        }

        // 4. Fallback: Normal SSH connection (without payload)
        // ... existing SSH connection code ...
    }
}
