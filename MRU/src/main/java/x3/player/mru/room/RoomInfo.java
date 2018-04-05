package x3.player.mru.room;

import java.util.Vector;

public class RoomInfo {
    private String roomId;

    private Vector<String> sessions;

    private int groupId;
    private int mixerId;

    public RoomInfo() {
        this.sessions = new Vector<>();
        groupId = -1;
        mixerId = -1;
    }

    public boolean addSession(String sessionId) {
        if (sessions.contains(sessionId)) {
            return false;
        }

        sessions.add(sessionId);
        return true;
    }

    public void removeSession(String sessionId) {
        if (sessions.contains(sessionId)) {
            sessions.remove(sessionId);
        }
    }

    public boolean hasSession(String sessionId) {
        return sessions.contains(sessionId);
    }

    public int getSessionSize() {
        return sessions.size();
    }

    /**
     * Gets the other session for the sessionId
     * @param sessionId
     * @return
     */
    public String getOtherSession(String sessionId) {
        String otherSession = null;
        if (sessions.contains(sessionId) && sessions.size() > 1) {
            for (String session: sessions) {
                System.out.println("Session list " + session + " input " + sessionId);
                if (session != null && !session.equals(sessionId)) {
                    otherSession = session;
                    break;
                }
            }
        }
        return otherSession;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getMixerId() {
        return mixerId;
    }

    public void setMixerId(int mixerId) {
        this.mixerId = mixerId;
    }
}
