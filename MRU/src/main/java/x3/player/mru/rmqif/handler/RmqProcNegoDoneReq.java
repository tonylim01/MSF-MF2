package x3.player.mru.rmqif.handler;

import x3.player.core.sdp.SdpParser;
import x3.player.mru.AppInstance;
import x3.player.mru.config.AmfConfig;
import x3.player.mru.rmqif.handler.base.RmqIncomingMessageHandler;
import x3.player.mru.rmqif.messages.NegoDoneReq;
import x3.player.mru.rmqif.types.RmqMessage;
import x3.player.mru.rmqif.types.RmqMessageType;
import x3.player.mru.room.RoomInfo;
import x3.player.mru.room.RoomManager;
import x3.player.mru.session.SessionInfo;
import x3.player.mru.session.SessionManager;
import x3.player.mru.simulator.UdpRelayManager;
import x3.player.mru.rmqif.module.RmqData;
import x3.player.core.sdp.SdpInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RmqProcNegoDoneReq extends RmqIncomingMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(RmqProcNegoDoneReq.class);

    @Override
    public boolean handle(RmqMessage msg) {
        if (msg == null || msg.getHeader() == null) {
            logger.error("[{}] Invalid message");
            return false;
        }

        if (msg.getSessionId() == null) {
            logger.error("[{}] No sessionId found");
            sendResponse(msg.getSessionId(), msg.getHeader().getTransactionId(), msg.getHeader().getMsgFrom(),
                    RmqMessageType.RMQ_MSG_COMMON_REASON_CODE_WRONG_PARAM,
                    "NO SESSION ID");
            return  false;
        }

        RmqData<NegoDoneReq> data = new RmqData<>(NegoDoneReq.class);
        NegoDoneReq req = data.parse(msg);

        if (req == null) {
            logger.error("[{}] NegoDoneReq: parsing failed", msg.getSessionId());
            sendResponse(msg.getSessionId(), msg.getHeader().getTransactionId(), msg.getHeader().getMsgFrom(),
                    RmqMessageType.RMQ_MSG_COMMON_REASON_CODE_FAILURE,
                    "PARSING FAILURE");
            return false;
        }

        logger.info("[{}] NegoDoneReq: sdp [{}]", msg.getSessionId(), req.getSdp());

        if (req.getSdp() != null) {
            SdpInfo sdpInfo = SdpParser.parseSdp(req.getSdp());

            SessionInfo sessionInfo = SessionManager.getInstance().getSession(msg.getSessionId());
            if (sessionInfo == null) {
                sendResponse(msg.getSessionId(), msg.getHeader().getTransactionId(), msg.getHeader().getMsgFrom(),
                        RmqMessageType.RMQ_MSG_COMMON_REASON_CODE_FAILURE,
                        "NO SESSION");
                return false;
            }

            sessionInfo.setSdpInfo(sdpInfo);
        }

        openLocalResource(msg.getSessionId());
        sendStartServiceReq(msg.getSessionId());

        sendResponse(msg.getSessionId(), msg.getHeader().getTransactionId(), msg.getHeader().getMsgFrom());

        return false;
    }

    @Override
    public void sendResponse(String sessionId, String transactionId, String queueName, int reasonCode, String reasonStr) {

        RmqProcNegoDoneRes res = new RmqProcNegoDoneRes(sessionId, transactionId);

        res.setReasonCode(reasonCode);
        res.setReasonStr(reasonStr);

        if (res.send(queueName) == false) {
            // TODO
        }
    }

    /**
     * Opens local udp server to receive rtp packets
     * @param sessionId
     * @return
     */
    private boolean openLocalResource(String sessionId) {
        SessionInfo sessionInfo = SessionManager.findSession(sessionId);

        if (sessionInfo == null) {
            logger.warn("[{}] No session found", sessionId);
            return false;
        }

        //
        // TODO
        //
        // Start of Demo Service
        UdpRelayManager udpRelayManager = UdpRelayManager.getInstance();
        udpRelayManager.openServer(sessionId, sessionInfo.getLocalPort());

        RoomInfo roomInfo = RoomManager.getInstance().getRoomInfo(sessionInfo.getConferenceId());
        if (roomInfo == null) {
            logger.error("[{}] No roomInfo found", sessionId);
            return false;
        }

        String otherSessionId = roomInfo.getOtherSession(sessionId);
        if (otherSessionId != null) {
            logger.info("[{}] Connected to session [{}]", sessionId, otherSessionId);

            SessionInfo otherSessionInfo = SessionManager.findSession(otherSessionId);
            if (otherSessionInfo == null) {
                logger.warn("[{}] No sessionInfo found", otherSessionId);
                return false;
            }

            SdpInfo otherRemoteSdpInfo = otherSessionInfo.getSdpInfo();
            udpRelayManager.openClient(sessionId, otherRemoteSdpInfo.getRemoteIp(), otherRemoteSdpInfo.getRemotePort());

            logger.debug("[{}] Make connection: local [{}] to [{}:{}]", sessionId,
                    sessionInfo.getLocalPort(), otherRemoteSdpInfo.getRemoteIp(), otherRemoteSdpInfo.getRemotePort());

        }
        // End of Demo service

        return true;
    }

    /**
     * Sends ServiceStartReq to ACSWF
     * @param sessionId
     * @return
     */
    private boolean sendStartServiceReq(String sessionId) {
        SessionInfo sessionInfo = SessionManager.findSession(sessionId);

        if (sessionInfo == null) {
            logger.warn("[{}] No session found", sessionId);
            return false;
        }

        AmfConfig config = AppInstance.getInstance().getConfig();
        if (config == null) {
            logger.error("[{}] Null config", sessionId);
            return false;
        }

        RmqProcStartServiceReq req = new RmqProcStartServiceReq(sessionId, null);
        return req.send(config.getRmqAcswf());
    }
}

