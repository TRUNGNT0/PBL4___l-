package server.model.BO;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private static final int KEYMAP_MAXSIZE = 500;

    // LinkedHashMap với cơ chế tự động xóa phần tử cũ nhất khi vượt quá kích thước tối đa
    private final LinkedHashMap<String, String> keyMap;		//<username, sessionId>

    public SessionManager() {
        keyMap = new LinkedHashMap<String, String>(KEYMAP_MAXSIZE, 0.75f, true) {
        	/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			//(kích thước ban đầu của mảng băm, 0.75f Ngưỡng để quyết định khi nào bảng băm cần mở rộng
        	// true: Duy trì thứ tự truy cập (access-order), nghĩa là phần tử được truy cập gần đây nhất sẽ được đưa về cuối.
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > KEYMAP_MAXSIZE;
            }
        };
    }

    /**
     * Kiểm tra tính hợp lệ của token và username
     * 
     * @param sessionId    sessionID cần kiểm tra
     * @param username Tên người dùng
     * @return true nếu hợp lệ, false nếu không
     */
    public synchronized boolean isValidSessionId(String username, String sessionId) {
        return keyMap.containsKey(username) && keyMap.get(username).equals(sessionId);
    }

    /**
     * Tạo chuỗi token ngẫu nhiên
     * 
     * @return Chuỗi token ngẫu nhiên
     */
    public String generateRandomSessionId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Thêm token và username vào hệ thống
     * 
     * @param sessionId    Token được thêm
     * @param username Tên người dùng
     */
    public synchronized void addSessionId(String username, String sessionId) {
        keyMap.put(username, sessionId);
    }
    
    public synchronized void printAllSessionId() {
        System.out.println("Danh sách các sessionId và username:");
        for (Map.Entry<String, String> entry : keyMap.entrySet()) {
            System.out.println("Username: " + entry.getKey() + " - SessionId: " + entry.getValue());
        }
    }
}
