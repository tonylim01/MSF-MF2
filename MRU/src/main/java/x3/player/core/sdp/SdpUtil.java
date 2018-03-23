package x3.player.core.sdp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SdpUtil {

    private static final Pattern rtpmapPattern = Pattern.compile("rtpmap:(\\d+) (.+)");

    /**
     * Parses "a=" attribute string
     * @param attributeStr
     * @return
     */
    public static SdpAttribute parseAttribute(String attributeStr) {
        if (attributeStr == null) {
            return null;
        }

        SdpAttribute attribute = new SdpAttribute();
        Matcher m = rtpmapPattern.matcher(attributeStr);
        if (m.find() && m.groupCount() >= 2) {
            attribute.setPayloadId(Integer.valueOf(m.group(1)));
            attribute.setDescription(m.group(2));
        }
        else {
            attribute.setDescription(attributeStr);
        }

        return attribute;
    }

    /**
     * Generates an attribute string which following after "a=" line
     * The result doesn't include "a="
     * @return
     */
    public static String getAttributeString(SdpAttribute attr) {
        StringBuilder sb = new StringBuilder(48);
        if (attr.getName() != null) {
            sb.append(attr.getName());
            sb.append(":");
        }

        if (attr.getPayloadId() != SdpAttribute.PAYLOADID_NONE) {
            sb.append(attr.getPayloadId());
            sb.append(" ");
        }

        if (attr.getDescription() != null) {
            sb.append(attr.getDescription());
        }

        return sb.toString();
    }

}
