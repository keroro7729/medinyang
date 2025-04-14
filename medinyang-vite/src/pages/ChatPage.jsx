import React, { useState, useEffect, useRef } from 'react';
import ChatList from '../components/Chat/ChatList';
import ChatInput from '../components/Chat/ChatInput';

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
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      backgroundColor: '#f0f0f0',
    }}>
      <div style={{
        width: '100%',
        maxWidth: '500px',
        height: '100%',
        display: 'flex',
        flexDirection: 'column',
        backgroundColor: '#ffffff',
      }}>
        <div style={{
          textAlign: 'center',
          padding: '16px',
          fontWeight: 'bold',
          fontSize: '18px',
          borderBottom: '1px solid #ddd',
        }}>
          메디냥 AI
        </div>
  
        <ChatList messages={messages} />
        <ChatInput onSend={handleSend} />
      </div>
    </div>
  );
  
  
};

export default ChatPage;
