// src/components/Main/UserDropdownMenu.jsx
import React from "react";

const UserDropdownMenu = ({ users, onSelectUser, onAddUser, onDeleteUser }) => {
  return (
    <div style={styles.menu}>
      {users.length > 0 ? (
        users.map((user) => (
          <div key={user.id} style={styles.itemRow}>
            <div
              style={styles.itemName}
              onClick={() => onSelectUser(user)}
            >
              {user.name}
            </div>
            <button
              style={styles.deleteBtn}
              onClick={(e) => {
                e.stopPropagation(); // 선택 방지
                onDeleteUser(user);
              }}
            >
              🗑
            </button>
          </div>
        ))
      ) : (
        <div style={styles.empty}>등록된 사용자가 없습니다</div>
      )}
      <div style={styles.addBtn} onClick={onAddUser}>
        + 추가하기
      </div>
    </div>
  );
};

const styles = {
  menu: {
    position: "absolute",
    top: "calc(100% + 6px)",
    left: "0",
    backgroundColor: "white",
    color: "#333",
    border: "1px solid #ccc",
    borderRadius: "8px",
    width: "200px",
    zIndex: 1000,
    boxShadow: "0 4px 8px rgba(0,0,0,0.1)",
    padding: "4px 0",
  },
  itemRow: {
    display: "flex",
    alignItems: "center",
    justifyContent: "space-between",
    padding: "8px 12px",
    borderBottom: "1px solid #eee",
    cursor: "pointer",
  },
  itemName: {
    flex: 1,
  },
  deleteBtn: {
    background: "none",
    border: "none",
    color: "#999",
    cursor: "pointer",
    fontSize: "14px",
  },
  addBtn: {
    padding: "10px",
    fontWeight: "bold",
    color: "#2C7EDB",
    cursor: "pointer",
    textAlign: "center",
  },
  empty: {
    padding: "10px",
    color: "#666",
    fontStyle: "italic",
    textAlign: "center",
  },
};

export default UserDropdownMenu;
