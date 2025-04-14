import React, { useEffect, useRef } from 'react';
import ChatMessage from './ChatMessage';

const ChatList = ({ messages }) => {
  const chatEndRef = useRef(null);

  useEffect(() => {
    chatEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages]);

  return (
    <div style={{ flex: 1, overflowY: 'auto', padding: '16px' }}>
      {messages.map((msg, idx) => (
        <ChatMessage key={idx} sender={msg.sender} text={msg.text} />
      ))}
      <div ref={chatEndRef} />
    </div>
  );
};

export default ChatList;
