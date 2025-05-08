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
    minHeight: "100dvh",
    width: "100vw",
    maxWidth: "100vw",
    overflowX: "hidden",
    margin: 0,
    padding: 0,
    boxSizing: "border-box",
    display: "flex",
    flexDirection: "column",
    backgroundColor: "#f9f9f9",
  },
  content: {
    flex: 1,
    padding: "16px",
    paddingBottom: "80px",
    boxSizing: "border-box",
    maxWidth: "600px", // ✅ 적절한 중앙 콘텐츠 폭 제한
    width: "100%",
    margin: "0 auto", // ✅ 가운데 정렬
  },
};

export default MainPage;
