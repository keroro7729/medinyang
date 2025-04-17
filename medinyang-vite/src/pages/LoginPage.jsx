import React, { useEffect } from 'react';
import googleIcon from '../assets/google.png';

const LoginPage = () => {
  const GOOGLE_CLIENT_ID = import.meta.env.VITE_GOOGLE_CLIENT_ID;

  useEffect(() => {
    // ✅ 로그인 버튼 렌더링 준비 (자동 초기화 막기)
    window.google?.accounts?.id?.initialize({
      client_id: GOOGLE_CLIENT_ID,
      callback: handleCredentialResponse,
    });
  }, []);

  // ✅ ID Token 수신 → 서버 전송
  const handleCredentialResponse = (response) => {
    const idToken = response.credential;
    console.log('🪪 받은 ID Token:', idToken);

    fetch('http://localhost:8080/api/auth/google', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include', // 쿠키 저장
      body: JSON.stringify({ idToken }), // 서버는 이 토큰으로 사용자 검증
    })
      .then((res) => {
        if (!res.ok) throw new Error('로그인 실패');
        return res.json();
      })
      .then(() => {
        window.location.href = '/';
      })
      .catch((err) => {
        console.error('❌ 로그인 오류:', err);
        alert('로그인 중 오류가 발생했습니다.');
      });
  };

  const handleGoogleLogin = () => {
    window.google.accounts.id.prompt(); // 팝업 방식 호출
  };

  return (
    <div style={{
      height: '100dvh',
      width: '100vw',
      display: 'flex',
      flexDirection: 'column',
      justifyContent: 'center',
      alignItems: 'center',
      backgroundColor: '#f5f5f5',
      textAlign: 'center',
    }}>
      <img src="src/assets/logo.png" alt="Medi냥 로고" style={{ width: 180 }} />
      <p style={{ fontSize: '14px', marginBottom: 70 }}>
        메디냥과 함께 건강한 생활을 시작해보세요!
      </p>

      <div style={{
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        gap: '12px',
        margin: '24px 0',
        width: '100%',
        maxWidth: '300px',
      }}>
        <div style={{ flex: 1, height: '1px', backgroundColor: '#ccc' }} />
        <span style={{ fontSize: '14px', color: '#555', whiteSpace: 'nowrap' }}>
          소셜 로그인
        </span>
        <div style={{ flex: 1, height: '1px', backgroundColor: '#ccc' }} />
      </div>

      {/* 커스텀 Google 로그인 버튼 */}
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
          marginBottom: '150px',
        }}
      >
        <img src={googleIcon} alt="Google 로고" style={{ width: '18px', height: '18px' }} />
        Google 계정으로 로그인
      </button>
    </div>
  );
};

export default LoginPage;
