import React from "react";
import { GoogleLogin } from "@react-oauth/google";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const LoginPage = () => {
  const navigate = useNavigate();
  const { setIsLoggedIn } = useAuth();

const handleLoginSuccess = (credentialResponse) => {
  const idToken = credentialResponse.credential;

  fetch(`${import.meta.env.VITE_API_BASE_URL}/auth/google`, {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
      "ngrok-skip-browser-warning": "69420",
    },
    body: JSON.stringify({ idToken }),
  })
    .then(async (res) => {
      if (!res.ok) {
        throw new Error("백엔드 인증 실패");
      }

      const data = await res.json();
      localStorage.setItem("jsessionId", data.data.jsessionId);
      localStorage.setItem("accessToken", data.token || "");
      localStorage.setItem("isLoggedIn", "true");

      // ✅ 서버에서 유저 목록 요청
      const userRes = await fetch(`${import.meta.env.VITE_API_BASE_URL}/users`, {
        method: "GET",
        credentials: "include",
        headers: {
          "ngrok-skip-browser-warning": "69420",
        },
      });

      if (!userRes.ok) throw new Error("유저 목록 조회 실패");
      const userList = await userRes.json();

      if (userList.length === 0) {
        navigate("/add-user");
      } else {
        localStorage.setItem("users", JSON.stringify(userList));
        localStorage.setItem("currentUser", JSON.stringify(userList[0]));
        navigate("/main");
      }

      setIsLoggedIn(true);
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
