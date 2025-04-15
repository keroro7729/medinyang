// src/components/common/TopHeader.jsx
import React from 'react';
import { useNavigate } from 'react-router-dom'; // 페이지 이동용 hook
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';

// TopHeader 컴포넌트
// title: 가운데 제목 텍스트
// backTo: 뒤로가기 버튼을 눌렀을 때 이동할 경로
const TopHeader = ({ title = '제목 없음', backTo = '/' }) => {
  const navigate = useNavigate();

  // 뒤로가기 버튼 클릭 시 실행
  const handleBack = () => {
    navigate(backTo); // 지정한 경로로 이동
  };

  return (
    <div
      style={{
        width: '100%', // 가로 전체
        display: 'flex',
        backgroundColor: '#ffffff',
        justifyContent: 'space-between', // 왼쪽, 가운데, 오른쪽 정렬
        alignItems: 'center',
        marginBottom: '45px', // 아래 콘텐츠와 간격
      }}
    >
      {/* 왼쪽 뒤로가기 버튼 */}
      <button
        onClick={handleBack}
        style={{
          backgroundColor: '#ffffff',
          color: '#3B82F6', // 기본 파란색
          fontWeight: 'bold',
          fontSize: '20px',
          padding: '4px',
          borderRadius: '6px',
          border: 'none',
          cursor: 'pointer',
          marginLeft: '20px',
          transition: 'color 0.2s ease', // hover 시 색상 전환
        }}
        onMouseEnter={(e) => (e.currentTarget.style.color = '#1D4ED8')} // 진한 파랑
        onMouseLeave={(e) => (e.currentTarget.style.color = '#3B82F6')} // 원래 색
      >
        <FontAwesomeIcon icon={faArrowLeft} />
      </button>

      {/* 가운데 타이틀 */}
      <h2
        style={{
          fontSize: '16px',
          fontWeight: '600',
          fontFamily: `'Segoe UI', 'Pretendard', 'Noto Sans KR', sans-serif`,
          color: '#111827', // 거의 검정에 가까운 색
        }}
      >
        {title}
      </h2>

      {/* 오른쪽 여백: 균형 맞추기용 (아이콘 자리) */}
      <div style={{ width: '40px' }} />
    </div>
  );
};

export default TopHeader;
