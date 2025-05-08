import React, { useState } from 'react';
import TopHeader from '../components/common/TopHeader';
import { useNavigate } from 'react-router-dom';


const EditBasicPage = () => {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    이름: '홍길동',
    나이: '29',
    키: '173',
    몸무게: '65',
    성별: '남성',
    가족관계: '1인가구',
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('수정된 정보:', form);
    navigate('/data');
  };

  return (
    <div>
      <TopHeader title="기본정보 수정" />
      <div style={styles.pageWrapper}>
        <form onSubmit={handleSubmit} style={styles.form}>
          {Object.entries(form).map(([key, value]) => (
            <div key={key} style={styles.inputGroup}>
              <label style={styles.label}>{key}</label>
              <input
                type="text"
                name={key}
                value={value}
                onChange={handleChange}
                style={styles.input}
              />
            </div>
          ))}
         
        </form>
        <button type="submit" style={styles.submit}>
            저장
          </button>
      </div>
    </div>
  );
};

const styles = {
  pageWrapper: {
    backgroundColor: '#f8f9fa',
    width: "100vw",
    height: "100dvh",
    padding: '24px',
    //display: 'flex',
    justifyContent: 'center',
    boxSizing: 'border-box',
  },
  form: {
    width: '100%',
    maxWidth: '480px', // 📱 iPhone 14 Pro 기준 꽉 차게
    backgroundColor: '#ffffff',
    padding: '24px',
    borderRadius: '16px',
    boxShadow: '0 4px 12px rgba(0,0,0,0.05)',
    boxSizing: 'border-box',
  },
  inputGroup: {
    marginBottom: '20px',
  },
  label: {
    display: 'block',
    marginBottom: '8px',
    fontWeight: '600',
    fontSize: '15px',
    color: '#333',
  },
  input: {
    width: '100%',
    padding: '12px 14px',
    fontSize: '15px',
    borderRadius: '10px',
    border: '1px solid #ddd',
    backgroundColor: '#fefefe',
    boxSizing: 'border-box',
    outlineColor: '#4CAF50',
  },
  submit: {
    width: "100%",
    backgroundColor: "#3B82F6",
    color: "#ffffff",
    fontWeight: "bold",
    padding: "12px",
    fontSize: "16px",
    marginTop:"15px",
    border: "none",
    borderRadius: "6px",
    cursor: "pointer",
   
  },
};

export default EditBasicPage;
