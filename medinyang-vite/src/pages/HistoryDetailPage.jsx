// ✅ HistoryDetailPage.jsx - 의료 이력 상세 보기 페이지
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom"; // URL 파라미터(id) 추출
import axios from "axios";
import TopHeader from "../components/common/TopHeader";

const HistoryDetailPage = () => {
  const { id } = useParams(); // 경로에서 이력 ID 추출
  const [detail, setDetail] = useState(null); // 상세 이력 데이터
  const [loading, setLoading] = useState(true); // 로딩 상태
  const [error, setError] = useState(""); // 에러 메시지 상태

  // ✅ 이력 상세 API 호출
  useEffect(() => {
    const fetchDetail = async () => {
      setLoading(true);
      try {
        const res = await axios.get(
          `${import.meta.env.VITE_API_BASE_URL}/api/history/detail/${id}`,
          {
            withCredentials: true, // 쿠키 포함
            headers: {
              "ngrok-skip-browser-warning": "69420", // ngrok 우회용 커스텀 헤더
            },
          }
        );
        const { status, data, message } = res.data;

        // 응답 status 코드 검사
        if (status !== 200) throw new Error(message || "조회 실패");

        setDetail(data); // 이력 데이터 상태에 저장
      } catch (err) {
        console.error("❌ 상세 조회 실패:", err);
        setError("상세 정보를 불러오지 못했습니다.");
      } finally {
        setLoading(false);
      }
    };

    fetchDetail();
  }, [id]);

  // ✅ 로딩/에러 상태 표시
  if (loading) return <p style={{ padding: "20px" }}>불러오는 중입니다...</p>;
  if (error) return <p style={{ color: "red", padding: "20px" }}>{error}</p>;

  return (
    <div style={{ padding: "20px" }}>
      <TopHeader title="이력 상세보기" />

      <div style={{ marginTop: "16px" }}>
        {/* 병원 이름 */}
        <p><strong>병원:</strong> {detail.hospitalName}</p>

        {/* 방문 날짜 */}
        <p><strong>방문일자:</strong> {detail.visitDate}</p>

        {/* OCR로 업로드된 의료 이미지 출력 */}
        {detail.imageUrl && (
          <img
            src={detail.imageUrl}
            alt="의료 이미지"
            style={{
              width: "100%",
              maxWidth: "600px",
              margin: "20px 0",
              borderRadius: "8px",
            }}
          />
        )}

        {/* AI 분석 결과 출력 */}
        <p><strong>AI 분석 결과:</strong></p>
        <pre
          style={{
            whiteSpace: "pre-wrap",
            background: "#f9f9f9",
            padding: "10px",
            borderRadius: "6px",
          }}
        >
          {detail.longDescription}
        </pre>
      </div>
    </div>
  );
};

export default HistoryDetailPage;
