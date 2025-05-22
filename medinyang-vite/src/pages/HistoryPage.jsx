import React, { useState } from "react";
import HistoryList from "../components/History/HistoryList";
import TopHeader from "../components/common/TopHeader";
import ScrollAwareBottomNav from "../components/common/ScrollAwareBottomNav";
import ScrollToTopButton from "../components/common/ScrollToTopButton";


const dummyData = [
  { hospital: "지콜 병원", date: "2023-03-02", type: "처방전", diagnosis: "감기" },
  { hospital: "지토스키 병원", date: "2024-04-01", type: "건강검진", diagnosis: "당뇨 또는 고혈압 등 만성질환 가능성이 있어요." },
  { hospital: "지토스키 병원", date: "2025-04-03", type: "처방전", diagnosis: "장염" },
  { hospital: "차은우 병원", date: "2021-04-03", type: "건강검진", diagnosis: "체중이 표준보다 높아 생활습관 개선이 권장됩니다. 정기적인 운동과 균형 잡힌 식단을 통해 건강을 관리해보세요."},
  { hospital: "지토스키 병원", date: "2021-05-03", type: "처방전", diagnosis: "장염" },
  { hospital: "지콜 정형외과", date: "2024-07-03", type: "처방전", diagnosis: "척추측만증으로 물리치료" },
  { hospital: "지토스키 병원", date: "2023-09-03", type: "처방전", diagnosis: "장염" },
  { hospital: "지콜 병원", date: "2020-09-03", type: "처방전", diagnosis: "감기" },
  { hospital: "인성 병원", date: "2022-09-01", type: "처방전", diagnosis: "장염" },
  { hospital: "지토스키 병원", date: "2023-09-03", type: "처방전", diagnosis: "장염" },
  { hospital: "지토스키 병원", date: "2023-09-03", type: "처방전", diagnosis: "감기" },
  { hospital: "인성 병원", date: "2024-11-28", type: "처방전", diagnosis: "노로바이러스" },
  { hospital: "삼성 병원", date: "2023-09-03", type: "처방전", diagnosis: "감기" },
  { hospital: "지훈 병원", date: "2025-01-21", type: "건강검진", diagnosis: "⚠️ 일부 항목에서 주의가 필요해요 이번 검진에서 혈압이 약간 높고, 콜레스테롤 수치도 경계 수준입니다." },
  { hospital: "철철 병원", date: "2025-05-10", type: "처방전", diagnosis: "고혈압" },
  { hospital: "삼성 병원", date: "2023-01-21", type: "처방전", diagnosis: "코로나" },
  { hospital: "진욱 병원", date: "2025-01-21", type: "건강검진", diagnosis: "고혈압" },
  { hospital: "삼성 병원", date: "2021-02-04", type: "처방전", diagnosis: "독감" },
  { hospital: "삼성 병원", date: "2024-12-21", type: "처방전", diagnosis: "감기" },

];

const HistoryPage = () => {
  const [filterType, setFilterType] = useState("전체");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  const filteredData = dummyData.filter((item) => {
    const inDateRange =
      (!startDate || item.date >= startDate) &&
      (!endDate || item.date <= endDate);
    const inType = filterType === "전체" || item.type === filterType;
    return inDateRange && inType;
  });

  const sortedData = [...filteredData].sort(
    (a, b) => new Date(b.date) - new Date(a.date)
  );

  return (
    <div style={styles.page}>
      <TopHeader title="의료 이력 관리" />
      <div style={styles.container}>
        <div style={styles.filterContainer}>
          <div style={styles.filters}>
            <label>
              시작일:
              <input
                type="date"
                value={startDate}
                onChange={(e) => setStartDate(e.target.value)}
                style={styles.input}
              />
            </label>
            <label>
              종료일:
              <input
                type="date"
                value={endDate}
                onChange={(e) => setEndDate(e.target.value)}
                style={styles.input}
              />
            </label>
            <select
              value={filterType}
              onChange={(e) => setFilterType(e.target.value)}
              style={{ ...styles.input, minWidth: "110px" }}
            >
              <option value="전체">전체</option>
              <option value="처방전">처방전</option>
              <option value="건강검진">건강검진</option>
            </select>
          </div>
        </div>
        <div style={styles.listWrapper}>
          <HistoryList data={sortedData} />
        </div>
      </div>
      <ScrollAwareBottomNav current="history" />
      <ScrollToTopButton />
    </div>
  );
};

const styles = {
  page: {
    width: "100%",
    minHeight: "100vh",
    backgroundColor: "#f5f5f5",
    overflowX: "hidden", 
    margin: "0 auto",
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
  },
  container: {
    width: "100%",
    maxWidth: "600px",
    padding: "0 20px 100px",
    boxSizing: "border-box",
  },
  filterContainer: {
    position: "sticky",
    top: 0,
    backgroundColor: "#f5f5f5",
    padding: "16px 0 10px",
    zIndex: 10,
    borderBottom: "1px solid #e0e0e0",
  },
  filters: {
    display: "flex",
    flexWrap: "wrap",
    gap: "10px",
  },
  input: {
    marginLeft: "6px",
    padding: "6px 10px",
    borderRadius: "6px",
    border: "1px solid #ccc",
    backgroundColor: "#fff",
    fontSize: "14px",
  },
  listWrapper: {
    marginTop: "16px",
  },
};

export default HistoryPage;
