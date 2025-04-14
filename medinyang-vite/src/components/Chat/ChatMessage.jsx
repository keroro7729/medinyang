import React from 'react';
import MedinyangIcon from '../../assets/medi_doctor.png';

const ChatMessage = ({ sender, text }) => {
  const isUser = sender === 'user';

  return (
    <div style={{ textAlign: isUser ? 'right' : 'left', marginBottom: '16px' }}>
      {isUser ? (
        <div style={{
          maxWidth: '70%', backgroundColor: '#A7D8F0',
          padding: '8px 12px', borderRadius: '16px',
          display: 'inline-block', color: '#000',
        }}>
          {text}
        </div>
      ) : (
        <div style={{
          display: 'flex', flexDirection: 'column',
          alignItems: 'flex-start', marginBottom: '16px',
        }}>
          <div style={{ color: '#000', whiteSpace: 'pre-wrap', marginLeft: '65px' }}>
            {text}
          </div>
          <img src={MedinyangIcon} alt="메디냥" style={{ width: 48, height: 45, borderRadius: '50%' }} />
        </div>
      )}
    </div>
  );
};

export default ChatMessage;
