import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import UploadPage from "./pages/UploadPage";
import ChatPage from "./pages/ChatPage";
import DataPage from './pages/DataPage';
import HistoryPage from "./pages/HistoryPage";
import EditBasicPage from './pages/EditBasicPage';
import ManagePage from './pages/ManagePage';
import SettingsPage from './pages/SettingsPage';
import MainPage from './pages/MainPage';
import LoginPage from "./pages/LoginPage"; // 👈 로그인 페이지 import 추가

function App() {
  return (
    <AuthProvider>
    <Router>
      <Routes>
        <Route path="/data" element={<DataPage />} />
        <Route path="/" element={<LoginPage />} /> {/* 👈 로그인 라우트 추가 */}
        <Route path="/upload" element={<UploadPage />} />
        <Route path="/chat" element={<ChatPage />} />
        <Route path="/edit-basic" element={<EditBasicPage />} />
        <Route path="/history" element={<HistoryPage />} />
        <Route path="/manage" element={<ManagePage />} />
        <Route path="/setting" element={<SettingsPage />} />
        <Route path="/main" element={<MainPage />} />
      </Routes>
    </Router>
    </AuthProvider>
  );
}

export default App;
