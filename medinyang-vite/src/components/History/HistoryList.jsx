import React from "react";

const HistoryList = ({ data, onItemClick }) => {
  if (!data.length) {
    return (
      <p style={{ textAlign: "center", color: "#999", marginTop: "20px" }}>
        기록이 없습니다.
      </p>
    );
  }

  return (
    <div>
      {data.map((item, idx) => (
        <div
          key={idx}
          onClick={() => onItemClick?.(item.id)} // ✅ 클릭 시 상세 이동
          style={{
            backgroundColor: "#ffffff",
            borderRadius: "12px",
            padding: "16px",
            marginBottom: "16px",
            boxShadow: "0 2px 6px rgba(0, 0, 0, 0.06)",
            display: "flex",
            justifyContent: "space-between",
            alignItems: "flex-start",
            cursor: "pointer", // ✅ 클릭 가능 느낌
            transition: "transform 0.1s ease-in-out",
          }}
          onMouseEnter={(e) => (e.currentTarget.style.transform = "scale(1.01)")}
          onMouseLeave={(e) => (e.currentTarget.style.transform = "scale(1.0)")}
        >
          {/* 왼쪽: 진료정보 */}
          <div style={{ flex: 1 }}>
            <div style={{ fontSize: "13px", color: "#888" }}>{item.type}</div>
            <div
              style={{
                fontWeight: "bold",
                fontSize: "17px",
                color: "#2f80ed",
              }}
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
              paddingTop: "2px",
              paddingBottom: "2px",
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
                fontSize: "14px",
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
