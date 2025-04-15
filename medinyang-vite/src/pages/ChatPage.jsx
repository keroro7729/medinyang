import React, { useState, useEffect, useRef } from 'react';
// 메시지 목록을 출력하는 컴포넌트
import ChatList from '../components/Chat/ChatList';
// 입력창과 버튼들이 있는 하단 UI 컴포넌트
import ChatInput from '../components/Chat/ChatInput';
// 상단 고정 헤더 (메디냥 로고/타이틀 등)
import TopHeader from '../components/TopHeader';

const ChatPage = () => {
  // 화면에 표시될 모든 채팅 메시지를 저장하는 state
  const [messages, setMessages] = useState([]);
  // GPT가 응답 중일 때 입력/버튼 비활성화용 상태값
  const [isReplying, setIsReplying] = useState(false);
  // WebSocket 객체를 저장할 ref (React에선 직접 DOM 조작 시 사용)
  const socket = useRef(null);

  useEffect(() => {
    // 페이지가 처음 열릴 때 웹소켓 연결
    socket.current = new WebSocket('ws://localhost:8080/ws/chat');

    // 서버에서 메시지를 받았을 때 처리
    socket.current.onmessage = (event) => {
      const data = JSON.parse(event.data);
      const gptReply = { sender: 'gpt', text: data.reply };
      setMessages((prev) => [...prev, gptReply]); // GPT 응답을 메시지에 추가
      setIsReplying(false); // 응답 완료 처리
    };

    socket.current.onclose = () => console.log('웹소켓 연결 종료');

    // 페이지를 벗어나면 연결 닫기
    return () => socket.current.close();
  }, []);

  // 사용자가 메시지를 보낼 때 실행됨
  const handleSend = (text) => {
    if (!text.trim() || isReplying) return;

    // 먼저 사용자 메시지를 화면에 표시
    setMessages((prev) => [...prev, { sender: 'user', text }]);
    setIsReplying(true);

    // 웹소켓으로 서버에 전송
    if (socket.current?.readyState === WebSocket.OPEN) {
      socket.current.send(JSON.stringify({ message: text }));
    }
  };

  // 응답 중단 요청
  const handleStop = () => {
    if (socket.current?.readyState === WebSocket.OPEN) {
      socket.current.send(JSON.stringify({ stop: true }));
    }
    setIsReplying(false);
  };

  return (
    <div style={{
      height: '100vh',
      width: '100vw',
      display: 'flex',
      flexDirection: 'column',
      backgroundColor: '#f5f5f5',
    }}>
      {/* 상단 고정 헤더 */}
      <TopHeader title="메디냥 AI" />

      {/* 채팅 내용 영역 */}
      <div style={{
        flex: 1,
        overflowY: 'auto',
        padding: '16px',
      }}>
        <ChatList messages={messages} />
      </div>

      {/* 입력창 및 버튼 */}
      <ChatInput
        onSend={handleSend}
        onStop={handleStop}
        isReplying={isReplying}
        onImageUpload={(file) => console.log('이미지 업로드됨:', file)}
      />
    </div>
  );
};

export default ChatPage;
