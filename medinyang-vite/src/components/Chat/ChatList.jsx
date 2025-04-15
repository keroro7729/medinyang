import React, { useEffect, useRef } from 'react';
import ChatMessage from './ChatMessage';

const ChatList = ({ messages }) => {
  const chatEndRef = useRef(null);

  // 메시지 추가 시 자동 스크롤
  useEffect(() => {
    chatEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages]);

  return (
    <div style={{ flex: 1, overflowY: 'auto', padding: '16px' }}>
      {messages.map((msg, idx) => (
        // 메시지 하나하나를 ChatMessage로 출력
        <ChatMessage key={idx} sender={msg.sender} text={msg.text} />
      ))}
      {/* 가장 아래 위치 (스크롤 포커스용) */}
      <div ref={chatEndRef} />
    </div>
  );
};

export default ChatList;
