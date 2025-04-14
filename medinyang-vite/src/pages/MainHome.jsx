import React from 'react';
import { useNavigate } from 'react-router-dom';
import TopHeader from '../components/TopHeader';

const MainHome = () => {
  const navigate = useNavigate();

  return (
    <div style={{
      width: '100vw',
      height: '100vh',
      backgroundColor: '#f5f5f5',
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
    }}>
      <div style={{
        width: '100%',
        maxWidth: '500px',
        height: '100%',
        backgroundColor: '#f5f5f5',
        display: 'flex',
        flexDirection: 'column',
      }}>
        <TopHeader title="메디냥 메인" />

        <div style={{
          flex: 1,
          display: 'flex',
          flexDirection: 'column',
          justifyContent: 'center',
          alignItems: 'center',
          gap: '20px',
          padding: '20px'
        }}>
          <button onClick={() => navigate('/chat')} style={btnStyle}>메디냥 AI 채팅</button>
          <button onClick={() => navigate('/upload')} style={btnStyle}>의료기록 업로드</button>
          <button onClick={() => navigate('/history')} style={btnStyle}>의료 이력 관리</button>
        </div>
      </div>
    </div>
  );
};

const btnStyle = {
  width: '80%',
  padding: '14px',
  fontSize: '16px',
  fontWeight: 'bold',
  backgroundColor: '#4CAF50',
  color: 'white',
  border: 'none',
  borderRadius: '8px',
  cursor: 'pointer'
};

export default MainHome;
