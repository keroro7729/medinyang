import React from "react";
import TopHeader from "../components/TopHeader";
import DataBasic from "../components/Data/DataBasic";
import DataReport from "../components/Data/DataReport";
import DataEdit from "../components/Data/DataEdit";
import BottomNav from "../components/Main/BottomNav"; // 하단바

const DataPage = () => {
  return (
    <div>
      <TopHeader title="나의 건강 DATA" />
      <div
  style={{
    padding: "20px",
    backgroundColor: "#f8f9fa",
    minHeight: "100vh",      // 내용이 적으면 전체화면, 많으면 스크롤 허용
    width: "100vw",       // 너무 넓게 퍼지지 않도록 고정
    margin: "0 auto",        // 가운데 정렬
    boxSizing: "border-box",
    
  }}
>

        <DataBasic />
        <DataReport />
        <DataEdit />
      </div>
      <BottomNav current="manage" />  {/* ✅ 하단바 추가 위치 */}
    </div>
  );
};

export default DataPage;