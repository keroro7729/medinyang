// src/components/Main/Greeting.jsx
import React from "react";
import profileImg from "../../assets/profile.png";

const Greeting = () => {
  return (
    <div style={styles.container}>
      <div style={styles.inner}>
        <img src={profileImg} alt="프로필" style={styles.image} />
        <div style={styles.textWrapper}>
          <p style={styles.name}>홍길동 님,</p>
          <p style={styles.sub1}>메디냥과 함께</p>
          <p style={styles.sub2}>더 건강한 생활 해보아요!</p>
        </div>
      </div>
    </div>
  );
};

const styles = {
  container: {
    backgroundColor: "#2C7EDB",
    padding: "48px 24px 70px",
    color: "white",
  },
  inner: {
    display: "flex",
    alignItems: "center",
    gap: "24px",
  },
  image: {
    width: "80px",
    height: "80px",
    borderRadius: "50%",
    backgroundColor: "#e4e4e4",
  },
  textWrapper: {
    flex: 1,
  },
  name: {
    fontSize: "20px",
    fontWeight: "bold",
    marginBottom: "4px",
  },
  sub1: {
    fontSize: "15px",
    lineHeight: "1.4",
  },
  sub2: {
    fontSize: "15px",
    lineHeight: "1.4",
    marginTop: "-12px",
  },
};

export default Greeting;
