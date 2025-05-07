// src/components/Main/BottomNav.jsx
import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faChartLine,
  faFileMedical,
  faUserDoctor,
  faGear,
} from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";

const BottomNav = ({ current }) => {
  const navigate = useNavigate();

  const navItems = [
    { icon: faChartLine, label: "나의 건강 DATA", to: "/data" },
    { icon: faFileMedical, label: "의료 이력 관리", to: "/history" },
    { icon: faUserDoctor, label: "맞춤관리", to: "/manage" },
    { icon: faGear, label: "설정", to: "/setting" },
  ];

  return (
    <div style={styles.nav}>
      {navItems.map((item) => (
        <div
          key={item.to}
          onClick={() => navigate(item.to)}
          style={{
            ...styles.item,
            color: current === item.to.split("/")[1] ? "#3B82F6" : "#6B7280",
          }}
        >
          <FontAwesomeIcon icon={item.icon} size="lg" />
          <div style={styles.label}>{item.label}</div>
        </div>
      ))}
    </div>
  );
};

const styles = {
  nav: {
    display: "flex",
    justifyContent: "space-around",
    padding: "12px 0",
    borderTop: "1px solid #ddd",
    backgroundColor: "#ffffff",
  },
  item: {
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    fontSize: "12px",
    cursor: "pointer",
  },
  label: {
    marginTop: "4px",
  },
};

export default BottomNav;
