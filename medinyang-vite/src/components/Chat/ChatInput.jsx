import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faArrowUp, faStop, faCamera, faStethoscope, faPills,
} from '@fortawesome/free-solid-svg-icons';

// 입력창 컴포넌트
const ChatInput = ({ onSend, onStop, isReplying, onImageUpload }) => {
  // 입력값 저장용 state
  const [input, setInput] = useState('');

  // 입력값이 있는지 여부
  const isActive = input.trim().length > 0;

  // 엔터치거나 보내기 버튼 눌렀을 때
  const handleSend = () => {
    if (!isActive || isReplying) return;
    onSend(input);
    setInput(''); // 입력창 초기화
  };

  // 보내기 or 멈추기 버튼 눌렀을 때
  const handleButtonClick = () => {
    if (isReplying) onStop();
    else handleSend();
  };

  // 프리셋 버튼 (증상/약물) 클릭 시 자동 입력
  const handlePresetClick = (type) => {
    if (isReplying) return;
    if (type === 'symptom') {
      setInput('증상이 심한데 어떻게 해야 하나요? 응급처치는 어떻게 하나요?');
    } else if (type === 'drug') {
      setInput('이 약물의 정보와 부작용을 알려주세요.');
    }
  };

  // 이미지 업로드
  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file && onImageUpload) {
      onImageUpload(file); // 부모로 전달
    }
  };

  return (
    // 입력창 전체를 감싸는 컨테이너
    <div style={{
      width: '100%', // 항상 너비 100%
      boxSizing: 'border-box', // padding 포함
      padding: '12px 16px',
      backgroundColor: '#ffffff',
      borderRadius: '16px 16px 0 0',
      boxShadow: '0 -1px 4px rgba(0,0,0,0.06)',
      position: 'sticky', // 스크롤 시 하단 고정
      bottom: 0,
      zIndex: 10, // 위에 보이게
    }}>
      {/* 사용자 입력창 */}
      <div
        style={{
          backgroundColor: '#f9f9f9',
          borderRadius: '999px',
          padding: '10px 20px',
          fontSize: '15px',
          color: '#555',
          boxShadow: 'inset 0 0 0 1px #ddd',
        }}
      >
        <input
          type="text"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyDown={(e) => e.key === 'Enter' && handleSend()}
          disabled={isReplying}
          placeholder="메디냥에게 물어보세요!"
          style={{
            width: '100%',
            border: 'none',
            outline: 'none',
            fontSize: '15px',
            backgroundColor: 'transparent',
          }}
        />
      </div>

      {/* 버튼 줄: 업로드 / 프리셋 / 전송 */}
      <div
        style={{
          marginTop: '10px',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between',
          flexWrap: 'wrap', // 화면 작으면 줄바꿈
          gap: '8px',
        }}
      >
        {/* 왼쪽 버튼 묶음 */}
        <div style={{ display: 'flex', gap: '8px', alignItems: 'center' }}>
          {/* 이미지 업로드 버튼 */}
          <label htmlFor="upload-image" style={iconBtnStyle}>
            <FontAwesomeIcon icon={faCamera} />
            <input
              id="upload-image"
              type="file"
              accept="image/*"
              onChange={handleFileChange}
              style={{ display: 'none' }}
              disabled={isReplying}
            />
          </label>

          {/* 프리셋 버튼: 증상 */}
          <button
            onClick={() => handlePresetClick('symptom')}
            disabled={isReplying}
            style={presetBtnStyle(isReplying)}
          >
            <FontAwesomeIcon icon={faStethoscope} style={{ marginRight: '6px' }} />
            증상/응급처치
          </button>

          {/* 프리셋 버튼: 약물 */}
          <button
            onClick={() => handlePresetClick('drug')}
            disabled={isReplying}
            style={presetBtnStyle(isReplying)}
          >
            <FontAwesomeIcon icon={faPills} style={{ marginRight: '6px' }} />
            약물 정보
          </button>
        </div>

        {/* 전송 or 응답 멈춤 버튼 */}
        <button
          onClick={handleButtonClick}
          disabled={!isActive && !isReplying}
          style={{
            backgroundColor: isReplying
              ? '#B1B1B1' // 응답하고 있을때 회색
              : isActive
              ? '#3B82F6' // 전송 가능 시 파란색
              : '#bcbcbc', // 아무것도 없을 땐 회색
            border: 'none',
            borderRadius: '50%',
            width: '36px',
            height: '36px',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            cursor: (!isActive && !isReplying) ? 'not-allowed' : 'pointer',
            transition: 'background-color 0.2s ease',
            color: '#fff',
          }}
        >
          <FontAwesomeIcon icon={isReplying ? faStop : faArrowUp} />
        </button>
      </div>
    </div>
  );
};

// 프리셋 버튼 스타일 함수
const presetBtnStyle = (disabled) => ({
  backgroundColor: '#f1f1f1',
  border: 'none',
  borderRadius: '20px',
  padding: '6px 12px',
  fontSize: '13px',
  fontWeight: 500,
  display: 'flex',
  alignItems: 'center',
  color: '#333',
  cursor: disabled ? 'not-allowed' : 'pointer',
  opacity: disabled ? 0.5 : 1,
});

// 카메라 아이콘 버튼 스타일
const iconBtnStyle = {
  backgroundColor: 'transparent',
  border: 'none',
  fontSize: '16px',
  cursor: 'pointer',
  color: '#333',
  display: 'flex',
  alignItems: 'center',
};

export default ChatInput;
