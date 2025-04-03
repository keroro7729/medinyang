/*
실제 화면 전체 구성 (메인인)
*/
import React, { useState } from 'react';
import ChatList from '../components/Chat/ChatList';
import ChatInput from '../components/Chat/ChatInput';

const ChatPage = () => {
  const [messages, setMessages] = useState([]);

  const handleSend = (text) => {
    const userMessage = { sender: 'user', text };
    const gptReply = { sender: 'gpt', text: `"${text}"에 대한 메디냥의 답변입니다.` };

    setMessages(prev => [...prev, userMessage, gptReply]);
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', height: '100vh', backgroundColor: '#ffffff', color: '#000000' }}>
      {/* 상단 바 */}
      <div style={{
        textAlign: 'center',
        padding: '16px',
        fontWeight: 'bold',
        fontSize: '18px',
        borderBottom: '1px solid #ddd',
        backgroundColor: '#ffffff',
        color: '#000000'
      }}>
        메디냥 AI
      </div>

      {/* 메시지 목록 */}
      <ChatList messages={messages} />

      {/* 입력창 */}
      <ChatInput onSend={handleSend} />
    </div>
  );
};

export default ChatPage;
