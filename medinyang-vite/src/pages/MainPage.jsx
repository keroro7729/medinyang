// src/pages/MainPage.jsx
import Greeting from "../components/Main/Greeting";
import ActionButtons from "../components/Main/ActionButtons";
import ChallengeSummary from "../components/Main/ChallengeSummary";
import BottomNav from "../components/Main/BottomNav";

const MainPage = () => {
  return (
    <div style={styles.page}>
      <Greeting />
      <ActionButtons />
      <div style={styles.challengeWrapper}>
        <div style={styles.challengeTitleBox}>
          <span style={styles.challengeTitleAccent}>꾸준함</span>
          <span style={styles.challengeTitle}>이 만드는 건강한 습관!</span>
          <p style={styles.challengeDesc}>
            현재 길동님이 진행중인 챌린지예요
          </p>
        </div>
        <ChallengeSummary />
      </div>
      <BottomNav current="main" />
    </div>
  );
};

const styles = {
  page: {
    width: "100vw",
    backgroundColor: "#f9f9f9",
    paddingBottom: "80px", // ✅ 하단 바 안 가리도록
  },
  challengeWrapper: {
    padding: "20px",
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
