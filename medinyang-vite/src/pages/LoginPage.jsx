import React from 'react';
import googleIcon from '../assets/google.png'; // ✅ 구글 로고 svg를 assets 폴더에 넣어야 함
const LoginPage = () => {
  // ✅ 환경변수에서 client_id와 redirect_uri 가져옴
  const GOOGLE_CLIENT_ID = import.meta.env.VITE_GOOGLE_CLIENT_ID;
  const REDIRECT_URI = import.meta.env.VITE_GOOGLE_REDIRECT_URI;

const handleGoogleLogin = () => {
  // Spring Boot에서 OAuth2 로그인 핸들링을 맡게 함
  window.location.href = "http://localhost:8080/oauth2/authorization/google";
};

  return (
    // 📱 전체 화면을 수직·수평 모두 가운데 정렬
    <div
      style={{
        height: '100vh',
        width: '100vw',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#f5f5f5',
        textAlign: 'center',
      }}
    >
      {/* 🐱 메디냥 로고 이미지 - 크기 줄임 */}
      <img
        src="src/assets/logo.png"
        alt="Medi냥 로고"
        style={{ width: 180 }}
      />

      {/* 💬 안내 문구 */}
      <p style={{ fontSize: '14px', marginBottom: 70 }}>
        메디냥과 함께 건강한 생활을 시작해보세요!
      </p>

      {/* 소셜 로그인 한 줄 구분선 (피그마 스타일 반영) */}
      <div style={{
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        gap: '12px',
        margin: '24px 0',
        width: '100%',
        maxWidth: '300px'
      }}>
        <div style={{ flex: 1, height: '1px', backgroundColor: '#ccc' }} />
        <span style={{ fontSize: '14px', color: '#555', whiteSpace: 'nowrap' }}>
          소셜 로그인
        </span>
        <div style={{ flex: 1, height: '1px', backgroundColor: '#ccc' }} />
      </div>

      {/* 커스텀 구글 로그인 버튼 */}
      <button
        onClick={handleGoogleLogin}
        style={{
          display: 'flex',
          alignItems: 'center',
          gap: '10px',
          padding: '10px 20px',
          fontSize: '15px',
          fontWeight: '500',
          border: '1px solid #ccc',
          borderRadius: '6px',
          backgroundColor: '#fff',
          color: '#555',
          cursor: 'pointer',
          boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
          marginBottom:'150px'
        }}
      >
        <img src={googleIcon} alt="Google 로고" style={{ width: '18px', height: '18px' }} />
        Google 계정으로 로그인
      </button>
    </div>
  );
};

export default LoginPage;
