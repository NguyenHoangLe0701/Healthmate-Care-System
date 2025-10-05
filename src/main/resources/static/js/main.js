// Dropdown toggle logic cho desktop
const dropdownWrap = document.getElementById("dropdownWrap");
const dropdownBtn = document.getElementById("dropdownBtn");
document.addEventListener("click", function (e) {
  if (dropdownWrap.contains(e.target)) {
    dropdownWrap.classList.toggle("active");
  } else {
    dropdownWrap.classList.remove("active");
  }
});

// Hamburger menu logic
const menuIcon = document.getElementById("menuIcon");
const sideMenu = document.getElementById("sideMenu");
const closeMenu = document.getElementById("closeMenu");
const overlay = document.getElementById("sideMenuOverlay");
menuIcon.onclick = () => {
  sideMenu.classList.add("open");
  overlay.style.display = "block";
};
closeMenu.onclick = () => {
  sideMenu.classList.remove("open");
  overlay.style.display = "none";
};
overlay.onclick = () => {
  sideMenu.classList.remove("open");
  overlay.style.display = "none";
};

// Dropdown in side menu (mobile)
const dropdownBtnMobile = document.getElementById("dropdownBtnMobile");
const dropdownContentMobile = document.getElementById("dropdownContentMobile");
dropdownBtnMobile.onclick = function (e) {
  e.preventDefault();
  dropdownBtnMobile.classList.toggle("active");
  dropdownContentMobile.style.display =
    dropdownContentMobile.style.display === "flex" ? "none" : "flex";
};
// Đóng dropdown khi click ra ngoài (mobile)
document.addEventListener("click", function (e) {
  if (!sideMenu.contains(e.target) && !menuIcon.contains(e.target)) {
    sideMenu.classList.remove("open");
    overlay.style.display = "none";
  }
  if (
    !dropdownBtnMobile.contains(e.target) &&
    !dropdownContentMobile.contains(e.target)
  ) {
    dropdownBtnMobile.classList.remove("active");
    dropdownContentMobile.style.display = "none";
  }
});
// Ngăn sự kiện click trong menu không đóng menu
sideMenu.onclick = function (e) {
  e.stopPropagation();
};

// Slider logic for banner images: shows pairs 1&2, 2&3, 3&1 in sequence
const slider = document.getElementById("ivieBannerSlider");
const imgs = slider.querySelectorAll(".ivie-banner-img");
const dots = document.querySelectorAll(".dot");
const pairs = [
  [0, 1],
  [1, 2],
  [2, 0],
];
let pairIdx = 0;
let intervalId;

function showPair(idx) {
  imgs.forEach((img, i) => {
    img.style.display = pairs[idx].includes(i) ? "block" : "none";
  });
  dots.forEach((dot, i) => {
    dot.classList.toggle("active", i === idx);
  });
  pairIdx = idx;
}

function startSlider() {
  intervalId = setInterval(() => {
    let next = (pairIdx + 1) % pairs.length;
    showPair(next);
  }, 2500);
}

dots.forEach((dot, i) => {
  dot.addEventListener("click", () => {
    clearInterval(intervalId);
    showPair(i);
    startSlider();
  });
});

showPair(0);
startSlider();

const testimonials = [
  {
    quote:
      "Làm mẹ ai mà chả thương con, nhiều khi thấy con ốm đau đưa vào bệnh viện mà nhìn cảnh xếp hàng chen chúc, chờ đợi mà sốt ruột quá. May sao nhờ mấy chị đồng nghiệp giới thiệu đặt trước lịch khám trên IVIE - Bác sĩ ơi nên cũng yên tâm mỗi lần đưa con đi khám.",
    name: "Chị Nguyễn Minh Ngọc",
    role: "Senior Marketing",
    img: "https://ivie.vn/_next/image?url=%2F_next%2Fstatic%2Fimg1.png&w=1920&q=75",
  },
  {
    quote:
      "Đặt khám trước IVIE - Bác sĩ ơi cực kỳ tiện lợi lại còn nhận được nhiều lợi ích nữa chứ. Khi tới bệnh viện mình được đi bằng lối ưu tiên, được các bạn nhân viên y tế hướng dẫn đi khám nhiệt tình. Bên cạnh đó, bệnh viện đã nắm được triệu chứng và yêu cầu của mình đã note trước đó nên khám rất thoái mái và nhanh chóng. Nói chung mình rất ưng dịch vụ của IVIE - Bác sĩ ơi.",
    name: "Chị Nguyễn Thúy Hiền",
    role: "Chuyên viên nhân sự cấp cao",
    img: "data:image/png;base64,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",
  },
  {
    quote:
      "Nhiều khi thắc mắc mấy vấn đề nhạy cảm mà chả biết hỏi ai, cũng may nhờ các bác sĩ trên ứng dụng IVIE - Bác sĩ ơi BÁC SĨ ƠI nhiệt tình giải đáp. Mọi người có câu hỏi nào cứ vào mục HỎI BÁC SĨ trên IVIE - Bác sĩ ơi để được giải đáp nhé. Các bác sĩ ở đây chuyên môn cao mà tận tâm lắm mà còn được ẩn danh nữa nha.",
    name: "Chị Trần Sa Phia",
    role: "HR Manager",
    img: "https://ivie.vn/_next/image?url=%2F_next%2Fstatic%2Fimg3.png&w=1920&q=75",
  },
  {
    quote:
      "Nhờ IVIE, tôi đã tiết kiệm được rất nhiều thời gian khi đặt lịch khám cho gia đình. Dịch vụ rất tiện lợi và nhanh chóng.",
    name: "Chị Lê Thu Hằng",
    role: "Nhân viên văn phòng",
    img: "data:image/png;base64,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",
  },
];

let start = 0;
let selected = 0;

function renderUsers() {
  const userList = document.getElementById("user-list");
  userList.innerHTML = "";
  for (let i = start; i < start + 3 && i < testimonials.length; i++) {
    const t = testimonials[i];
    const div = document.createElement("div");
    div.className = "testimonial-user" + (i === selected ? " active" : "");
    div.innerHTML = `
        <div class="testimonial-user-img-wrap">
          <img class="testimonial-user-img" src="${t.img}" alt="${t.name}">
        </div>
        <div class="testimonial-user-info">
          <div class="testimonial-user-name">${t.name}</div>
          <div class="testimonial-user-role">${t.role}</div>
        </div>
        `;
    div.onclick = () => {
      selected = i;
      renderTestimonial();
      renderUsers();
    };
    userList.appendChild(div);
  }
}

function renderTestimonial() {
  const t = testimonials[selected];
  document.getElementById("testimonial-quote").textContent = t.quote;
}

document.getElementById("arrow-left").onclick = function () {
  // Nếu đang ở đầu, vòng về cuối
  if (start === 0) {
    start = testimonials.length - 3;
    selected = start;
  } else {
    start--;
    if (selected < start) selected = start;
  }
  renderUsers();
  renderTestimonial();
};
document.getElementById("arrow-right").onclick = function () {
  // Nếu đang ở cuối, vòng về đầu
  if (start >= testimonials.length - 3) {
    start = 0;
    selected = 0;
  } else {
    start++;
    if (selected >= start + 3) selected = start + 2;
  }
  renderUsers();
  renderTestimonial();
};

// Khởi tạo ban đầu
renderUsers();
renderTestimonial();
