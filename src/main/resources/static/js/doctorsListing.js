document.addEventListener("DOMContentLoaded", function () {
  const filterForm = document.getElementById("filterForm");
  const mainSearchInput = document.getElementById("main-search-input");
  const filterSelects = filterForm.querySelectorAll(".filter-bar__select");
  const appointmentDateInput = document.getElementById(
    "appointment-date-input"
  );

  filterSelects.forEach((select) => {
    select.addEventListener("change", function () {
      filterForm.submit();
    });
  });

  if (appointmentDateInput) {
    appointmentDateInput.addEventListener("change", function () {
      filterForm.submit();
    });
  }

  if (mainSearchInput) {
    mainSearchInput.addEventListener("keypress", function (event) {
      if (event.key === "Enter") {
        filterForm.submit();
      }
    });
  }
});
