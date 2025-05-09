import React from "react";

const SettingItem = ({ label }) => {
  return (
    <div style={styles.item}>
      <span>{label}</span>
    </div>
  );
};

const styles = {
  item: {
    padding: "10px 12px",
    borderBottom: "1px solid #A7D8F0",
    fontSize: "14px",
    color: "#374151",
    cursor: "pointer",
  },
};

export default SettingItem;
