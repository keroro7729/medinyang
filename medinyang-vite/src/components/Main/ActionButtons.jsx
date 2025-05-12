// src/components/Main/ActionButtons.jsx
import React from "react";
import { useNavigate } from "react-router-dom";
import doctor from "../../assets/doctornyang.png";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCamera } from "@fortawesome/free-solid-svg-icons";


const ActionButtons = () => {
  const navigate = useNavigate();

  return (
    <div style={styles.row}>
      <div style={styles.button} onClick={() => navigate("/upload")}>
  <FontAwesomeIcon icon={faCamera} size="3x" style={styles.camera} />
  <span style={styles.label}>업로드</span>
</div>

      <div style={styles.button} onClick={() => navigate("/chat")}>
        <img src={doctor} alt="메디냥" style={styles.icon} />
        <span style={styles.label}>메디냥 상담</span>
      </div>
    </div>
  );
};

const styles = {
  row: {
    display: "flex",
    justifyContent: "space-evenly",   // ✅ 버튼 균등 간격
    gap: "20px",
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
  camera: {
    color: "#3B82F6",
    marginBottom: "8px",
  },
  icon: {
    width: "60px",
    height: "70px",
    marginBottom: "8px",
  },
  label: {
    fontSize: "13px",
    textAlign: "center",
    color: "#111827",
  },
};

export default ActionButtons;
