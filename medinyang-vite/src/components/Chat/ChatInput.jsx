import React, { useState } from 'react';
import { IoIosArrowUp } from 'react-icons/io';

const ChatInput = ({ onSend }) => {
  const [input, setInput] = useState('');
  const isActive = input.trim().length > 0;

  const handleSend = () => {
    if (!isActive) return;
    onSend(input);
    setInput('');
  };

  return (
    <div style={{ display: 'flex', padding: '12px', borderTop: '1px solid #ccc' }}>
      <input
        type="text"
        value={input}
        onChange={(e) => setInput(e.target.value)}
        onKeyDown={(e) => e.key === 'Enter' && handleSend()}
        placeholder="궁금한 점을 메디냥에게 물어보세요!"
        style={{
          flex: 1,
          padding: '8px 12px',
          marginRight: '12px',
          fontSize: '14px',
          border: '1px solid #ccc',
          borderRadius: '8px',
        }}
      />
      <button
        onClick={handleSend}
        disabled={!isActive}
        style={{
          backgroundColor: isActive ? '#2C7EDB' : '#D7D7D7',
          color: 'white',
          border: 'none',
          borderRadius: '50%',
          width: '40px',
          height: '40px',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          fontSize: '20px',
        }}
      >
        <IoIosArrowUp size={22} color="white" />
      </button>
    </div>
  );
};

export default ChatInput;
