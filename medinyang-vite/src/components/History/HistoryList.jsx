import React from 'react';

const HistoryList = ({ data }) => {
  if (!data.length) return <p>기록이 없습니다.</p>;

  return (
    <div>
      {data.map((item, idx) => (
        <div
          key={idx}
          style={{
            backgroundColor: '#ffffff',
            borderRadius: '8px',
            padding: '12px',
            marginBottom: '12px'
          }}
        >
          <div><strong>병원명:</strong> {item.hospital}</div>
          <div><strong>방문일:</strong> {item.date}</div>
          <div><strong>유형:</strong> {item.type}</div>
          <div><strong>진단명:</strong> {item.diagnosis}</div>
        </div>
      ))}
    </div>
  );
};

export default HistoryList;
