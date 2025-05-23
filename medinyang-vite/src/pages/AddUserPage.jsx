import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import TopHeader from "../components/common/TopHeader";

const AddUserPage = () => {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    name: "",
    age: "",
    gender: "남성",
    height: "",
    weight: "",
  });
  const [error, setError] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const validate = () => {
    if (!form.name || !form.age || !form.height || !form.weight) {
      return "모든 항목을 입력해주세요.";
    }
    if (isNaN(form.age) || isNaN(form.height) || isNaN(form.weight)) {
      return "나이, 키, 몸무게는 숫자여야 합니다.";
    }
    return null;
  };

  const handleSubmit = async () => {
    const validationError = validate();
    if (validationError) {
      setError(validationError);
      return;
    }

    const requestData = {
      name: form.name,
      age: parseInt(form.age),
      height: parseFloat(form.height),
      weight: parseFloat(form.weight),
      gender: form.gender,
    };

    try {
      const res = await fetch(`${import.meta.env.VITE_API_BASE_URL}/users`, {
        method: "POST",

        headers: {
          "Content-Type": "application/json",
          "ngrok-skip-browser-warning": "69420",
        },
        credentials: "include",
        body: JSON.stringify(requestData),
      });

      if (!res.ok) throw new Error("등록 실패");

      const createdUser = await res.json();
      localStorage.setItem("currentUser", JSON.stringify(createdUser));
      navigate("/main");
    } catch (err) {
      console.error("유저 등록 실패:", err);
      alert("유저 등록에 실패했습니다.");
    }
  };

  return (
    <div style={styles.wrapper}>
      <TopHeader title="기본 정보 입력" />
      <div style={styles.container}>
        {error && <p style={styles.error}>{error}</p>}

        <label style={styles.label}>이름</label>
        <input
          name="name"
          placeholder="이름"
          onChange={handleChange}
          style={styles.input}
        />

        <label style={styles.label}>나이</label>
        <input
          name="age"
          placeholder="(숫자)"
          onChange={handleChange}
          style={styles.input}
        />

        <label style={styles.label}>성별</label>
        <div style={styles.genderWrapper}>
          {["남성", "여성"].map((option) => (
            <button
              key={option}
              type="button"
              onClick={() => {
                setForm((prev) => ({ ...prev, gender: option }));

                if (option === "여성") {
                  fetch(`${import.meta.env.VITE_API_BASE_URL}/auth/test`, {
                    method: "GET",
                    headers: {
                      "Content-Type": "application/json",
                      "ngrok-skip-browser-warning": "69420", // ✅ 이 줄 추가
                    },
                    credentials: "include",
                  })
                    .then((res) => {
                      if (!res.ok) throw new Error("요청 실패");
                      return res.text(); // 또는 .json(), 응답 형식에 따라
                    })
                    .then((data) => {
                      console.log("✅ /auth/test 응답:", data);
                    })
                    .catch((err) => {
                      console.error("❌ /auth/test 요청 실패:", err);
                    });
                }
              }}
              style={{
                ...styles.genderButton,
                ...(form.gender === option ? styles.genderButtonSelected : {}),
              }}
            >
              {option}
            </button>
          ))}
        </div>

        <label style={styles.label}>키</label>
        <input
          name="height"
          placeholder="(cm)"
          onChange={handleChange}
          style={styles.input}
        />

        <label style={styles.label}>몸무게</label>
        <input
          name="weight"
          placeholder="(kg)"
          onChange={handleChange}
          style={styles.input}
        />

        <button onClick={handleSubmit} style={styles.button}>
          등록
        </button>
      </div>
    </div>
  );
};

const styles = {
  wrapper: {
    width: "100vw",
    height: "100dvh",
    overflow: "hidden",
    backgroundColor: "#f8f9fa",
  },
  container: {
    padding: "56px 24px 40px",
    boxSizing: "border-box",
    display: "flex",
    flexDirection: "column",
    height: "100%",
    overflowY: "auto",
  },
  label: {
    fontSize: "15px",
    marginTop: "18px",
    marginBottom: "6px",
    fontWeight: "500",
    display: "block",
  },
  input: {
    width: "95%",
    padding: "10px",
    borderRadius: "12px",
    border: "1px solid #ccc",
    fontSize: "15px",
    backgroundColor: "white",
    marginBottom: "12px",
  },
  genderWrapper: {
    display: "flex",
    justifyContent: "space-between",
    gap: "15px",
    marginTop: "4px",
    marginBottom: "24px",
  },
  genderButton: {
    flex: 1,
    padding: "14px 0",
    borderRadius: "12px",
    border: "1px solid #ccc",
    backgroundColor: "#f1f3f5",
    color: "#333",
    fontSize: "15px",
    cursor: "pointer",
  },
  genderButtonSelected: {
    backgroundColor: "#2C7EDB",
    color: "white",
    fontWeight: "bold",
    border: "1px solid #2C7EDB",
  },
  button: {
    width: "100%",
    padding: "16px",
    marginTop: "100px",
    backgroundColor: "#2C7EDB",
    color: "white",
    border: "none",
    borderRadius: "12px",
    fontWeight: "bold",
    cursor: "pointer",
    fontSize: "17px",
  },
  error: {
    color: "red",
    marginBottom: "12px",
    fontSize: "14px",
    textAlign: "center",
  },
};

export default AddUserPage;
