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
      display: "flex", // 가로 정렬
      alignItems: "center", // 수직 가운데 정렬
      justifyContent: "center", // 전체 중앙 배치
      gap: "12px", // 이미지와 텍스트 사이 간격
      padding: "24px 0",
    },
    image: {
      width: "60px",
      height: "60px",
      borderRadius: "50%",
      objectFit: "cover",
    },
    text: {
      fontSize: "18px",
      fontWeight: "bold",
      color: "#111827",
    },
  };
  

export default Greeting;
