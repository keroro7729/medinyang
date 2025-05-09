// src/api/auth.js

export async function checkSession() {
  return fetch("http://localhost:8080/auth/session", {
    method: "GET",
    credentials: "include", // ✅ 쿠키 포함해서 세션 체크
  }).then((res) => {
    if (!res.ok) throw new Error("세션 없음");
    return res.json();
  });
}