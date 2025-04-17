import React from 'react';
import { useNavigate } from 'react-router-dom'; // 페이지 이동 hook
import TopHeader from '../components/TopHeader'; // 상단 헤더

const MainHome = () => {
  const navigate = useNavigate(); // 페이지 이동용 함수

  return (
    <div style={{
      width: '100vw',
      height: '100dvh',
      backgroundColor: '#f5f5f5',
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
    }}>
      {/* 내부 컨테이너 (모바일 크기 제한) */}
      <div style={{
        width: '100%',
        maxWidth: '500px',
        height: '100%',
        backgroundColor: '#f5f5f5',
        display: 'flex',
        flexDirection: 'column',
      }}>
        {/* 상단 고정 헤더 */}
        <TopHeader title="메디냥 메인" />

        {/* 중앙 버튼 영역 */}
        <div style={{
          flex: 1,
          display: 'flex',
          flexDirection: 'column',
          justifyContent: 'center',
          alignItems: 'center',
          gap: '20px',
          padding: '20px'
        }}>
          {/* 각 버튼 클릭 시 라우팅 이동 */}
          <button onClick={() => navigate('/chat')} style={btnStyle}>메디냥 AI 채팅</button>
          <button onClick={() => navigate('/upload')} style={btnStyle}>의료기록 업로드</button>
          <button onClick={() => navigate('/history')} style={btnStyle}>의료 이력 관리</button>
        </div>
      </div>
    </div>
  );
};

// 버튼 공통 스타일
const btnStyle = {
  width: '80%',
  padding: '14px',
  fontSize: '16px',
  fontWeight: 'bold',
  backgroundColor: '#3B82F6',
  color: 'white',
  border: 'none',
  borderRadius: '8px',
  cursor: 'pointer'
};

export default MainHome;
