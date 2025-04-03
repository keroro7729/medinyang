/*
입력창과 전송 버튼
*/

import React, { useState } from 'react';
// 전송 버튼에 사용할 '위쪽 화살표' 아이콘 가져오기 (react-icons 라이브러리 사용)
import { IoIosArrowUp } from 'react-icons/io';


// ChatInput 컴포넌트: 사용자 입력창 + 전송 버튼
// 부모 컴포넌트(ChatPage)로부터 onSend 함수를 props로 받아와 사용
const ChatInput = ({ onSend }) => {
  // input이라는 상태(state)를 선언 → 사용자가 입력한 텍스트 저장용
  const [input, setInput] = useState('');


  // 전송 버튼을 클릭하거나 Enter를 눌렀을 때 실행되는 함수
  const handleSend = () => {
     // 입력값이 비어있으면 아무것도 하지 않음
    if (!input.trim()) return;

    // 부모 컴포넌트로 입력값을 전달 → 실제 메시지를 등록하거나 서버로 전송할 때 사용됨
    onSend(input);

    //입력창 비우기
    setInput('');
  };


  // 사용자가 뭔가 입력하면 true, 아무것도 없으면 false
  // 이 값을 통해 버튼 활성화 여부를 판단하고 색상도 바꿔줌
  const isActive = input.trim().length > 0;

  //전송 버튼의 스타일 정의의
  const buttonStyle = {
    backgroundColor: isActive ? '#2C7EDB' : '#D7D7D7',  //파란색:입력o /회색:입력x
    color: 'white',
    border: 'none',
    borderRadius: '50%',  //원형형
    width: 40,
    height: 40,
    fontSize: 18,
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    cursor: isActive ? 'pointer' : 'default', //입력 없으면 클릭 비활성화
    transition: 'background-color 0.1s ease',
  };
  

  return (
    <div style={{ 
      display: 'flex', 
      padding: '12px', 
      borderTop: '1px solid #ccc', 
      backgroundColor: '#fff' }}>
      
      {/* 텍스트 입력창 */}
      <input
        type="text"
        value={input} //input 상태와 바인딩 됨(입력값 저장장)
        onChange={e => setInput(e.target.value)} //입력시 상태 업데이트트
        onKeyDown={e => e.key === 'Enter' && isActive && handleSend()} //enter누르면 전송송
        placeholder="궁금한 점을 메디냥에게 물어보세요!"
        style={{
          flex: 1,
          padding: '8px 12px',
          marginRight: '20px',
          marginLeft: '10px',
          fontSize: '14px',
          border: '1px solid #ccc',
          borderRadius: '8px',
        }}
      />

      {/* 전송 버튼 */}
      <button
      onClick={handleSend}  //클릭 시 전송 함수 실행행
      disabled={!isActive}  //입력 없으면 버튼 비활성화
      style={buttonStyle}
      >
       <IoIosArrowUp size={22} />
      </button>
    </div>
  );
};

export default ChatInput;
