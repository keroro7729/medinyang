import React, { useState } from 'react';
import HistoryList from '../components/History/HistoryList';
import TopHeader from '../components/TopHeader';

const dummyData = [
  { hospital: '지콜 병원', date: '2023-03-02', type: '처방전', diagnosis: '감기' },
  { hospital: '지도스키 병원', date: '2024-04-01', type: '건강검진결과', diagnosis: '고혈압' },
  { hospital: '지도스키 병원', date: '2024-04-03', type: '처방전', diagnosis: '장염' },
];

const HistoryPage = () => {
  const [filterType, setFilterType] = useState('전체');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');

  const filteredData = dummyData.filter(item => {
    const inDateRange =
      (!startDate || item.date >= startDate) &&
      (!endDate || item.date <= endDate);
    const inType = filterType === '전체' || item.type === filterType;
    return inDateRange && inType;
  });

  return (
    <div style={{
      width: '100vw',
      height: '100vh',
      backgroundColor: '#f5f5f5',
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
    }}>
      <div style={{
        width: '100%',
        maxWidth: '500px',
        height: '100%',
        display: 'flex',
        flexDirection: 'column',
        backgroundColor: '#f5f5f5',
      }}>
        {/* 헤더 */}
        <TopHeader title="의료 이력 관리" />

        {/* 필터 + 리스트 */}
        <div style={{ flex: 1, overflowY: 'auto', padding: '20px' }}>
          {/* 필터 영역 */}
          <div style={{ marginBottom: '20px' }}>
            <label>
              시작일: <input type="date" value={startDate} onChange={e => setStartDate(e.target.value)} />
            </label>
            <label style={{ marginLeft: '10px' }}>
              종료일: <input type="date" value={endDate} onChange={e => setEndDate(e.target.value)} />
            </label>
            <select
              value={filterType}
              onChange={e => setFilterType(e.target.value)}
              style={{ display: 'block', marginTop: '10px' }}
            >
              <option value="전체">전체</option>
              <option value="처방전">처방전</option>
              <option value="건강검진결과">건강검진결과</option>
            </select>
          </div>

          {/* 리스트 */}
          <HistoryList data={filteredData} />
        </div>
      </div>
    </div>
  );
};

export default HistoryPage;
