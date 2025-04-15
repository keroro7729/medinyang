import React from 'react';

// 의료 이력 리스트 컴포넌트
// data: 객체 배열 [{ hospital, date, type, diagnosis }, ...]
const HistoryList = ({ data }) => {
  // 데이터가 없으면 안내 문구 출력
  if (!data.length) {
    return (
      <p style={{
        textAlign: 'center',
        color: '#999',
        marginTop: '20px',
      }}>
        기록이 없습니다.
      </p>
    );
  }

  return (
    <div>
      {/* 각 이력을 반복해서 카드 형태로 렌더링 */}
      {data.map((item, idx) => (
        <div
          key={idx}
          style={{
            backgroundColor: '#ffffff',
            borderRadius: '12px',
            padding: '16px',
            marginBottom: '12px',
            boxShadow: '0 2px 6px rgba(0, 0, 0, 0.05)',
            fontSize: '15px',
            lineHeight: '1.6',
          }}
        >
          {/* 병원명 강조 표시 */}
          <div>
            <strong style={{ color: '#2f80ed' }}>병원명:</strong> {item.hospital}
          </div>

          {/* 방문 날짜 */}
          <div>
            <strong>방문일:</strong> {item.date}
          </div>

          {/* 진료 유형 */}
          <div>
            <strong>유형:</strong> {item.type}
          </div>

          {/* 진단명 */}
          <div>
            <strong>진단명:</strong> {item.diagnosis}
          </div>
        </div>
      ))}
    </div>
  );
};

export default HistoryList;
