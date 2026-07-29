package io.nekohasekai.sagernet.fmt;

public class SSSHBean {
    // ... existing fields ...
    private boolean usePayload = false;
    private String payload = "";
    private String remoteProxy = "";
    private int remoteProxyPort = 80;

    // Getters and Setters
    public boolean isUsePayload() { return usePayload; }
    public void setUsePayload(boolean usePayload) { this.usePayload = usePayload; }

    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }

    public String getRemoteProxy() { return remoteProxy; }
    public void setRemoteProxy(String remoteProxy) { this.remoteProxy = remoteProxy; }

    public int getRemoteProxyPort() { return remoteProxyPort; }
    public void setRemoteProxyPort(int remoteProxyPort) { this.remoteProxyPort = remoteProxyPort; }
}
