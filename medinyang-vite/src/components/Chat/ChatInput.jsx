import React, { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faArrowUp,
  faStop,
  faCamera,
  faStethoscope,
  faPills,
} from "@fortawesome/free-solid-svg-icons";

const ChatInput = ({ onSend, onStop, isReplying, onImageUpload }) => {
  const [input, setInput] = useState("");
  const isActive = input.trim().length > 0;

  const handleSend = () => {
    if (!isActive || isReplying) return;
    onSend(input);
    setInput("");
  };

  const handleButtonClick = () => {
    if (isReplying) onStop();
    else handleSend();
  };

  const handlePresetClick = (type) => {
    if (isReplying) return;
    if (type === "symptom") {
      setInput("증상이 심한데 어떻게 해야 하나요? 응급처치는 어떻게 하나요?");
    } else if (type === "drug") {
      setInput("이 약물의 정보와 부작용을 알려주세요.");
    }
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file && onImageUpload) {
      onImageUpload(file);
    }
  };

  return (
    <div
      style={{
        width: "100%",
        boxSizing: "border-box",
        padding: "12px 16px calc(env(safe-area-inset-bottom) + 12px)",
        backgroundColor: "#ffffff",
        borderTop: "1px solid #eee",
      }}
    >
      {/* 입력창 */}
      <div
        style={{
          backgroundColor: "#f9f9f9",
          borderRadius: "999px",
          padding: "10px 20px",
          fontSize: "15px",
          color: "#555",
          boxShadow: "inset 0 0 0 1px #ddd",
        }}
      >
        <input
          type="text"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && handleSend()}
          disabled={isReplying}
          placeholder="메디냥에게 물어보세요!"
          style={{
            width: "100%",
            border: "none",
            outline: "none",
            fontSize: "16px",
            backgroundColor: "transparent",
          }}
        />
      </div>

      {/* 버튼 영역 */}
      <div
        style={{
          marginTop: "10px",
          display: "flex",
          alignItems: "center",
          justifyContent: "space-between",
          flexWrap: "wrap",
          gap: "8px",
        }}
      >
        {/* 왼쪽 버튼들 */}
        <div style={{ display: "flex", gap: "8px", alignItems: "center" }}>
          {/* 이미지 업로드 */}
          <label htmlFor="upload-image" style={iconBtnStyle}>
            <FontAwesomeIcon icon={faCamera} />
            <input
              id="upload-image"
              type="file"
              accept="image/*"
              onChange={handleFileChange}
              style={{ display: "none" }}
              disabled={isReplying}
            />
          </label>

          {/* 증상 프리셋 */}
          <button
            onClick={() => handlePresetClick("symptom")}
            disabled={isReplying}
            style={presetBtnStyle(isReplying)}
          >
            <FontAwesomeIcon
              icon={faStethoscope}
              style={{ marginRight: "6px" }}
            />
            증상/응급처치
          </button>

          {/* 약물 프리셋 */}
          <button
            onClick={() => handlePresetClick("drug")}
            disabled={isReplying}
            style={presetBtnStyle(isReplying)}
          >
            <FontAwesomeIcon icon={faPills} style={{ marginRight: "6px" }} />
            약물 정보
          </button>
        </div>

        {/* 전송 or 응답 멈춤 */}
        <button
          onClick={handleButtonClick}
          disabled={!isActive && !isReplying}
          style={{
            backgroundColor: isReplying
              ? "#B1B1B1"
              : isActive
              ? "#3B82F6"
              : "#bcbcbc",
            border: "none",
            borderRadius: "50%",
            width: "36px",
            height: "36px",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            cursor: !isActive && !isReplying ? "not-allowed" : "pointer",
            transition: "background-color 0.2s ease",
            color: "#fff",
          }}
        >
          <FontAwesomeIcon icon={isReplying ? faStop : faArrowUp} />
        </button>
      </div>
    </div>
  );
};

// 스타일 유틸 함수들
const presetBtnStyle = (disabled) => ({
  backgroundColor: "#f1f1f1",
  border: "none",
  borderRadius: "20px",
  padding: "6px 12px",
  fontSize: "13px",
  fontWeight: 500,
  display: "flex",
  alignItems: "center",
  color: "#333",
  cursor: disabled ? "not-allowed" : "pointer",
  opacity: disabled ? 0.5 : 1,
});

const iconBtnStyle = {
  backgroundColor: "transparent",
  border: "none",
  fontSize: "16px",
  cursor: "pointer",
  color: "#333",
  display: "flex",
  alignItems: "center",
};

export default ChatInput;
