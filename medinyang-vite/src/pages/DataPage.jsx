import React from "react";
import TopHeader from "../components/common/TopHeader";
import DataBasic from "../components/Data/DataBasic";
import DataReport from "../components/Data/DataReport";
import DataEdit from "../components/Data/DataEdit";
import ScrollAwareBottomNav from "../components/common/ScrollAwareBottomNav"; // ✅ 스크롤 감지용 하단바

const DataPage = () => {
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
      <ScrollAwareBottomNav current="data" /> {/* ✅ 하단바 교체 */}
    </div>
  );
};

export default DataPage;
