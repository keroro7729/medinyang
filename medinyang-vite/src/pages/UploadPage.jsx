import React, { useState, useRef } from "react";
import TopHeader from "../components/TopHeader";
import BottomNav from "../components/Main/BottomNav"; // 하단바


const UploadPage = () => {
  // 선택된 파일 이름 (텍스트로 표시)
  const [fileName, setFileName] = useState("선택된 파일 없음");

  // 실제 파일 객체
  const [selectedFile, setSelectedFile] = useState(null);

  // 에러 메시지 (확장자 오류 등)
  const [error, setError] = useState("");

  // 숨겨진 input 요소를 트리거하기 위한 ref
  const fileInputRef = useRef(null);

  // 허용 확장자
  const validExtensions = ["jpg", "jpeg", "png", "bmp"];

  // 파일 선택 버튼 클릭 시 input 클릭
  const triggerFileSelect = () => {
    fileInputRef.current.click();
  };

  // 파일 선택되었을 때 실행
  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (!file) return;

    const ext = file.name.split(".").pop().toLowerCase();
    const isValid = validExtensions.includes(ext);

    if (!isValid) {
      setSelectedFile(null);
      setFileName("선택된 파일 없음");
      setError("❗ 이미지 파일 형식이 아닙니다. (JPG, JPEG, PNG, BMP만 허용)");
      return;
    }

    setSelectedFile(file);
    setFileName(file.name);
    setError("");
  };

  // 서버 업로드 함수
  const handleUpload = async () => {
    if (!selectedFile) {
      alert("⚠️ 파일을 먼저 선택해주세요.");
      return;
    }

    try {
      const formData = new FormData();
      formData.append("file", selectedFile);

      const response = await fetch("http://localhost:8080/api/upload", {
        method: "POST",
        body: formData,
        credentials: "include", // ✅ 세션 쿠키(JSESSIONID)를 자동 포함
      });

      if (!response.ok) throw new Error("업로드 실패");

      const result = await response.json();
      console.log("✅ 서버 응답:", result);

      alert("파일 업로드 성공!");
    } catch (err) {
      console.error(err);
      alert("❌ 업로드 실패: " + err.message);
    }
  };

  return (
    <div
      style={{
        width: "100vw",
        height: "100dvh",
        backgroundColor: "#f5f5f5",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <div
        style={{
          width: "100%",
          maxWidth: "500px",
          height: "100%",
          display: "flex",
          flexDirection: "column",
          backgroundColor: "#f5f5f5",
        }}
      >
        {/* 상단 헤더 */}
        <TopHeader title="의료 기록 업로드" />

        {/* 콘텐츠 본문 */}
        <div style={{ padding: "20px", overflowY: "auto", flex: 1 }}>
          {/* 파일 선택 섹션 */}
          <div
            style={{
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
              marginBottom: "8px",
              gap: "12px",
              flexWrap: "wrap",
            }}
          >
            {/* 파일 이름 또는 에러 */}
            <span
              style={{
                fontSize: "14px",
                color: error ? "red" : "#333",
                flex: "1",
                marginLeft: "8px",
              }}
            >
              {error || fileName}
            </span>

            {/* 파일 선택 버튼 */}
            <button
              onClick={triggerFileSelect}
              style={{
                backgroundColor: "#3B82F6",
                color: "#ffffff",
                fontWeight: "bold",
                fontSize: "12px",
                padding: "6px 12px",
                borderRadius: "6px",
                marginRight: "30px",
                cursor: "pointer",
                whiteSpace: "nowrap",
              }}
            >
              파일선택
            </button>

            {/* 숨겨진 input */}
            <input
              ref={fileInputRef}
              type="file"
              accept=".jpg,.jpeg,.png,.bmp"
              onChange={handleFileChange}
              style={{ display: "none" }}
            />
          </div>

          {/* 파일 형식 안내 */}
          <p
            style={{
              fontSize: "12px",
              color: "#9CA3AF",
              marginBottom: "24px",
            }}
          >
            10MB 이하의 이미지 파일만 등록할 수 있습니다. (JPG, JPEG, PNG, BMP)
          </p>

          {/* 주의사항 안내 시각영역 */}
          <div style={{ marginBottom: "24px" }}>
            <h3
              style={{
                fontSize: "14px",
                fontWeight: "bold",
                marginBottom: "8px",
              }}
            >
              📸 사진 업로드 시 주의사항
            </h3>

            {/* 예시 이미지 영역 (임시 박스) */}
            <div
              style={{
                width: "100%",
                height: "220px",
                backgroundColor: "#E5E7EB",
                borderRadius: "10px",
                marginBottom: "10px",
              }}
            />

            {/* 주의사항 텍스트 */}
            <ul
              style={{
                fontSize: "13px",
                color: "#4B5563",
                lineHeight: "1.6",
                paddingLeft: "1rem",
              }}
            >
              <li> 문서 전체가 잘 보이도록 촬영해주세요.</li>
              <li> 빛 반사가 없도록 해주세요.</li>
              <li> 초점이 맞지 않으면 인식이 어려울 수 있어요.</li>
            </ul>
          </div>
        </div>

        {/* 하단 업로드 버튼 */}
        <div style={{ padding: "20px" }}>
          <button
            onClick={handleUpload}
            style={{
              width: "100%",
              backgroundColor: "#3B82F6",
              color: "#ffffff",
              fontWeight: "bold",
              padding: "12px",
              fontSize: "16px",
              border: "none",
              borderRadius: "6px",
              cursor: "pointer",
            }}
          >
            업로드
          </button>
        </div>
      </div>
      <BottomNav current="manage" />  {/* ✅ 하단바 추가 위치 */}
    </div>
  );
};

export default UploadPage;
