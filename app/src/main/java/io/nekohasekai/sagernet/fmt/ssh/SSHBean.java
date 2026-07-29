package io.nekohasekai.sagernet.fmt.ssh;

public class SSHBean {
    // ─── Required existing fields ───
    public String serverAddress = "";
    public int serverPort = 22;
    public String username = "";
    public String password = "";
    public String privateKey = "";
    public String privateKeyPassphrase = "";
    public int timeout = 15000;

    // ─── Your new fields ───
    private boolean usePayload = false;
    private String payload = "";
    private String remoteProxy = "";
    private int remoteProxyPort = 80;

    // ─── Getters & Setters for new fields ───
    public boolean isUsePayload() {
        return usePayload;
    }

    public void setUsePayload(boolean usePayload) {
        this.usePayload = usePayload;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getRemoteProxy() {
        return remoteProxy;
    }

    public void setRemoteProxy(String remoteProxy) {
        this.remoteProxy = remoteProxy;
    }

    public int getRemoteProxyPort() {
        return remoteProxyPort;
    }

    public void setRemoteProxyPort(int remoteProxyPort) {
        this.remoteProxyPort = remoteProxyPort;
    }
}