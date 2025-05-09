// src/pages/DataPage.jsx
import React from "react";
import TopHeader from "../components/common/TopHeader";
import DataBasic from "../components/Data/DataBasic";
import DataReport from "../components/Data/DataReport";
import DataEdit from "../components/Data/DataEdit";
import ScrollAwareBottomNav from "../components/common/ScrollAwareBottomNav";
import { useAuth } from "../context/AuthContext"; // ✅ 로그인 상태 가져오기

const DataPage = () => {
  const { isLoggedIn, loading } = useAuth(); // ✅ 로그인 정보

  if (loading) return <p>로딩 중입니다...</p>;
  if (!isLoggedIn) return <p>로그인이 필요합니다.</p>;

  return (
    <div>
      <TopHeader title="나의 건강 DATA" />
      <div
        style={{
          padding: "20px",
          backgroundColor: "#f8f9fa",
          minHeight: "100vh",
          width: "100vw",
          margin: "0 auto",
          boxSizing: "border-box",
        }}
      >
        <DataBasic />
        <DataReport />
        <DataEdit />
      </div>
      <ScrollAwareBottomNav current="data" />
    </div>
  );
};

export default DataPage;
