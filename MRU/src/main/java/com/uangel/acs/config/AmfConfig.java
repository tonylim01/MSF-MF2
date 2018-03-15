package com.uangel.acs.config;

import com.uangel.acs.common.StringValue;
import com.uangel.core.config.DefaultConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

public class AmfConfig extends DefaultConfig {

    private static final Logger logger = LoggerFactory.getLogger(AmfConfig.class);
    private static final String CONFIG_FILE = "amf.conf";

    private String rmqHost;
    private String rmqMcud;
    private String rmqUser, rmqPass;

    private SdpConfig sdpConfig;

    public AmfConfig() {

        super(CONFIG_FILE);

        boolean result = load();
        logger.info("Load config [{}] ... [{}]", CONFIG_FILE, StringValue.getOkFail(result));

        sdpConfig = new SdpConfig();

        if (result == true) {
            loadConfig();
        }
    }

    @Override
    public String getStrValue(String key, String defaultValue) {
        String value = super.getStrValue(key, defaultValue);

        logger.info("\tConfig key [{}] value [{}]", key, value);
        return value;
    }

    private void loadConfig() {

        try {
            rmqHost = getStrValue("RMQ_HOST", "localhost");
            rmqMcud = getStrValue("RMQ_MCUD", null);
            rmqUser = getStrValue("RMQ_USER", null);
            rmqPass = getStrValue("RMQ_PASS", null);

            String rawPasswd = getStrValue("RAW_PASS", null);
            if (rawPasswd != null) {
                String encoded = Base64.getEncoder().encodeToString(rawPasswd.getBytes());
                logger.warn("Encoding password: input [{}] encoded [{}]", rawPasswd, encoded);

            }

            if (rmqPass != null) {
                String decoded = new String(Base64.getDecoder().decode(rmqPass));
                logger.info("Decoding passowrd: input [{}] decoded [{}]", rmqPass, decoded);
                rmqPass = decoded;
            }

            String localHost = getStrValue("SDP_LOCAL_HOST", null);
            String localIp = getStrValue("SDP_LOCAL_IP", null);

            sdpConfig.setLocalHost(localHost);
            sdpConfig.setLocalIpAddress(localIp);

            for (int i = 0; ; i++) {
                String key = String.format("SDP_LOCAL_ATTR_%d", i);
                String attr = getStrValue(key, null);
                if (attr == null) {
                    break;
                }
                sdpConfig.addAttribute(attr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRmqHost() {
        return rmqHost;
    }

    public String getMcudName() {
        return rmqMcud;
    }

    public String getRmqUser() {
        return rmqUser;
    }

    public String getRmqPass() {
        return rmqPass;
    }

    public SdpConfig getSdpConfig() {
        return sdpConfig;
    }
}
