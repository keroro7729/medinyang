// src/pages/UploadPage.jsx
import React, { useState } from 'react';
import exYes from '../assets/ex_yes.png';
import exNo from '../assets/ex_no.png';

const UploadPage = () => {
  const [file, setFile] = useState(null);

  const handleChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleUpload = () => {
    if (!file) {
      alert('파일을 선택해주세요.');
      return;
    }

    alert(`"${file.name}" 업로드 완료`);
  };

  return (
    <div style={{ maxWidth: '500px', margin: 'auto', padding: '20px' }}>
      <h1 style={{ marginBottom: '20px' }}>의료 기록 업로드</h1>

      <input type="file" accept="image/*,.pdf" onChange={handleChange} />
      {file && <p style={{ marginTop: '10px' }}>선택된 파일: {file.name}</p>}

      <div style={{ margin: '30px 0' }}>
        <p style={{ marginBottom: '10px', fontWeight: 'bold' }}>사진 예시 가이드</p>
        <div style={{ display: 'flex', gap: '10px' }}>
          <div style={{ textAlign: 'center' }}>
            <img src={exYes} alt="올바른 예시" style={{ width: '100px', height: '100px', objectFit: 'cover', borderRadius: '8px' }} />
            <p style={{ fontSize: '12px' }}>ex_yes</p>
          </div>
          <div style={{ textAlign: 'center' }}>
            <img src={exNo} alt="잘못된 예시" style={{ width: '100px', height: '100px', objectFit: 'cover', borderRadius: '8px' }} />
            <p style={{ fontSize: '12px' }}>ex_no</p>
          </div>
        </div>
        <p style={{ marginTop: '10px', fontSize: '14px', color: '#555' }}>
          문서 전체가 보이게, 빛 반사 없이 촬영해주세요.
        </p>
      </div>

      <button
        onClick={handleUpload}
        style={{
          padding: '10px 20px',
          backgroundColor: '#3182ce',
          color: 'white',
          border: 'none',
          borderRadius: '5px',
          cursor: 'pointer'
        }}
      >
        업로드
      </button>
    </div>
  );
};

export default UploadPage;
