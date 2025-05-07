// src/components/Main/ActionButtons.jsx
import React from "react";
import { useNavigate } from "react-router-dom";
import camera from "../../assets/logo.png"; // 예시 이미지
import doctor from "../../assets/medi_doctor.png";

const ActionButtons = () => {
  const navigate = useNavigate();

  return (
    <div style={styles.row}>
      <div style={styles.button} onClick={() => navigate("/upload")}>
        <img src={camera} alt="업로드" style={styles.icon} />
        <span style={styles.label}>의료 기록 업로드</span>
      </div>
      <div style={styles.button} onClick={() => navigate("/chat")}>
        <img src={doctor} alt="메디냥" style={styles.icon} />
        <span style={styles.label}>메디냥AI 상담</span>
      </div>
    </div>
  );
};

const styles = {
  row: {
    display: "flex",
    justifyContent: "space-around",
    marginBottom: "24px",
  },
  button: {
    width: "120px",
    height: "120px",
    borderRadius: "60px",
    border: "2px solid #3B82F6",
    display: "flex",
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
    cursor: "pointer",
  },
  icon: {
    width: "40px",
    height: "40px",
    marginBottom: "8px",
  },
  label: {
    fontSize: "13px",
    textAlign: "center",
    color: "#111827",
  },
};

export default ActionButtons;
