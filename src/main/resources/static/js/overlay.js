document.addEventListener("DOMContentLoaded", function () {
  const loginBtn = document.getElementById("loginBtn");
  const overlay = document.getElementById("authOverlay");
  const closeBtn = document.querySelector(".auth-container__close");
  const authRegisterButton = document.getElementById("auth-register");
  const authLoginButton = document.getElementById("auth-login");
  const authContainer = document.getElementById("auth-container");

  // Kiểm tra nếu các element tồn tại
  if (
    !loginBtn ||
    !overlay ||
    !closeBtn ||
    !authRegisterButton ||
    !authLoginButton ||
    !authContainer
  ) {
    console.error("Một số element không tồn tại trong DOM");
    return;
  }

  // Hiển thị overlay khi click vào nút đăng nhập
  loginBtn.addEventListener("click", function (e) {
    e.preventDefault();
    overlay.style.display = "flex";
    document.body.style.overflow = "hidden"; // Ngăn scroll khi overlay hiển thị
  });

  // Đóng overlay khi click vào nút đóng
  closeBtn.addEventListener("click", function () {
    overlay.style.display = "none";
    document.body.style.overflow = "auto"; // Cho phép scroll lại
  });

  // Đóng overlay khi click bên ngoài
  overlay.addEventListener("click", function (e) {
    if (e.target === overlay) {
      overlay.style.display = "none";
      document.body.style.overflow = "auto";
    }
  });

  authRegisterButton.addEventListener("click", () => {
    authContainer.classList.add("right-panel-active");
  });

  authLoginButton.addEventListener("click", () => {
    authContainer.classList.remove("right-panel-active");
  });
});
