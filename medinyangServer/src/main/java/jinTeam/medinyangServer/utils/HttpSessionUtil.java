package jinTeam.medinyangServer.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jinTeam.medinyangServer.common.exceptions.NotLoginException;
import jinTeam.medinyangServer.common.exceptions.ResourceNotFoundException;

import java.net.http.HttpRequest;

public class HttpSessionUtil {
    public static Long getAccountId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            throw new NotLoginException("not logged in: "+request.getRemoteUser());
        Long result = (Long) session.getAttribute("accountId");
        if(result == null) {
            throw new NotLoginException(session.getId()+" session has no accountId");
        }
        return result;
    }

    public static Long getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            throw new NotLoginException("not logged in: "+request.getRemoteUser());
        Long result = (Long) session.getAttribute("userId");
        if(result == null)
            throw new NotLoginException(session.getId()+" session has no userId");
        return result;
    }

    public static void setUserId(HttpServletRequest request, Long userId) {
        HttpSession session = request.getSession(false);
        if(session == null)
            throw new NotLoginException("not logged in: "+request.getRemoteUser());
        session.setAttribute("userId", userId);
    }
}
