import React, { useState, useEffect } from "react";
import HistoryList from "../components/History/HistoryList";
import TopHeader from "../components/common/TopHeader";
import ScrollAwareBottomNav from "../components/common/ScrollAwareBottomNav";
import ScrollToTopButton from "../components/common/ScrollToTopButton";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

const dummyData = [
  { hospital: "지콜 병원", date: "2023-03-02", type: "처방전", diagnosis: "감기" },
  { hospital: "지토스키 병원", date: "2024-04-01", type: "건강검진", diagnosis: "고혈압" },
  { hospital: "지토스키 병원", date: "2025-04-03", type: "처방전", diagnosis: "장염" },
  { hospital: "차은우 병원", date: "2021-04-03", type: "건강검진", diagnosis: "과체중으로 체중조절 필요" },
  { hospital: "지토스키 병원", date: "2021-05-03", type: "처방전", diagnosis: "장염" },
  { hospital: "지콜 정형외과", date: "2024-07-03", type: "처방전", diagnosis: "척추측만증으로 물리치료" },
  { hospital: "지토스키 병원", date: "2023-09-03", type: "처방전", diagnosis: "장염" },
  { hospital: "지콜콜 병원", date: "2011-09-03", type: "처방전", diagnosis: "감기" },
  { hospital: "인성 병원", date: "2025-09-01", type: "처방전", diagnosis: "장염" },
  { hospital: "지토스키 병원", date: "2023-09-03", type: "처방전", diagnosis: "장염" },
  { hospital: "지토스키 병원", date: "2023-09-03", type: "처방전", diagnosis: "감기" },
  { hospital: "인성성 병원", date: "2024-11-28", type: "처방전", diagnosis: "노로바이러스" },
  { hospital: "삼성성 병원", date: "2023-09-03", type: "처방전", diagnosis: "감기" },
  { hospital: "삼성성 병원", date: "2025-01-21", type: "건강검진", diagnosis: "고혈압" },
];

const HistoryPage = () => {
  const { isLoggedIn, loading } = useAuth();
  const navigate = useNavigate();

  const [filterType, setFilterType] = useState("전체");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  useEffect(() => {
    if (!loading && !isLoggedIn) {
      alert("로그인이 필요한 서비스입니다!");
      navigate("/");
    }
  }, [isLoggedIn, loading, navigate]);

  if (loading) return <p>로딩 중입니다...</p>;

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
    <div style={styles.wrapper}>
      <div style={styles.container}>
        <TopHeader title="의료 이력 관리" />
        <div style={styles.scrollableContent}>
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
      </div>
      <ScrollAwareBottomNav current="history" />
      <ScrollToTopButton />
    </div>
  );
};

const styles = {
  wrapper: {
    width: "100vw",
    minHeight: "100dvh",
    backgroundColor: "#f5f5f5",
    display: "flex",
    justifyContent: "center",
  },
  container: {
    width: "100%",
    maxWidth: "600px",
    height: "100%",
    display: "flex",
    flexDirection: "column",
    backgroundColor: "#f5f5f5",
  },
  scrollableContent: {
    flex: 1,
    overflowY: "auto",
    padding: "0 20px",
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
