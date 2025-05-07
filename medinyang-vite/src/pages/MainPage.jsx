// src/pages/MainPage.jsx
import React from "react";
import Greeting from "../components/Main/Greeting";
import ActionButtons from "../components/Main/ActionButtons";
import ChallengeSummary from "../components/Main/ChallengeSummary";
import BottomNav from "../components/Main/BottomNav";

const MainPage = () => {
  return (
    <div style={styles.page}>
      <Greeting />
      <div style={styles.section}>
        <ActionButtons />
      </div>
      <div style={styles.section}>
        <ChallengeSummary />
      </div>
      <BottomNav current="main" />
    </div>
  );
};

const styles = {
  page: {
    backgroundColor: "#f9f9f9",
    minHeight: "100vh",      // 내용이 적으면 전체화면, 많으면 스크롤 허용
    width: "100vw",       // 너무 넓게 퍼지지 않도록 고정
    margin: "0 auto",        // 가운데 정렬
    display: "flex",
    flexDirection: "column",
    justifyContent: "space-between",
  },
  section: {
    padding: "16px",
  },
};

export default MainPage;