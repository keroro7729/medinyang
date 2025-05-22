import React, { useState, useEffect, useRef } from "react";
import UserDropdownMenu from "./UserDropdown.jsx"; // 🟦 드롭다운 컴포넌트

const UserSwitcher = () => {
  const [currentUser, setCurrentUser] = useState(null);
  const [users, setUsers] = useState([]);
  const [isOpen, setIsOpen] = useState(false);
  const wrapperRef = useRef(null);

  // ✅ 사용자 정보 불러오기
  useEffect(() => {
    const storedUsers = JSON.parse(localStorage.getItem("users") || "[]");
    const storedCurrent = JSON.parse(localStorage.getItem("currentUser"));

    // 로그인 유저가 users에 없으면 추가
    if (storedCurrent && !storedUsers.find((u) => u.id === storedCurrent.id)) {
      storedUsers.push(storedCurrent);
      localStorage.setItem("users", JSON.stringify(storedUsers));
    }

    setUsers(storedUsers);
    setCurrentUser(storedCurrent);
  }, []);

  // ✅ 드롭다운 외부 클릭 시 닫기
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (wrapperRef.current && !wrapperRef.current.contains(event.target)) {
        setIsOpen(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  // ✅ 유저 선택 시 서버에 세션 전환 요청
  const handleSelectUser = async (user) => {
    console.log("선택된 유저:", user);         // ✅ 전체 유저 객체 확인
  console.log("유저 ID:", user.id);         // ✅ 실제로 백엔드로 보내는 ID 확인
    try {
      const res = await fetch(
        `${import.meta.env.VITE_API_BASE_URL}/users/switch/${user.id}`,
        {
          method: "POST",
          credentials: "include", // ✅ 세션 쿠키 포함
        }
      );

      if (!res.ok) throw new Error("세션 전환 실패");

      // ✅ 세션 전환 성공 → currentUser 갱신
      localStorage.setItem("currentUser", JSON.stringify(user));
      setCurrentUser(user);
      setIsOpen(false);
      window.location.reload();
    } catch (err) {
      console.error("유저 전환 실패", err);
      alert("유저 전환에 실패했어요. 다시 로그인 해주세요.");
      window.location.href = "/";
    }
  };

  // ✅ 유저 추가 페이지로 이동
  const handleAddUser = () => {
    setIsOpen(false);
    window.location.href = "/add-user";
  };

  // ✅ 유저 삭제
  const handleDeleteUser = (userToDelete) => {
    const confirmDelete = window.confirm("정말 삭제하시겠습니까?");
    if (!confirmDelete) return;

    const updatedUsers = users.filter((u) => u.id !== userToDelete.id);
    localStorage.setItem("users", JSON.stringify(updatedUsers));
    setUsers(updatedUsers);

    if (userToDelete.id === currentUser?.id) {
      localStorage.removeItem("currentUser");
      setCurrentUser(null);
      window.location.reload();
    }
  };

  return (
    <div ref={wrapperRef} style={styles.wrapper}>
      <div onClick={() => setIsOpen(!isOpen)} style={styles.button}>
        ▼ {currentUser?.name || "등록된 유저 없음"}
      </div>
      {isOpen && (
        <UserDropdownMenu
          users={users}
          onSelectUser={handleSelectUser}
          onAddUser={handleAddUser}
          onDeleteUser={handleDeleteUser}
        />
      )}
    </div>
  );
};

const styles = {
  wrapper: {
    position: "relative",
    padding: "10px 20px",
    zIndex: 1000,
  },
  button: {
    fontWeight: "bold",
    backgroundColor: "white",
    border: "1px solid #ccc",
    borderRadius: "8px",
    padding: "6px 12px",
    cursor: "pointer",
    width: "fit-content",
    boxShadow: "0 2px 4px rgba(0,0,0,0.1)",
  },
};

export default UserSwitcher;
