/*
실제 화면 전체 구성 (메인)
*/

import React, { useState, useEffect, useRef } from 'react'; // useEffect, useRef 추가됨
import ChatList from '../components/Chat/ChatList';
import ChatInput from '../components/Chat/ChatInput';

const ChatPage = () => {
  const [messages, setMessages] = useState([]);

  const socket = useRef(null); // WebSocket 객체 저장용 (한 번만 생성됨)

  // 컴포넌트가 처음 로드될 때 웹소켓 연결 설정
  useEffect(() => {
    // 백엔드에서 제공한 WebSocket 주소로 연결
    socket.current = new WebSocket('ws://localhost:8080/ws/chat'); // ← 주소는 실제 백엔드 주소로 바꿔야 함

    // 메시지를 수신했을 때 실행될 함수
    socket.current.onmessage = (event) => {
      const data = JSON.parse(event.data); // 서버에서 받은 JSON 문자열을 JS 객체로 변환
      const gptReply = { sender: 'gpt', text: data.reply }; // 서버가 보낸 응답을 gpt 메시지로 처리
      setMessages((prev) => [...prev, gptReply]); // GPT 메시지 추가
    };

    // 연결이 끊겼을 때 로그 출력 (선택 사항)
    socket.current.onclose = () => {
      console.log('웹소켓 연결 종료');
    };

    // 컴포넌트가 사라질 때 소켓 연결 해제
    return () => {
      socket.current.close();
    };
  }, []);

  const handleSend = (text) => {
    const userMessage = { sender: 'user', text };
    setMessages((prev) => [...prev, userMessage]); // 사용자 메시지는 바로 화면에 출력

    // WebSocket을 통해 백엔드로 메시지 전송
    if (socket.current && socket.current.readyState === WebSocket.OPEN) {
      socket.current.send(JSON.stringify({ message: text })); // 백엔드와 약속한 포맷으로 보냄
    }
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', height: '100vh', backgroundColor: '#ffffff', color: '#000000' }}>
      {/* 상단 바 */}
      <div style={{
        textAlign: 'center',
        padding: '16px',
        fontWeight: 'bold',
        fontSize: '18px',
        borderBottom: '1px solid #ddd',
        backgroundColor: '#ffffff',
        color: '#000000'
      }}>
        메디냥 AI
      </div>

      {/* 메시지 목록 */}
      <ChatList messages={messages} />

      {/* 입력창 */}
      <ChatInput onSend={handleSend} />
    </div>
  );
};

export default ChatPage;
