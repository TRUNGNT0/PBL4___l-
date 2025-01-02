package server.model.BO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private static final int KEYMAP_MAXSIZE = 500;
    private static final long INACTIVITY_LIMIT = 5 * 60 * 1000; // 10 phút tính bằng milliseconds

    private final LinkedHashMap<String, Object[]> keyMap;

    public SessionManager() {
        keyMap = new LinkedHashMap<String, Object[]>(KEYMAP_MAXSIZE, 0.75f, true) {
            private static final long serialVersionUID = 1L;

            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Object[]> eldest) {
                return size() > KEYMAP_MAXSIZE;
            }
        };

        // Khởi chạy luồng tự động xóa session không hoạt động
        startCleanupThread();
    }

    public synchronized boolean isValidSessionId(String username, String sessionId) {
        if (keyMap.containsKey(username)) {
            Object[] sessionData = keyMap.get(username);
            if (sessionData[0].equals(sessionId)) {
                sessionData[1] = System.currentTimeMillis(); // Cập nhật thời gian
                return true;
            }
        }
        return false;
    }

    public String generateRandomSessionId() {
        return UUID.randomUUID().toString();
    }

    public synchronized void addSessionId(String username, String sessionId) {
        keyMap.put(username, new Object[]{sessionId, System.currentTimeMillis()});
    }

    public synchronized void removeSessionId(String username) {
        keyMap.remove(username);
    }

    public synchronized void printAllSessionId() {
        System.out.println("Danh sách các sessionId và username:");
        for (Map.Entry<String, Object[]> entry : keyMap.entrySet()) {
            System.out.println("Username: " + entry.getKey() +
                               " - SessionId: " + entry.getValue()[0] +
                               " - Last Access: " + formatTimestamp((long) entry.getValue()[1]));
        }
    }

    public synchronized Object[][] getAllSessions() {
        Object[][] data = new Object[keyMap.size()][3]; // 3 cột: Username, SessionId, Last Access
        int i = 0;
        for (Map.Entry<String, Object[]> entry : keyMap.entrySet()) {
            data[i][0] = entry.getKey();               // Username
            data[i][1] = entry.getValue()[0];          // SessionId
            data[i][2] = formatTimestamp((long) entry.getValue()[1]); // Last Access
            i++;
        }
        return data;
    }

    private String formatTimestamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(timestamp));
    }

    // Luồng tự động xóa session không hoạt động
    private void startCleanupThread() {
        Thread cleanupThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(60 * 1000); // Chạy mỗi phút
                    removeInactiveSessions();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        cleanupThread.setDaemon(true); // Đảm bảo thread này không ngăn JVM thoát
        cleanupThread.start();
    }

    private synchronized void removeInactiveSessions() {
        long currentTime = System.currentTimeMillis();
        keyMap.entrySet().removeIf(entry -> {
            long lastAccessTime = (long) entry.getValue()[1];
            if (currentTime - lastAccessTime > INACTIVITY_LIMIT) {
                System.out.println("Ngắt kết nối người dùng không hoạt động: " + entry.getKey());
                return true;
            }
            return false;
        });
    }
}