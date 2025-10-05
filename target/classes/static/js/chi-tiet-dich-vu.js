function selectTime(btn, time) {
    document.querySelectorAll('.slot-btn').forEach(b => b.classList.remove('active'));
    btn.classList.add('active');
    document.getElementById('appointmentTime').value = time;
    document.getElementById('bookingBtnWrap').style.display = 'block';
}





