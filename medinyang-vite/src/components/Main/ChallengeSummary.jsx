// src/components/Main/ChallengeSummary.jsx
import React from "react";
import sleepyCat from "../../assets/goodnyang.png";
import { useNavigate } from "react-router-dom";

const ChallengeSummary = () => {
  const navigate = useNavigate();

  return (
    <div style={styles.card} onClick={() => navigate("/manage")}>
      <div style={styles.row}>
        <img src={sleepyCat} alt="챌린지" style={styles.image} />
        <div>
          <p style={styles.title}>🌙 수면 챌린지</p>
          <p style={styles.sub}>13일째 ing</p>
        </div>
      </div>
      <p style={styles.link}>챌린지 체크하러 가기 &gt;</p>
    </div>
  );
};

const styles = {
  card: {
    backgroundColor: "#ffffff",
    padding: "20px",
    borderRadius: "12px",
    boxShadow: "0 2px 8px rgba(0,0,0,0.05)",
    cursor: "pointer",
  },
  row: {
    display: "flex",
    alignItems: "center",
    gap: "16px",
  },
  image: {
    width: "60px",
    height: "60px",
  },
  title: {
    fontSize: "16px",
    fontWeight: "bold",
    marginBottom: "4px",
  },
  sub: {
    fontSize: "14px",
    color: "#555",
  },
  link: {
    marginTop: "12px",
    fontSize: "13px",
    color: "#3B82F6",
  },
};

export default ChallengeSummary;
