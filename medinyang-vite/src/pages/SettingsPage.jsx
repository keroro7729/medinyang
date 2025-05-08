import React from "react";
import TopHeader from "../components/common/TopHeader";
import SettingSection from "../components/Settings/SettingSection";
import BottomNav from "../components/Main/BottomNav"; // 하단바
import ScrollAwareBottomNav from "../components/common/ScrollAwareBottomNav";

const SettingsPage = () => {
  return (
    <div style={styles.page}>
      <TopHeader title="설정" />

      <div style={styles.container}>
        <SettingSection
          title="계정 관리"
          items={["로그아웃", "계정 정보(이메일, 닉네임 등)", "탈퇴하기"]}
        />
        <SettingSection
          title="앱 정보"
          items={["버전 정보", "업데이트 확인"]}
        />
        <SettingSection
          title="약관 및 정책"
          items={["이용 약관", "개인정보 처리 방침"]}
        />
        <SettingSection
          title="문의 및 피드백"
          items={["고객센터", "피드백 보내기"]}
        />
      </div>
      <ScrollAwareBottomNav current="setting" />
    </div>
  );
};

const styles = {
  page: {
    backgroundColor: "#f9f9f9",
    minHeight: "100vh",      // 내용이 적으면 전체화면, 많으면 스크롤 허용
    width: "100vw",       // 너무 넓게 퍼지지 않도록 고정
    margin: "0 auto",        // 가운데 정렬
  },
  container: {
    padding: "16px",
    display: "flex",
    flexDirection: "column",
    gap: "16px",
  },
};

export default SettingsPage;
