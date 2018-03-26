package x3.player.mru.session;

import x3.player.core.sdp.SdpInfo;

public class SessionInfo {

    private String sessionId;
    private long createdTime;

    private SessionServiceState serviceState;
    private long lastSentTime;

    private String conferenceId;
    private SdpInfo sdpInfo;

    private String localIpAddress;
    private int localPort;

    private String fromNo;
    private String toNo;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public SessionServiceState getServiceState() {
        return serviceState;
    }

    public void setServiceState(SessionServiceState serviceState) {
        this.serviceState = serviceState;
        this.lastSentTime = 0;
    }

    public long getLastSentTime() {
        return lastSentTime;
    }

    public void setLastSentTime(long lastSentTime) {
        this.lastSentTime = lastSentTime;
    }

    public void setLastSentTime() {
        this.lastSentTime = System.currentTimeMillis();
    }

    public String getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(String conferenceId) {
        this.conferenceId = conferenceId;
    }

    public SdpInfo getSdpInfo() {
        return sdpInfo;
    }

    public void setSdpInfo(SdpInfo sdpInfo) {
        this.sdpInfo = sdpInfo;
    }

    public String getLocalIpAddress() {
        return localIpAddress;
    }

    public void setLocalIpAddress(String localIpAddress) {
        this.localIpAddress = localIpAddress;
    }

    public int getLocalPort() {
        return localPort;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    public String getFromNo() {
        return fromNo;
    }

    public void setFromNo(String fromNo) {
        this.fromNo = fromNo;
    }

    public String getToNo() {
        return toNo;
    }

    public void setToNo(String toNo) {
        this.toNo = toNo;
    }
}
