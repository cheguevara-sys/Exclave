package io.nekohasekai.sagernet.fmt.ssh

import android.util.Log
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session

// import com.github.shadowssocks.injector.PayloadParser   // Commented out
// import com.github.shadowssocks.injector.SshInjector      // Commented out
// import com.github.shadowssocks.injector.PreconnectedSocketFactory  // Commented out

class SSHFmt {

    companion object {
        private const val TAG = "SSHFmt"
    }

    fun createConnection(bean: SSSHBean): Session? {
        return try {
            // Payload injection is temporarily disabled
            connectStandard(bean)
        } catch (e: Exception) {
            Log.e(TAG, "SSH connection failed", e)
            null
        }
    }

    private fun connectStandard(bean: SSSHBean): Session? {
        Log.d(TAG, "Establishing standard SSH connection...")
        return try {
            val jsch = JSch()
            val session = jsch.getSession(bean.username, bean.serverAddress, bean.serverPort)
            session.setPassword(bean.password)

            val config = java.util.Properties()
            config.put("StrictHostKeyChecking", "no")
            session.setConfig(config)

            session.connect(bean.timeout)
            session
        } catch (e: Exception) {
            Log.e(TAG, "Standard SSH connection failed", e)
            null
        }
    }
}