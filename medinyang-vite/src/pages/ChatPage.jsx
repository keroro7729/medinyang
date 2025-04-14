import React, { useState, useEffect, useRef } from 'react';
import ChatList from '../components/Chat/ChatList';
import ChatInput from '../components/Chat/ChatInput';
import TopHeader from '../components/TopHeader';

const ChatPage = () => {
  const [messages, setMessages] = useState([]);
  const socket = useRef(null);

  useEffect(() => {
    socket.current = new WebSocket('ws://localhost:8080/ws/chat');

    socket.current.onmessage = (event) => {
      const data = JSON.parse(event.data);
      const gptReply = { sender: 'gpt', text: data.reply };
      setMessages((prev) => [...prev, gptReply]);
    };

    socket.current.onclose = () => console.log('웹소켓 연결 종료');

    return () => socket.current.close();
  }, []);

  const handleSend = (text) => {
    const userMessage = { sender: 'user', text };
    setMessages((prev) => [...prev, userMessage]);

    if (socket.current?.readyState === WebSocket.OPEN) {
      socket.current.send(JSON.stringify({ message: text }));
    }
  };

  return (
    <div style={{
      width: '100vw',
      height: '100vh',
      backgroundColor: '#f5f5f5',
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
    }}>
      <div style={{
        width: '100%',
        maxWidth: '500px',
        height: '100%',
        display: 'flex',
        flexDirection: 'column',
        backgroundColor: '#f5f5f5',
      }}>
        <TopHeader title="메디냥 AI" />

        {/* 채팅 내용 스크롤 영역 */}
        <div style={{
          flex: 1,
          overflowY: 'auto',
          padding: '20px',
          backgroundColor: '#f5f5f5',
        }}>
          <ChatList messages={messages} />
        </div>

        {/* 하단 고정 입력창 */}
        <div style={{
          backgroundColor: '#f5f5f5',
          padding: '12px 20px',
          borderTop: '1px solid #ddd',
        }}>
          <ChatInput onSend={handleSend} />
        </div>
      </div>
    </div>
  );
};

export default ChatPage;
