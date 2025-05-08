// src/pages/MainPage.jsx
import React from "react";
import Greeting from "../components/Main/Greeting";
import ActionButtons from "../components/Main/ActionButtons";
import ChallengeSummary from "../components/Main/ChallengeSummary";
import BottomNav from "../components/Main/BottomNav";

const MainPage = () => {
  return (
    <div style={styles.container}>
      <div style={styles.content}>
        <Greeting />
        <ActionButtons />
        <ChallengeSummary />
      </div>
      <BottomNav current="main" />
    </div>
  );
};

const styles = {
  container: {
    height: "100dvh",         // 화면에 맞게 고정
    width: "100vw",
    overflow: "hidden",       // 스크롤 제거
    margin: 0,
    padding: 0,
    boxSizing: "border-box",
    display: "flex",
    flexDirection: "column",
    backgroundColor: "#f9f9f9",
  },
  content: {
    flex: 1,
    maxWidth: "600px",
    width: "100%",
    margin: "0 auto",
    padding: "0 16px",
    boxSizing: "border-box",
    display: "flex",
    flexDirection: "column",
    justifyContent: "center",       // ✅ 중앙 배치
    gap: "24px",                    // ✅ 요소 간 여백
  },
};