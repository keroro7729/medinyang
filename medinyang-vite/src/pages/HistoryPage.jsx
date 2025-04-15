import React, { useState } from 'react';
// 의료 이력 항목을 출력하는 리스트 컴포넌트 import
import HistoryList from '../components/History/HistoryList';
// 상단 헤더 UI import
import TopHeader from '../components/TopHeader';

// 더미 데이터: 의료 기록 목록 (병원명, 날짜, 유형, 진단명)
const dummyData = [
  { hospital: '지콜 병원', date: '2023-03-02', type: '처방전', diagnosis: '감기' },
  { hospital: '지토스키 병원', date: '2024-04-01', type: '건강검진결과', diagnosis: '고혈압' },
  { hospital: '지토토스키 병원', date: '2025-04-03', type: '처방전', diagnosis: '장염' },
  { hospital: '지토토스키 병원', date: '2021-04-03', type: '건강검진결과', diagnosis: '비만' },
  { hospital: '지토토스키 병원', date: '2021-05-03', type: '처방전', diagnosis: '장염' },
  { hospital: '지토토스키 병원', date: '2024-07-03', type: '처방전', diagnosis: '장염' },
  { hospital: '지토토스키 병원', date: '2023-09-03', type: '처방전', diagnosis: '장염' },
];

const HistoryPage = () => {
  // 선택된 유형, 시작일, 종료일에 대한 상태값 정의
  const [filterType, setFilterType] = useState('전체');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');

  // 날짜 및 유형 필터링 조건 적용
  const filteredData = dummyData.filter(item => {
    const inDateRange =
      (!startDate || item.date >= startDate) &&
      (!endDate || item.date <= endDate);
    const inType = filterType === '전체' || item.type === filterType;
    return inDateRange && inType;
  });

  // 날짜 기준 내림차순 정렬
  const sortedData = [...filteredData].sort((a, b) => new Date(b.date) - new Date(a.date));

  return (
    <div style={{
      width: '100vw',
      height: '100vh',
      backgroundColor: '#f5f5f5',
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
    }}>
      {/* 모바일 중앙 정렬 박스 */}
      <div style={{
        width: '100%',
        maxWidth: '500px',
        height: '100%',
        display: 'flex',
        flexDirection: 'column',
        backgroundColor: '#f5f5f5',
      }}>
        {/* 상단 고정 헤더 */}
        <TopHeader title="의료 이력 관리" />

        {/* 스크롤 가능한 본문 영역 */}
        <div style={{ flex: 1, overflowY: 'auto', padding: '0 20px' }}>
          
          {/* 날짜 및 유형 필터 영역 (상단 고정) */}
          <div style={{
            position: 'sticky',
            top: 0,
            backgroundColor: '#f5f5f5',
            padding: '16px 0 10px',
            zIndex: 10,
            borderBottom: '1px solid #e0e0e0'
          }}>
            <div style={{ display: 'flex', flexWrap: 'wrap', gap: '10px' }}>
              {/* 시작일 필터 */}
              <label>
                시작일:
                <input
                  type="date"
                  value={startDate}
                  onChange={(e) => setStartDate(e.target.value)}
                  style={inputStyle}
                />
              </label>

              {/* 종료일 필터 */}
              <label>
                종료일:
                <input
                  type="date"
                  value={endDate}
                  onChange={(e) => setEndDate(e.target.value)}
                  style={inputStyle}
                />
              </label>

              {/* 유형 필터 (처방전 / 건강검진결과) */}
              <select
                value={filterType}
                onChange={(e) => setFilterType(e.target.value)}
                style={{ ...inputStyle, minWidth: '110px' }}
              >
                <option value="전체">전체</option>
                <option value="처방전">처방전</option>
                <option value="건강검진결과">건강검진결과</option>
              </select>
            </div>
          </div>

          {/* 필터링 + 정렬된 데이터를 리스트로 출력 */}
          <div style={{ marginTop: '16px' }}>
            <HistoryList data={sortedData} />
          </div>
        </div>
      </div>
    </div>
  );
};

// 필터 input 공통 스타일 정의
const inputStyle = {
  marginLeft: '6px',
  padding: '6px 10px',
  borderRadius: '6px',
  border: '1px solid #ccc',
  backgroundColor: '#fff',
  fontSize: '14px',
};

export default HistoryPage;
