import React, { useState } from "react";
import TopHeader from "../components/TopHeader";
import { useNavigate } from "react-router-dom";
import PlanResultCard from "../components/Manage/PlanResultCard";
import ChallengeContent from "../components/Manage/ChallengeContent";


const ManagePage = () => {
  const navigate = useNavigate();
  const [tab, setTab] = useState("plan"); // "plan" or "challenge"

  const handleGoToChat = () => {
    navigate("/chat");
  };

  return (
    <div style={styles.page}>
      <TopHeader title="맞춤 관리" />

      {/* 탭 메뉴 */}
      <div style={styles.tabContainer}>
        <button
          style={{ ...styles.tab, ...(tab === "plan" ? styles.activeTab : {}) }}
          onClick={() => setTab("plan")}
        >
          맞춤 플랜
        </button>
        <button
          style={{ ...styles.tab, ...(tab === "challenge" ? styles.activeTab : {}) }}
          onClick={() => setTab("challenge")}
        >
          챌린지
        </button>
      </div>

      {/* 탭 콘텐츠 */}
      <div style={styles.content}>
      {tab === "plan" ? (
  <>
    <button onClick={handleGoToChat} style={styles.consultButton}>
      플랜 상담하러 가기
    </button>
    <hr style={styles.divider} />
    <PlanResultCard />
  </>
) : (
  <ChallengeContent />
)}
      </div>
    </div>
  );
};

const styles = {
  page: {
    width: "100vw",
        height: "100dvh",
    backgroundColor: "#f9f9f9",
    boxSizing: "border-box",
  },
  tabContainer: {
    display: "flex",
    justifyContent: "center",
    marginTop: "12px",
    borderBottom: "1px solid #ddd",
  },
  tab: {
    flex: 1,
    padding: "12px 0",
    backgroundColor: "#f3f4f6",
    border: "none",
    fontWeight: "bold",
    color: "#6b7280",
    cursor: "pointer",
  },
  activeTab: {
    backgroundColor: "#ffffff",
    color: "#111827",
    borderBottom: "2px solid #3B82F6",
  },
  content: {
    padding: "16px",
  },
  consultButton: {
    width: "100%",
    padding: "16px",
    backgroundColor: "#BFDBFE",
    color: "#1D4ED8",
    fontWeight: "bold",
    border: "none",
    borderRadius: "12px",
    fontSize: "16px",
    marginBottom: "16px",
  },
  divider: {
    margin: "16px 0",
    border: "none",
    borderTop: "1px solid #e5e7eb",
  },
  planResult: {
    backgroundColor: "#ffffff",
    borderRadius: "12px",
    padding: "40px 20px",
    textAlign: "center",
    position: "relative",
    minHeight: "240px",
    boxShadow: "0 2px 6px rgba(0,0,0,0.05)",
  },
  planText: {
    fontSize: "20px",
    fontWeight: "600",
    color: "#111827",
  },
  catImage: {
    position: "absolute",
    right: "20px",
    bottom: "20px",
    width: "80px",
    height: "auto",
  },
  challengeArea: {
    backgroundColor: "#ffffff",
    padding: "40px 20px",
    borderRadius: "12px",
    minHeight: "240px",
    boxShadow: "0 2px 6px rgba(0,0,0,0.05)",
    textAlign: "center",
  },
};

export default ManagePage;
