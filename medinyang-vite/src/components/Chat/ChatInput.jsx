import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowUp } from '@fortawesome/free-solid-svg-icons';

const ChatInput = ({ onSend }) => {
  const [input, setInput] = useState('');
  const isActive = input.trim().length > 0;

  const handleSend = () => {
    if (!isActive) return;
    onSend(input);
    setInput('');
  };

  return (
    <div
      style={{
        display: 'flex',
        alignItems: 'center',
        backgroundColor: '#f5f5f5', // ✅ 전체 배경 통일
        padding: '8px 0',
      }}
    >
      <input
        type="text"
        value={input}
        onChange={(e) => setInput(e.target.value)}
        onKeyDown={(e) => e.key === 'Enter' && handleSend()}
        placeholder="궁금한 점을 메디냥에게 물어보세요!"
        style={{
          flex: 1,
          padding: '10px 12px',
          fontSize: '14px',
          border: '1px solid #ccc',
          borderRadius: '6px',
          marginRight: '10px',
          backgroundColor: '#ffffff', // ✅ 인풋창은 흰 배경
        }}
      />
      <button
        onClick={handleSend}
        style={{
          backgroundColor: '#3B82F6',   // ✅ 파란색 원
          border: 'none',
          borderRadius: '50%',          // 원형
          width: '40px',
          height: '40px',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          cursor: 'pointer',
          transition: 'background-color 0.2s ease',
        }}
      >
        <FontAwesomeIcon
          icon={faArrowUp}
          style={{
            color: '#ffffff',            // ✅ 흰색 화살표
            fontSize: '16px',
          }}
        />
      </button>
    </div>
  );
};

export default ChatInput;
