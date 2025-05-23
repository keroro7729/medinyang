package jinTeam.medinyangServer.database.chatLog;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {
    List<ChatLog> findByUser_UserId(Long userId);
    @Query("SELECT c FROM ChatLog c WHERE c.user.userId = :userId AND (:lastChatId IS NULL OR c.chatId < :lastChatId) ORDER BY c.chatId DESC")
    List<ChatLog> findNextChats(@Param("userId") Long userId, @Param("lastChatId") Long lastChatId, Pageable pageable);


}
