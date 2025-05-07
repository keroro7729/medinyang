// src/components/Main/Greeting.jsx
import React from "react";
import profileImg from "../../assets/google.png"; // ❗️profile.png 이미지 필요

const Greeting = () => {
  return (
    <div style={styles.container}>
      <img src={profileImg} alt="프로필" style={styles.image} />
      <p style={styles.text}>홍길동님 반갑습니다!</p>
    </div>
  );
};

const styles = {
  container: {
    textAlign: "center",
    padding: "32px 0 16px",
  },
  image: {
    width: "80px",
    height: "80px",
    borderRadius: "50%",
    objectFit: "cover",
    marginBottom: "12px",
  },
  text: {
    fontSize: "18px",
    fontWeight: "bold",
    color: "#111827",
  },
};

export default Greeting;
