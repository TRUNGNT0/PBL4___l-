package server.model.BO;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private static final int KEYMAP_MAXSIZE = 500;

    private final LinkedHashMap<String, String> keyMap;

    public SessionManager() {
        keyMap = new LinkedHashMap<String, String>(KEYMAP_MAXSIZE, 0.75f, true) {
            private static final long serialVersionUID = 1L;

            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > KEYMAP_MAXSIZE;
            }
        };
    }

    public synchronized boolean isValidSessionId(String username, String sessionId) {
        return keyMap.containsKey(username) && keyMap.get(username).equals(sessionId);
    }

    public String generateRandomSessionId() {
        return UUID.randomUUID().toString();
    }

    public synchronized void addSessionId(String username, String sessionId) {
        keyMap.put(username, sessionId);
    }

    public synchronized void removeSessionId(String username) {
        keyMap.remove(username);
    }
    
    public synchronized void printAllSessionId() {
        System.out.println("Danh sách các sessionId và username:");
        for (Map.Entry<String, String> entry : keyMap.entrySet()) {
            System.out.println("Username: " + entry.getKey() + " - SessionId: " + entry.getValue());
        }
    }

    public synchronized Object[][] getAllSessions() {
        Object[][] data = new Object[keyMap.size()][2];
        int i = 0;
        for (Map.Entry<String, String> entry : keyMap.entrySet()) {
            data[i][0] = entry.getKey();
            data[i][1] = entry.getValue();
            i++;
        }
        return data;
    }
}
