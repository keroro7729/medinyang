// src/components/common/TopHeader.jsx
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';

const TopHeader = ({ title = '제목 없음', backTo = '/' }) => {
  const navigate = useNavigate();

  const handleBack = () => {
    navigate(backTo);
  };

  return (
    <div
      style={{
        width: '100%',
        display: 'flex',
        backgroundColor: '#ffffff',
        justifyContent: 'space-between',
        alignItems: 'center',
        marginBottom: '45px',
      }}
    >
      <button
        onClick={handleBack}
        style={{
          backgroundColor: '#ffffff',
          color: '#3B82F6',
          fontWeight: 'bold',
          fontSize: '20px',
          padding: '4px',
          borderRadius: '6px',
          border: 'none',
          cursor: 'pointer',
          marginLeft: '20px',
          transition: 'color 0.2s ease',
        }}
        onMouseEnter={(e) => (e.currentTarget.style.color = '#1D4ED8')}
        onMouseLeave={(e) => (e.currentTarget.style.color = '#3B82F6')}
      >
        <FontAwesomeIcon icon={faArrowLeft} />
      </button>

      <h2
        style={{
          fontSize: '16px',
          fontWeight: '600',
          fontFamily: `'Segoe UI', 'Pretendard', 'Noto Sans KR', sans-serif`,
          color: '#111827',
        }}
      >
        {title}
      </h2>

      <div style={{ width: '40px' }} />
    </div>
  );
};

export default TopHeader;
