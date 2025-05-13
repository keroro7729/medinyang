package jinTeam.medinyangServer.session;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**세션이 생성될 떄마다 ID를 메모리에 저장하고 나중에 JSESSIONID로 찾을 수 있도록 함**/
public class SessionCollector implements HttpSessionListener {

    private static final Map<String, HttpSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        sessions.put(event.getSession().getId(), event.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        sessions.remove(event.getSession().getId());
    }

    public static HttpSession getSessionById(String sessionId) {
        return sessions.get(sessionId);
    }
}
