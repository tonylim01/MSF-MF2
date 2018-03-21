package x3.player.mru.rmqif.handler;

import x3.player.mru.AppInstance;
import x3.player.mru.config.SdpConfig;
import x3.player.mru.rmqif.handler.base.RmqIncomingMessageHandler;
import x3.player.mru.rmqif.types.RmqMessage;
import x3.player.mru.rmqif.types.RmqMessageType;
import x3.player.mru.session.SessionInfo;
import x3.player.mru.session.SessionManager;
import x3.player.mru.simulator.UdpRelayManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RmqProcInboundGetAnswerReq extends RmqIncomingMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(RmqProcInboundGetAnswerReq.class);

    @Override
    public boolean handle(RmqMessage msg) {
        if (msg == null || msg.getHeader() == null) {
            logger.error("[{}] Invalid message");
            return false;
        }

        logger.info("[{}] InboundGetAnswerReq", msg.getSessionId());

        if (msg.getSessionId() == null) {
            logger.error("[{}] No sessionId found");
            sendResponse(msg.getSessionId(), msg.getHeader().getTransactionId(), msg.getHeader().getMsgFrom(),
                    RmqMessageType.RMQ_MSG_COMMON_REASON_CODE_WRONG_PARAM,
                    "NO SESSION ID");
            return  false;
        }

        allocLocalResource(msg.getSessionId());

        sendResponse(msg.getSessionId(), msg.getHeader().getTransactionId(), msg.getHeader().getMsgFrom());

        return false;
    }

    @Override
    public void sendResponse(String sessionId, String transactionId, String queueName, int reasonCode, String reasonStr) {

        RmqProcInboundGetAnswerRes res = new RmqProcInboundGetAnswerRes(sessionId, transactionId);

        res.setReasonCode(reasonCode);
        res.setReasonStr(reasonStr);

        if (res.send(queueName) == false) {
            // TODO
        }
    }

    /**
     * Allocates a local media resource to receive RTP packets
     * @param sessionId
     * @return
     */
    private boolean allocLocalResource(String sessionId) {
        if (sessionId == null) {
            return false;
        }

        SessionInfo sessionInfo = SessionManager.getInstance().getSession(sessionId);
        if (sessionInfo == null) {
            logger.error("[{}] No sessionInfo found", sessionId);
            return false;
        }

        //
        // TODO
        //
        // Start of Demo Service
        SdpConfig config = AppInstance.getInstance().getConfig().getSdpConfig();
        sessionInfo.setLocalIpAddress(config.getLocalIpAddress());

        UdpRelayManager udpRelayManager = UdpRelayManager.getInstance();
        int localPort = udpRelayManager.getNextLocalPort();

        sessionInfo.setLocalPort(localPort);

        logger.debug("[{}] Alloc local media: ip [{}] port [{}]", sessionId,
                sessionInfo.getLocalIpAddress(), sessionInfo.getLocalPort());
        // End of Demo service

        return true;
    }
}

