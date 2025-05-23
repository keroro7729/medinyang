// src/pages/MainPage.jsx
import React, { useEffect, useState } from "react";
import Greeting from "../components/Main/Greeting";
import ActionButtons from "../components/Main/ActionButtons";
import ChallengeSummary from "../components/Main/ChallengeSummary";
import BottomNav from "../components/Main/BottomNav";
import UserSwitcher from "../components/Main/UserSwitcher";

const MainPage = () => {
  const [userName, setUserName] = useState("사용자");

  useEffect(() => {
    const stored = localStorage.getItem("currentUser");
    if (stored) {
      try {
        const parsed = JSON.parse(stored);
        if (parsed.name) setUserName(parsed.name);
      } catch (e) {
        console.error("currentUser 파싱 오류:", e);
      }
    }
  }, []);

  return (
    <div style={styles.page}>
      {/* 유저 전환 드롭다운: 상단 고정 */}
      <div style={styles.switcherBox}>
        <UserSwitcher />
      </div>

      {/* 인사 영역 */}
      <Greeting />

      {/* 주요 기능 버튼 */}
      <ActionButtons />

      {/* 챌린지 영역 */}
      <div style={styles.challengeWrapper}>
        <div style={styles.challengeTitleBox}>
          <span style={styles.challengeTitleAccent}>꾸준함</span>
          <span style={styles.challengeTitle}>이 만드는 건강한 습관!</span>
          <p style={styles.challengeDesc}>
            현재 {userName}님이 진행중인 챌린지예요
          </p>
        </div>
        <ChallengeSummary />
      </div>

      {/* 하단 바 */}
      <BottomNav current="main" />
    </div>
  );
};

const styles = {
  page: {
    width: "100%",
    //height: "100%",
    backgroundColor: "#f9f9f9",
    paddingBottom: "80px",
    position: "relative",
    overflow: "visible",
  },
  switcherBox: {
    position: "absolute",
    top: "50px",
    left: "1px",
    //zIndex: 1000,
  },
  challengeWrapper: {
    paddingLeft: "20px",
    paddingRight: "20px",
    paddingTop : "10px",
  },
  challengeTitleBox: {
    marginBottom: "12px",
  },
  challengeTitleAccent: {
    fontSize: "20px",
    fontWeight: "bold",
    color: "#3B82F6",
  },
  challengeTitle: {
    fontSize: "20px",
    fontWeight: "bold",
  },
  challengeDesc: {
    fontSize: "12px",
    color: "#6B7280",
    marginTop: "-2px",
  },
};

export default MainPage;
