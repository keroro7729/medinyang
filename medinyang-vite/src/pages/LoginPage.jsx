import React from 'react';
import { GoogleLogin } from '@react-oauth/google';
import { useNavigate } from 'react-router-dom';

const LoginPage = () => {
  const navigate = useNavigate();

  // 🔐 구글 로그인 성공 시 호출되는 함수
  const handleLoginSuccess = (credentialResponse) => {
    const idToken = credentialResponse.credential; // 구글에서 받은 토큰 (id_token)

    // 📡 이 토큰을 우리 백엔드 서버에 보내 인증 요청
    fetch('http://localhost:8080/api/auth/google', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json', // 보낼 데이터 형식 명시
      },
      body: JSON.stringify({ idToken: idToken }), // 토큰을 JSON 형식으로 보냄
    })
      .then((res) => res.json())
      .then((data) => {
        // 🧾 백엔드에서 JWT 토큰이 오면 브라우저에 저장
        localStorage.setItem('accessToken', data.token);

        // 🔀 로그인 성공 후 메인 페이지("/")로 이동
        navigate('/main');
      })
      .catch((err) => {
        // ❌ 오류 발생 시 알림 표시
        console.error('서버 인증 실패', err);
        alert('로그인에 실패했어요 😢');
      });
  };

  // ❌ 구글 로그인 자체가 실패했을 때 실행되는 함수
  const handleLoginError = () => {
    console.error('Google 로그인 실패');
    alert('구글 로그인에 실패했어요');
  };

  return (
    // 📱 전체 화면을 수직·수평 모두 가운데 정렬
    <div
      style={{
        height: '100dvh', // 화면 전체 높이
        width: '100vw',
        display: 'flex', // flexbox 사용
        flexDirection: 'column', // 세로 방향 정렬
        justifyContent: 'center', // 수직 정렬
        alignItems: 'center', // 수평 정렬
        backgroundColor: '#f5f5f5', // 배경색
        //padding: '0 20px', // 좌우 여백
        textAlign: 'center', // 텍스트 가운데 정렬
      }}
    >
      {/* 🐱 메디냥 로고 이미지 - 크기 줄임 */}
      <img
        src="src/assets/logo.png" //
        alt="Medi냥 로고"
        style={{ width: 180 }} // ✅ 크기 줄임
      />

      {/* 💬 안내 문구 */}
      <p style={{ fontSize: '14px', marginBottom: 40 }}>
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

      {/* 🔐 실제 Google 로그인 버튼 (자동 생성됨) */}
      <GoogleLogin
        onSuccess={handleLoginSuccess} // 성공 시 처리 함수
        onError={handleLoginError}     // 실패 시 처리 함수
      />
    </div>
  );
};

export default LoginPage;