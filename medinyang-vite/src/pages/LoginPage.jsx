import React from "react";
import { GoogleLogin } from "@react-oauth/google";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext"; // ✅ context 가져오기

const LoginPage = () => {
  const navigate = useNavigate();
  const { setIsLoggedIn } = useAuth(); // ✅ 로그인 상태 변경 함수

  const handleLoginSuccess = (credentialResponse) => {
    const idToken = credentialResponse.credential;

    fetch("https://1fc0-210-110-128-155.ngrok-free.app/login/auth/google", {
      method: "POST",
      credentials: "include", // ✅ 세션 쿠키 포함
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ idToken }),
    })
      .then(async (res) => {
        if (!res.ok) {
          throw new Error("백엔드 인증 실패"); // ❌ 실패 처리
        }
        const data = await res.json();

        // ✅ 세션 ID 저장 (백엔드 응답 구조: data.data.jsessionId)
        localStorage.setItem("jsessionId", data.data.jsessionId);
          // (선택) accessToken 저장
        localStorage.setItem("accessToken", data.token || "");
          // ✅ 로그인 여부 저장
        localStorage.setItem("isLoggedIn", "true");

        setIsLoggedIn(true); // ✅ 로그인 상태 전역 변경
        navigate("/main");
      })
      .catch((err) => {
        console.error("서버 인증 실패", err);
        alert("로그인에 실패했어요 😢");
      });
  };

  const handleLoginError = () => {
    console.error("Google 로그인 실패");
    alert("구글 로그인에 실패했어요");
  };

  return (
    <div
      style={{
        height: "100dvh",
        width: "100vw",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#f5f5f5",
        textAlign: "center",
      }}
    >
      <img
        src="src/assets/logo.png"
        alt="Medi냥 로고"
        style={{ width: 180 }}
      />
      <p style={{ fontSize: "14px", marginBottom: 40 }}>
        메디냥과 함께 건강한 생활을 시작해보세요!
      </p>
      <div
        style={{
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          gap: "12px",
          margin: "24px 0",
          width: "100%",
          maxWidth: "300px",
        }}
      >
        <div style={{ flex: 1, height: "1px", backgroundColor: "#ccc" }} />
        <span style={{ fontSize: "14px", color: "#555", whiteSpace: "nowrap" }}>
          소셜 로그인
        </span>
        <div style={{ flex: 1, height: "1px", backgroundColor: "#ccc" }} />
      </div>
      <div style={{ marginBottom: "150px" }}>
        <GoogleLogin
          onSuccess={handleLoginSuccess}
          onError={handleLoginError}
        />
      </div>
    </div>
  );
};

export default LoginPage;
