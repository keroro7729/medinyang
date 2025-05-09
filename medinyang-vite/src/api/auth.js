// src/api/auth.js

export async function checkSession() {
    const res = await fetch("http://localhost:8080/auth/session", {
      method: "GET",
      credentials: "include", // ✅ 세션 쿠키(JSESSIONID) 자동 포함
    });
  
    if (!res.ok) throw new Error("세션 없음");
    return await res.json(); // 예시 응답: { email: "...", message: "세션 유효함" }
  }
  