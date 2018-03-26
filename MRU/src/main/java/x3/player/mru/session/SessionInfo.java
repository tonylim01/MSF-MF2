package x3.player.mru.session;

import x3.player.core.sdp.SdpInfo;

public class SessionInfo {

    private String sessionId;
    private long createdTime;

    private SessionServiceState serviceState;
    private long lastSentTime;
    private long t2Time;
    private long t4Time;

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
        synchronized (this) {
            this.serviceState = serviceState;
            this.lastSentTime = 0;
            this.t2Time = 0;
            this.t4Time = 0;
        }
    }

    public long getLastSentTime() {
        synchronized (this) {
            return lastSentTime;
        }
    }

    public void setLastSentTime(long lastSentTime) {
        synchronized (this) {
            this.lastSentTime = lastSentTime;
        }
    }

    public void setLastSentTime() {
        setLastSentTime(System.currentTimeMillis());
    }

    public long getT2Time() {
        synchronized (this) {
            return t2Time;
        }
    }

    public void setT2Time(long t2Time) {
        synchronized (this) {
            this.t2Time = t2Time;
        }
    }

    public long getT4Time() {
        synchronized (this) {
            return t4Time;
        }
    }

    public void setT4Time(long t4Time) {
        this.t4Time = t4Time;
    }

    public void updateT2Time(long t2interval) {
        synchronized (this) {
            this.t2Time = System.currentTimeMillis() + t2interval;
        }
    }

    public void updateT4Time(long t4interval) {
        synchronized (this) {
            this.t4Time = System.currentTimeMillis() + t4interval;
        }
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
