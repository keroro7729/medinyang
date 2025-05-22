package jinTeam.medinyangServer.utils;

import jakarta.servlet.http.HttpSession;
import jinTeam.medinyangServer.common.exceptions.ResourceNotFoundException;

public class HttpSessionUtil {
    public static Long getAccountId(HttpSession session) {
        Long result = (Long) session.getAttribute("accountId");
        if(result == null) {
            throw new ResourceNotFoundException(session.getId()+" session has no accountId");
        }
        return result;
    }

    public static Long getUserId(HttpSession session) {
        Long result = (Long) session.getAttribute("userId");
        if(result == null) {
            throw new ResourceNotFoundException(session.getId()+" session has no userId");
        }
        return result;
    }
}
