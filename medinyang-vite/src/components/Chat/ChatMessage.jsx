import React from "react";
import MedinyangIcon from "../../assets/medi_doctor.png";

const ChatMessage = ({ sender, text }) => {
  const isUser = sender === "user";

  return (
    <div
      style={{
        textAlign: isUser ? "right" : "left",
        marginBottom: "16px",
      }}
    >
      {isUser ? (
        // 사용자 메시지
        <div
          style={{
            maxWidth: "70%",
            backgroundColor: "#A7D8F0",
            padding: "8px 12px",
            borderRadius: "16px",
            display: "inline-block",
            color: "#000",
          }}
        >
          {text}
        </div>
      ) : (
        // GPT 메시지 + 메디냥 아이콘
        <div
          style={{
            display: "flex",
            flexDirection: "column",
            alignItems: "flex-start",
          }}
        >
          <div
            style={{
              color: "#000",
              whiteSpace: "pre-wrap",
              marginLeft: "65px",
            }}
          >
            {text}
          </div>
          <img
            src={MedinyangIcon}
            alt="메디냥"
            style={{ width: 48, height: 45, borderRadius: "50%" }}
          />
        </div>
      )}
    </div>
  );
};

export default ChatMessage;
