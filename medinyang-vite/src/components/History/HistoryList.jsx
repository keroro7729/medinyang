import React from "react";

// 리디자인된 의료 이력 카드 컴포넌트
const HistoryList = ({ data }) => {
  if (!data.length) {
    return (
      <p
        style={{
          textAlign: "center",
          color: "#999",
          marginTop: "20px",
        }}
      >
        기록이 없습니다.
      </p>
    );
  }

  return (
    <div>
      {data.map((item, idx) => (
        <div
          key={idx}
          style={{
            backgroundColor: "#ffffff",
            borderRadius: "12px",
            padding: "16px",
            marginBottom: "16px",
            boxShadow: "0 2px 6px rgba(0, 0, 0, 0.06)",
            display: "flex",
            justifyContent: "space-between",
            alignItems: "flex-start", // 👈 핵심 포인트: 위로 정렬
          }}
        >
          {/* 왼쪽: 진료정보 */}
          <div style={{ flex: 1 }}>
            <div style={{ fontSize: "13px", color: "#888" }}>{item.type}</div>
            <div
              style={{ fontWeight: "bold", fontSize: "17px", color: "#2f80ed" }}
            >
              {item.hospital}
            </div>
            <div style={{ fontSize: "14px", color: "#555", marginTop: "4px" }}>
              {item.date}
            </div>
          </div>

          {/* 오른쪽: AI 요약 */}
          <div
            style={{
              flex: 1.5,
              paddingLeft: "12px",
              borderLeft: "2px solid #e0e0e0",
              paddingTop: "2px", // 위 여백
              paddingBottom: "2px", // 아래 여백
            }}
          >
            <div
              style={{
                fontSize: "13px",
                color: "#888",
                marginBottom: "4px",
              }}
            >
              {item.type ? "AI 요약" : ""}
            </div>

            <div
              style={{
                fontWeight: "bold",
                fontSize: "14px", // 글자 크기도 맞춤
              }}
            >
              {item.diagnosis}
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};

export default HistoryList;
