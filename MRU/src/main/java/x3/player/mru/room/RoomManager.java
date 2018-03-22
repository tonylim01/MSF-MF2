package x3.player.mru.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RoomManager {

    private static final Logger logger = LoggerFactory.getLogger(RoomManager.class);

    private static RoomManager roomManager = null;

    public static RoomManager getInstance() {
        if (roomManager == null) {
            roomManager = new RoomManager();
        }

        return roomManager;
    }

    private Map<String, RoomInfo> roomInfos;

    public RoomManager() {
        roomInfos = new HashMap<>();
    }

    public RoomInfo getRoomInfo(String roomId) {
        RoomInfo roomInfo = null;
        synchronized (roomInfos) {
            if (roomInfos.containsKey(roomId)) {
                roomInfo = roomInfos.get(roomId);
            }
        }

        return roomInfo;
    }

    public boolean hasSession(String roomId, String sessionId) {
        boolean result = false;

        synchronized (roomInfos) {
            if (roomInfos.containsKey(roomId)) {
                RoomInfo roomInfo = roomInfos.get(roomId);
                result = roomInfo.hasSession(sessionId);
            }
        }

        return result;
    }

    public boolean addSession(String roomId, String sessionId) {
        boolean result = false;

        synchronized (roomInfos) {
            RoomInfo roomInfo;
            if (roomInfos.containsKey(roomId)) {
                roomInfo = roomInfos.get(roomId);
            }
            else {
                roomInfo = new RoomInfo();
                roomInfos.put(roomId, roomInfo);
            }

            if (!roomInfo.hasSession(sessionId)) {
                roomInfo.addSession(sessionId);
                result = true;
            }
            else {
                logger.warn("[{}] Room already has session [{}]", roomId, sessionId);
            }

        }

        return result;
    }

    public boolean removeSession(String roomId, String sessionId) {
        boolean result = false;

        synchronized (roomInfos) {
            if (roomInfos.containsKey(roomId)) {
                RoomInfo roomInfo = roomInfos.get(roomId);

                if (roomInfo.hasSession(sessionId)) {
                    roomInfo.removeSession(sessionId);
                    result = true;
                }
                else {
                    logger.warn("[{}] Room has not session [{}]", roomId, sessionId);
                }

                logger.debug("[{}] Room session count [{}]", roomId, roomInfo.getSessionSize());
                if (roomInfo.getSessionSize() == 0) {
                    roomInfos.remove(roomId);
                    logger.debug("[{}] Room deleted", roomId);
                }
            }
            else {
                logger.warn("[{}] Room not found", roomId);
            }

       }

        return result;
    }

    /**
     * Gets the total number of rooms
     * @return
     */
    public int getTotalRoomCount() {

        //
        // TODO
        //
        return 10;
    }

    /**
     * Gets the current idle room count
     * @return
     */
    public int getIdleRoomCount() {
        //
        // TODO
        //
        return 10;
    }

}
