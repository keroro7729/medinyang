/*
메시지 한 줄 (말풍선 포함)
*/

import React from 'react';
import MedinyangIcon from '../../assets/medi_doctor.png';


// ChatMessage 컴포넌트 : 메시지 하나를 렌더링
// props로 sender(누가 보냈는지), text(내용)을 받음음
const ChatMessage = ({ sender, text }) => {
  //sender가 'user'이면 내가 보낸 메시지임임
  const isUser = sender === 'user';

  //사용자 메시지 스타일 (말풍선 형태)
  const userBubbleStyle = {
    maxWidth: '70%',
    backgroundColor: '#A7D8F0',
    padding: '8px 12px',
    borderRadius: '16px',
    margin: '4px 0',
    display: 'inline-block',
    color: '#000',
  };

  // GPT 메시지 전체 래퍼 스타일일
  const gptWrapperStyle = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'flex-start',
    marginBottom: '16px',
  };

  // GPT의 텍스트에만 적용되는 스타일일
  const gptTextStyle = {
    color: '#000',
    whiteSpace: 'pre-wrap',
    marginBottom: '6px',
    marginLeft: '65px', // 텍스트만 들여쓰기
  };

  // 메디냥 이미지 스타일일
  const gptImageStyle = {
    width: 48,
    height: 45,
    borderRadius: '50%',
    marginLeft: 0, // 여백 없음!
    //marginRight: '20px',
  };

  return (
    // 전체 메시지 컨테이너
    // user면 오른쪽 정렬, gpt면 왼쪽 정렬렬
    <div style={{ textAlign: isUser ? 'right' : 'left', marginBottom: '16px' }}>
      {isUser ? (
        <div style={userBubbleStyle}>
          {text}
        </div>
      ) : (
        <div style={gptWrapperStyle}>
          <div style={gptTextStyle}>{text}</div>
          <img
            src={MedinyangIcon}
            alt="메디냥"
            style={gptImageStyle}
          />
        </div>
      )}
    </div>
  );
};

export default ChatMessage;
