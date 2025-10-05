document.addEventListener("DOMContentLoaded", function() {
    const ctx = document.getElementById('userAccessChart').getContext('2d');
    const userAccessChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['T2', 'T3', 'T4', 'T5', 'T6', 'T7', 'CN'],
            datasets: [{
                label: 'Lượt truy cập',
                data: [12, 19, 8, 15, 22, 30, 25], // Dữ liệu mô phỏng
                backgroundColor: 'rgba(0, 150, 136, 0.2)',
                borderColor: 'rgba(0, 150, 136, 1)',
                borderWidth: 3,
                pointBackgroundColor: '#fff',
                pointBorderColor: 'rgba(0, 150, 136, 1)',
                tension: 0.4
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { display: false }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: { stepSize: 5 }
                }
            }
        }
    });
});