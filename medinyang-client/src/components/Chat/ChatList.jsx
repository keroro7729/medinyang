/*
전체 메시지 목록
*/

import React from 'react';

// 각각의 메시지를 화면에 출력해주는 컴포넌트 (말풍선 포함함)
import ChatMessage from './ChatMessage';


// ChatList 컴포넌트 정의
// props로 message라는 배열을 받아서 화면에 나열함함
const ChatList = ({ messages }) => {
  return (
    // 채팅창 영역 전체체
    <div 
    style={{ 
      flex: 1, 
      overflowY: 'auto', 
      padding: '16px' 
      }}
    >
      {/* 메시지 배열을 map()을 이용해 반복 출력 */}
      {messages.map((msg, idx) => (
        // 각각의 메시지를 ChatMessage 컴포넌트로 전달
        // sender가 'user' 또는 'gpt'인지, text는 메시지 내용
        <ChatMessage 
        key={idx} sender={msg.sender} text={msg.text} />
      ))}
    </div>
  );
};

export default ChatList;
