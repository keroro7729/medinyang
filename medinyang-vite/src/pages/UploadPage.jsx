import React, { useState, useRef } from 'react';

const UploadPage = () => {
    const [fileName, setFileName] = useState('선택된 파일 없음');
    const fileInputRef = useRef(null);
  
    const handleFileChange = (e) => {
      const file = e.target.files[0];
      setFileName(file ? file.name : '선택된 파일 없음');
    };
  
    const triggerFileSelect = () => {
      fileInputRef.current.click();
    };
  return (
    <div
      style={{
        width: '100vw',
        height: '100vh',
        backgroundColor: '#f5f5f5',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        //padding: '0 16px',
        //boxSizing: 'border-box',
      }}
    >
      <div
        style={{
          width: '100%',
          maxWidth: '500px', // ✅ 화면 크기 따라 유동 + 중앙 정렬
          height: '100%',
          backgroundColor: '#f5f5f5',
          display: 'flex',
          flexDirection: 'column',
          padding: '20px',
          boxSizing: 'border-box',
          overflowY: 'auto',
          borderRadius: '8px',
          margin: '0 auto', // ✅ 중앙 정렬용
        }}
      >
        {/* 헤더 */}
<div
  style={{
    width:"100%",
    display: 'flex',
    backgroundColor: '#ffffff',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: '45px',
    //padding: '12px',
   
  }}
>
  <button
    style={{
      backgroundColor: '#ffffff',
      //border: '2px solid #3B82F6',
      color: '#3B82F6',
      fontWeight: 'bold',
      fontSize: '20px',
      padding: '4px 12px',
      borderRadius: '6px',
      //cursor: 'pointer',
    }}
  >
    &lt;
  </button>

  <h2
    style={{
      fontSize: '16px', // ← 크기 줄임
      fontWeight: '600',
      fontFamily: `'Segoe UI', 'Pretendard', 'Noto Sans KR', sans-serif`,
      color: '#111827',
    }}
  >
    의료 기록 업로드
  </h2>

  <div style={{ width: '40px' }} /> {/* 버튼 크기 맞춰서 여백 맞춤 */}
</div>


        {/* 📂 커스텀 파일 업로드 */}
<div
  style={{
    display: 'flex',
    justifyContent: 'space-between', // ✅ 좌우 정렬!
    alignItems: 'center',
    marginBottom: '8px',
    //backgroundColor:'',
    gap: '12px',
    flexWrap: 'wrap',
  }}
>
  {/* 왼쪽: 파일 이름 */}
  <span
  style={{
    fontSize: '14px',
    color: '#333',
    flex: '1',
    marginLeft: '8px', // ✅ 왼쪽 여백 추가!
  }}
>
  {fileName}
</span>


  {/* 오른쪽: 파일 선택 버튼 */}
  <button
    onClick={triggerFileSelect}
    style={{
      backgroundColor: '#3B82F6',
      //border: '2px solid #3B82F6',
      color: '#ffffff',
      fontWeight: 'bold',
      fontSize : '12px',
      padding: '6px 12px',
      borderRadius: '6px',
      marginRight:'30px',
      cursor: 'pointer',
      whiteSpace: 'nowrap',
    }}
  >
    파일선택
  </button>

  {/* 숨겨진 input */}
  <input
    ref={fileInputRef}
    type="file"
    accept="image/*,.pdf"
    onChange={handleFileChange}
    style={{ display: 'none' }}
  />
</div>


        <p style={{ fontSize: '12px', color: '#9CA3AF', marginBottom: '24px' }}>
          10MB 이하의 이미지 파일만 등록할 수 있습니다. (JPG, PNG, BMP)
        </p>

        {/* 주의사항 */}
        <div style={{ marginBottom: '24px' }}>
          <h3 style={{ fontSize: '14px', fontWeight: 'bold', marginBottom: '8px' }}>
            📸 사진 업로드 시 주의사항
          </h3>
          <div
            style={{
              width: '100%',
              height: '220px',
              backgroundColor: '#E5E7EB',
              borderRadius: '10px',
              marginBottom: '10px',
            }}
          />
          <ul
            style={{
              fontSize: '13px',
              color: '#4B5563',
              lineHeight: '1.6',
              paddingLeft: '1rem',
            }}
          >
            <li> 문서 전체가 잘 보이도록 촬영해주세요.</li>
            <li> 빛 반사가 없도록 해주세요.</li>
            <li> 초점이 맞지 않으면 인식이 어려울 수 있어요.</li>
          </ul>
        </div>

        {/* 업로드 버튼 */}
        <button
          style={{
            marginTop: 'auto',
            width: '100%',
            backgroundColor: '#3B82F6',
            color: '#ffffff',
            fontWeight: 'bold',
            padding: '12px',
            fontSize: '16px',
            border: 'none',
            borderRadius: '6px',
            cursor: 'pointer',
          }}
        >
          업로드
        </button>
      </div>
    </div>
  );
};

export default UploadPage;
