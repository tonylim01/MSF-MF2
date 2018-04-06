package x3.player.mru.surfif.messages;

import com.google.gson.annotations.SerializedName;

public class SurfMsgRtp {
    @SerializedName("local_udp_port")
    private int localUdpPort;
    @SerializedName("remote_udp_port")
    private int remoteUdpPort;
    @SerializedName("remote_ip")
    private String remoteIp;
    @SerializedName("in_payload_type")
    private int inPayloadType;
    @SerializedName("out_payload_type")
    private int outPayloadType;

    public int getLocalUdpPort() {
        return localUdpPort;
    }

    public void setLocalUdpPort(int localUdpPort) {
        this.localUdpPort = localUdpPort;
    }

    public int getRemoteUdpPort() {
        return remoteUdpPort;
    }

    public void setRemoteUdpPort(int remoteUdpPort) {
        this.remoteUdpPort = remoteUdpPort;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public int getInPayloadType() {
        return inPayloadType;
    }

    public void setInPayloadType(int inPayloadType) {
        this.inPayloadType = inPayloadType;
    }

    public int getOutPayloadType() {
        return outPayloadType;
    }

    public void setOutPayloadType(int outPayloadType) {
        this.outPayloadType = outPayloadType;
    }
}
