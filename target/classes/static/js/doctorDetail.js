document.addEventListener("DOMContentLoaded", function () {
  const doctorId = document.querySelector('input[name="doctorId"]').value;

  const bookBtn = document.getElementById("btnBookOrConsult");
  const selectedTimeInput = document.getElementById("selectedTimeInput");
  const selectedDateInput = document.getElementById("selectedDateInput");

  const dateDropdownTrigger = document.getElementById("dateDropdownTrigger");
  const calendar = document.getElementById("calendar");
  const selectedDateDisplay = document.getElementById("selectedDateDisplay");
  const calendarGrid = document.querySelector(".calendar-grid");

  const currentMonthYearDisplay = document.getElementById("currentMonthYear");
  const prevMonthBtn = document.getElementById("prevMonth");
  const nextMonthBtn = document.getElementById("nextMonth");

  const timeSlotContainer = document.getElementById("timeSlotContainer");

  const today = new Date();
  today.setHours(0, 0, 0, 0);

  const currentTime = new Date();

  const initialMonth = parseInt(7);
  const initialYear = parseInt(2025);
  let currentDisplayedDate = new Date(initialYear, initialMonth - 1, 1);

  function formatDateForDisplay(dateObj) {
    const day = String(dateObj.getDate()).padStart(2, "0");
    const month = String(dateObj.getMonth() + 1).padStart(2, "0");
    const year = dateObj.getFullYear();
    return `${day}/${month}/${year}`;
  }

  function formatDateForAPI(dateObj) {
    const year = dateObj.getFullYear();
    const month = String(dateObj.getMonth() + 1).padStart(2, "0");
    const day = String(dateObj.getDate()).padStart(2, "0");
    return `${year}-${month}-${day}`;
  }

  function renderCalendarDays() {
    const existingDayCells = calendarGrid.querySelectorAll(".day-cell");
    existingDayCells.forEach((cell) => {
      if (!cell.classList.contains("day-name")) {
        cell.remove();
      }
    });

    const year = currentDisplayedDate.getFullYear();
    const month = currentDisplayedDate.getMonth();

    currentMonthYearDisplay.textContent = `Tháng ${month + 1} ${year}`;

    const firstDayOfMonth = new Date(year, month, 1);
    const lastDayOfMonth = new Date(year, month + 1, 0);
    const numDaysInMonth = lastDayOfMonth.getDate();

    let firstDayOfWeek = firstDayOfMonth.getDay();

    for (let i = 0; i < firstDayOfWeek; i++) {
      const emptyCell = document.createElement("div");
      emptyCell.classList.add("day-cell", "inactive");
      calendarGrid.appendChild(emptyCell);
    }

    for (let day = 1; day <= numDaysInMonth; day++) {
      const date = new Date(year, month, day);
      const dayCell = document.createElement("div");
      dayCell.classList.add("day-cell");
      dayCell.textContent = day;

      const dateFormatted = formatDateForAPI(date);
      dayCell.dataset.date = dateFormatted;

      const cellDateNoTime = new Date(date);
      cellDateNoTime.setHours(0, 0, 0, 0);

      if (cellDateNoTime < today) {
        dayCell.classList.add("inactive");
      } else if (cellDateNoTime.getTime() === today.getTime()) {
        dayCell.classList.add("today");
      }

      calendarGrid.appendChild(dayCell);
    }

    attachDayCellListeners();

    const initialSelectedDateValue = selectedDateInput.value;
    let targetSelectedCell = null;

    if (initialSelectedDateValue) {
      targetSelectedCell = document.querySelector(
        `.day-cell[data-date="${initialSelectedDateValue}"]`
      );
    }

    if (
      targetSelectedCell &&
      !targetSelectedCell.classList.contains("inactive")
    ) {
      targetSelectedCell.classList.add("active");
      selectedDateInput.value = initialSelectedDateValue;
      selectedDateDisplay.textContent = formatDateForDisplay(
        new Date(initialSelectedDateValue)
      );
      loadTimeSlots(doctorId, initialSelectedDateValue);
    } else {
      const todayFormatted = formatDateForAPI(new Date());
      const todayCell = document.querySelector(
        `.day-cell[data-date="${todayFormatted}"]`
      );
      if (todayCell && !todayCell.classList.contains("inactive")) {
        todayCell.classList.add("active");
        selectedDateInput.value = todayFormatted;
        selectedDateDisplay.textContent = formatDateForDisplay(new Date());
        loadTimeSlots(doctorId, todayFormatted);
      } else {
        selectedDateInput.value = "";
        selectedDateDisplay.textContent = "Chọn ngày";
        timeSlotContainer.innerHTML = "<p>Vui lòng chọn một ngày hợp lệ.</p>";
        bookBtn.style.display = "none";
      }
    }
  }

  async function loadTimeSlots(docId, date) {
    if (!date || !docId) {
      timeSlotContainer.innerHTML = "<p>Vui lòng chọn một ngày.</p>";
      bookBtn.style.display = "none";
      return;
    }

    try {
      const response = await fetch(
        `/api/doctor/${docId}/available-slots?date=${date}`
      );
      const data = await response.json();

      if (data.error) {
        timeSlotContainer.innerHTML = `<p style="color: red;">Lỗi tải giờ: ${data.error}</p>`;
        bookBtn.style.display = "none";
        return;
      }

      const availableSlots = data.timeSlots;
      timeSlotContainer.innerHTML = "";

      if (availableSlots.length === 0) {
        timeSlotContainer.innerHTML =
          "<p>Không có khung giờ trống cho ngày này.</p>";
        bookBtn.style.display = "none";
      } else {
        availableSlots.forEach((slot) => {
          const button = document.createElement("button");
          button.classList.add("time-slot");
          button.textContent = slot;
          button.dataset.time = slot;
          timeSlotContainer.appendChild(button);
        });
        attachTimeSlotListeners();
      }
      selectedTimeInput.value = "";
      bookBtn.style.display = "none";
    } catch (error) {
      console.error("Error loading time slots:", error);
      timeSlotContainer.innerHTML =
        '<p style="color: red;">Không thể tải khung giờ. Vui lòng thử lại.</p>';
      bookBtn.style.display = "none";
    }
  }

  function attachDayCellListeners() {
    const currentCalendarDays = calendarGrid.querySelectorAll(
      ".day-cell:not(.inactive)"
    );
    currentCalendarDays.forEach((dayCell) => {
      dayCell.addEventListener("click", function () {
        if (this.classList.contains("inactive")) {
          return;
        }

        calendarGrid
          .querySelectorAll(".day-cell")
          .forEach((cell) => cell.classList.remove("active"));
        this.classList.add("active");

        const selectedDate = this.dataset.date;
        selectedDateInput.value = selectedDate;

        const selectedDateObj = new Date(selectedDate);
        selectedDateDisplay.textContent = formatDateForDisplay(selectedDateObj);

        calendar.classList.remove("show");

        loadTimeSlots(doctorId, selectedDate);
      });
    });
  }

  function attachTimeSlotListeners() {
    const timeSlotButtons = timeSlotContainer.querySelectorAll(".time-slot");
    timeSlotButtons.forEach((btn) => {
      btn.addEventListener("click", function () {
        timeSlotContainer
          .querySelectorAll(".time-slot")
          .forEach((b) => b.classList.remove("active"));
        this.classList.add("active");
        selectedTimeInput.value = this.dataset.time;
        bookBtn.style.display = "inline-block";
      });
    });
  }

  dateDropdownTrigger.addEventListener("click", function (event) {
    event.stopPropagation();
    calendar.classList.toggle("show");
  });

  document.addEventListener("click", function (event) {
    if (
      !dateDropdownTrigger.contains(event.target) &&
      !calendar.contains(event.target)
    ) {
      calendar.classList.remove("show");
    }
  });

  prevMonthBtn.addEventListener("click", function () {
    currentDisplayedDate.setMonth(currentDisplayedDate.getMonth() - 1);
    renderCalendarDays();
  });

  nextMonthBtn.addEventListener("click", function () {
    currentDisplayedDate.setMonth(currentDisplayedDate.getMonth() + 1);
    renderCalendarDays();
  });

  bookBtn.addEventListener("click", function () {
    const time = selectedTimeInput.value;
    const date = selectedDateInput.value;

    if (!date) {
      alert("Vui lòng chọn một ngày từ lịch.");
      return;
    }
    if (!time) {
      alert("Vui lòng chọn một khung giờ.");
      return;
    }

    document.getElementById("bookingForm").submit();
  });

  renderCalendarDays();
});
