import React, { useState } from 'react';
import exYes from '../assets/ex_yes.png';
import exNo from '../assets/ex_no.png';
import { useNavigate } from 'react-router-dom';

const UploadPage = () => {
  const [file, setFile] = useState(null);
  const navigate = useNavigate();

  const handleChange = (e) => setFile(e.target.files[0]);

  const handleUpload = () => {
    if (!file) return alert('파일을 선택해주세요.');
    alert(`"${file.name}" 업로드 완료`);
  };

  return (
    <div
      style={{
        width: '100vw',
        height: '100vh',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        backgroundColor: '#f2f4f8',
      }}
    >
      <div
        style={{
          width: '100%',
          maxWidth: '500px',
          height: '100%',
          backgroundColor: '#ffffff',
          padding: '20px',
          boxSizing: 'border-box',
          overflowY: 'auto',
        }}
      >
        <h1 style={{ textAlign: 'center', marginBottom: '20px' }}>의료 기록 업로드</h1>

        <input type="file" accept="image/*,.pdf" onChange={handleChange} />
        {file && <p style={{ marginTop: '10px' }}>선택된 파일: {file.name}</p>}

        <div style={{ margin: '30px 0' }}>
          <p style={{ fontWeight: 'bold' }}>사진 예시 가이드</p>
          <div style={{ display: 'flex', gap: '10px', marginBottom: '10px' }}>
            <img src={exYes} alt="정상 예시" width={100} height={100} />
            <img src={exNo} alt="비정상 예시" width={100} height={100} />
          </div>
          <p style={{ fontSize: '14px', color: '#555' }}>
            문서 전체가 나오도록 <br />
            빛 반사 없이 촬영해주세요.
          </p>
        </div>

        <button
          onClick={handleUpload}
          style={{
            width: '100%',
            padding: '10px',
            backgroundColor: '#3182ce',
            color: 'white',
            border: 'none',
            borderRadius: '6px',
            fontWeight: 'bold',
            fontSize: '16px',
            cursor: 'pointer',
          }}
        >
          업로드
        </button>

        <div style={{ marginTop: '20px', textAlign: 'center' }}>
          <button
            onClick={() => navigate('/chat')}
            style={{
              background: 'none',
              border: 'none',
              color: '#3182ce',
              textDecoration: 'underline',
              fontSize: '14px',
              cursor: 'pointer',
            }}
          >
            💬 메디냥에게 질문하러 가기
          </button>
        </div>
      </div>
    </div>
  );
};

export default UploadPage;
