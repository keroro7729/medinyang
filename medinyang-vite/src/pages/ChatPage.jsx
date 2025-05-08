import React, { useState, useEffect, useRef } from "react";
import ChatList from "../components/Chat/ChatList";
import ChatInput from "../components/Chat/ChatInput";
import TopHeader from "../components/TopHeader";

const ChatPage = () => {
  const [messages, setMessages] = useState([]);
  const [isReplying, setIsReplying] = useState(false);
  const socket = useRef(null);

  useEffect(() => {
    socket.current = new WebSocket("ws://localhost:8080/ws/chat");

    socket.current.onmessage = (event) => {
      const data = JSON.parse(event.data);
      const gptReply = { sender: "gpt", text: data.reply };
      setMessages((prev) => [...prev, gptReply]);
      setIsReplying(false);
    };

    socket.current.onclose = () => console.log("웹소켓 연결 종료");

    return () => socket.current.close();
  }, []);

  const handleSend = (text) => {
    if (!text.trim() || isReplying) return;

    setMessages((prev) => [...prev, { sender: "user", text }]);
    setIsReplying(true);

    if (socket.current?.readyState === WebSocket.OPEN) {
      socket.current.send(JSON.stringify({ message: text }));
    }
  };

  const handleStop = () => {
    if (socket.current?.readyState === WebSocket.OPEN) {
      socket.current.send(JSON.stringify({ stop: true }));
    }
    setIsReplying(false);
  };

  return (
    <div
      style={{
        height: "100dvh",
        width: "100vw",
        backgroundColor: "#f5f5f5",
        overflow: "hidden",
        position: "relative",
      }}
    >
      {/* 상단 고정 헤더 */}
      <div
        style={{
          position: "fixed",
          top: 0,
          left: 0,
          width: "100%",
          zIndex: 1000,
          backgroundColor: "#fff",
          boxShadow: "0 1px 4px rgba(0,0,0,0.05)",
        }}
      >
        <TopHeader title="메디냥 AI" />
      </div>

      {/* 채팅 메시지 영역 */}
      <div
        style={{
          position: "absolute",
          top: "56px", // 헤더 높이
          bottom: "96px", // 입력창 높이
          left: 0,
          right: 0,
          overflowY: "auto",
          padding: "16px",
          WebkitOverflowScrolling: "touch",
        }}
      >
        <ChatList messages={messages} />
      </div>

      {/* 하단 고정 입력창 */}
      <div
        style={{
          position: "fixed",
          bottom: 0,
          left: 0,
          width: "100%",
          zIndex: 1000,
          backgroundColor: "#fff",
          paddingBottom: "env(safe-area-inset-bottom)",
        }}
      >
        <ChatInput
          onSend={handleSend}
          onStop={handleStop}
          isReplying={isReplying}
          onImageUpload={(file) => console.log("이미지 업로드됨:", file)}
        />
      </div>
    </div>
  );
};

export default ChatPage;
