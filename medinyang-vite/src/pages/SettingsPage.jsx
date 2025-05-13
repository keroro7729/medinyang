// src/pages/SettingsPage.jsx
import React, { useEffect } from "react";
import TopHeader from "../components/common/TopHeader";
import SettingSection from "../components/Settings/SettingSection";
import ScrollAwareBottomNav from "../components/common/ScrollAwareBottomNav";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

const SettingsPage = () => {
  const { isLoggedIn, loading } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (!loading && !isLoggedIn) {
      alert("로그인이 필요한 서비스입니다!");
      navigate("/");
    }
  }, [isLoggedIn, loading, navigate]);

  if (loading) return <p>로딩 중입니다...</p>;

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
    minHeight: "100vh",
    width: "100vw",
    margin: "0 auto",
  },
  container: {
    padding: "16px",
    display: "flex",
    flexDirection: "column",
    gap: "16px",
  },
};

export default SettingsPage;
