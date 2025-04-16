import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const OAuthCallback = () => {
  const navigate = useNavigate();

  useEffect(() => {
    // 1️⃣ URL에서 ?code=... 추출
    const code = new URLSearchParams(window.location.search).get('code');

    if (!code) {
      alert('인증 코드가 없습니다!');
      return;
    }

    // 2️⃣ 백엔드에 POST로 code 전달
    fetch('http://localhost:8080/api/auth/google', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include', // 세션 쿠키 저장
      body: JSON.stringify({ code }),
    })
      .then((res) => {
        if (!res.ok) throw new Error('로그인 실패');
        return res.json();
      })
      .then(() => {
        navigate('/'); // 로그인 성공 후 메인으로
      })
      .catch((err) => {
        console.error('로그인 처리 실패:', err);
        alert('로그인 중 오류가 발생했습니다.');
      });
  }, [navigate]);

  return (
    <div style={{ textAlign: 'center', paddingTop: '40vh' }}>
      로그인 처리 중입니다...
    </div>
  );
};

export default OAuthCallback;
