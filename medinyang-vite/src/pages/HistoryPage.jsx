// ✅ HistoryPage.jsx
import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import HistoryList from "../components/History/HistoryList";
import TopHeader from "../components/common/TopHeader";
import ScrollAwareBottomNav from "../components/common/ScrollAwareBottomNav";
import ScrollToTopButton from "../components/common/ScrollToTopButton";

const PAGE_SIZE = 10;

const HistoryPage = () => {
  const navigate = useNavigate();
  const today = new Date().toISOString().slice(0, 10);
  const [historyData, setHistoryData] = useState([]);
  const [filterType, setFilterType] = useState("전체");
  const [startDate, setStartDate] = useState("2023-01-01");
  const [endDate, setEndDate] = useState(today);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const fetchHistoryData = async () => {
    setLoading(true);
    try {
      const res = await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/medical-images/history`,
        {
          params: {
            startDate,
            endDate,
            page,
            size: PAGE_SIZE,
            sort: "visitDate,desc",
          },
          withCredentials: true,
          headers: {
            "ngrok-skip-browser-warning": "69420",
          },
        }
      );

      const { status, data, message } = res.data;

      if (status !== 200) throw new Error(message || "조회 실패");

      const transformed = data.content
        .filter((item) => filterType === "전체" || item.type === filterType)
        .map((item) => ({
          id: item.historyId,
          hospital: item.hospitalName,
          type: item.type,
          diagnosis: item.shortDescription,
          date: item.visitDate.replace(/\./g, "-"),
        }));

      setHistoryData(transformed);
      setTotalPages(data.totalPages);
      setError("");
    } catch (err) {
      console.error("❌ 의료 이력 요청 실패:", err);
      setError("의료 이력을 불러오지 못했습니다.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchHistoryData();
  }, [page, startDate, endDate, filterType]);

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
          {loading ? (
            <p>불러오는 중입니다...</p>
          ) : error ? (
            <p style={{ color: "red" }}>{error}</p>
          ) : historyData.length === 0 ? (
            <p>조회된 이력이 없습니다.</p>
          ) : (
            <HistoryList
              data={historyData}
              onItemClick={(id) => navigate(`/history/${id}`)}
            />
          )}
        </div>

        <div style={styles.pagination}>
          <button
            onClick={() => setPage((p) => Math.max(0, p - 1))}
            disabled={page === 0}
            style={styles.pageButton}
          >
            이전
          </button>
          <span style={{ margin: "0 12px" }}>{page + 1} / {totalPages}</span>
          <button
            onClick={() => setPage((p) => p + 1)}
            disabled={page >= totalPages - 1}
            style={styles.pageButton}
          >
            다음
          </button>
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
  pagination: {
    marginTop: "20px",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
  },
  pageButton: {
    padding: "6px 14px",
    fontSize: "14px",
    backgroundColor: "#3B82F6",
    color: "#fff",
    border: "none",
    borderRadius: "6px",
    cursor: "pointer",
    disabled: {
      opacity: 0.5,
      cursor: "not-allowed",
    },
  },
};

export default HistoryPage;