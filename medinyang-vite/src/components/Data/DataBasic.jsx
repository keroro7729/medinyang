import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

const DataBasic = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);

  useEffect(() => {
    const stored = localStorage.getItem("currentUser");
    if (stored) {
      try {
        const parsed = JSON.parse(stored);
        setUser(parsed);
      } catch (e) {
        console.error("❌ 유저 정보 파싱 오류:", e);
      }
    }
  }, []);

  if (!user) {
    return <p style={{ padding: '20px' }}>유저 정보를 불러오는 중입니다...</p>;
  }

  return (
    <div style={styles.card}>
      <h3>기본정보</h3>
      <div style={styles.grid}>
        <div style={styles.row}><strong>이름:</strong> {user.name}</div>
        <div style={styles.row}><strong>나이:</strong> {user.age}</div>
        <div style={styles.row}><strong>성별:</strong> {user.gender}</div>
        <div style={styles.row}><strong>키:</strong> {user.height} cm</div>
        <div style={styles.row}><strong>몸무게:</strong> {user.weight} kg</div>
      </div>
      <button
        onClick={() => navigate('/edit-basic', { state: { from: '/data' } })}
        style={styles.button}
      >
        수정하기
      </button>
    </div>
  );
};

const styles = {
  card: {
    background: 'white',
    padding: '16px',
    borderRadius: '10px',
    marginBottom: '20px',
    boxShadow: '0 1px 4px rgba(0,0,0,0.1)',
  },
  row: {
    marginBottom: '8px',
  },
  button: {
    marginTop: '12px',
    padding: '8px 16px',
    backgroundColor: '#3B82F6',
    color: 'white',
    border: 'none',
    borderRadius: '6px',
    cursor: 'pointer',
  },
};

export default DataBasic;
