document.addEventListener('DOMContentLoaded', function() {
    const keywordInput = document.getElementById('keywordInput');
    const locationSelect = document.getElementById('locationSelect');
    const sortPriceSelect = document.getElementById('sortPriceSelect');
    const serviceList = document.getElementById('serviceList');

    // Lấy categoryId từ button active
    function getCategoryId() {
        const activeBtn = document.querySelector('.filter-btns .btn-success[data-category-id]');
        return activeBtn ? activeBtn.getAttribute('data-category-id') : null;
    }

    function updateUrlWithFilters(params) {
        let url = '/dichvu';
        if (params.toString()) {
            url += '?' + params.toString();
        } else {
            url += '?';
        }
        history.replaceState(null, '', url);
    }

    function fetchServices() {
        const keyword = keywordInput && keywordInput.value ? keywordInput.value : null;
        const locationId = locationSelect && locationSelect.value ? locationSelect.value : null;
        const sortPrice = sortPriceSelect && sortPriceSelect.value ? sortPriceSelect.value : null;
        const categoryId = getCategoryId();

        const params = new URLSearchParams();
        if (keyword) params.append('keyword', keyword);
        if (locationId) params.append('locationId', locationId);
        if (sortPrice) params.append('sortPrice', sortPrice);
        if (categoryId) params.append('categoryId', categoryId);

        fetch('/dichvu/filter?' + params.toString())
            .then(response => response.text())
            .then(html => {
                if (serviceList) {
                    serviceList.innerHTML = html;
                }
                updateUrlWithFilters(params);
            })
            .catch(error => {
                console.error('Lỗi khi lọc dịch vụ:', error);
            });
    }

    if (keywordInput) keywordInput.addEventListener('input', fetchServices);
    if (locationSelect) locationSelect.addEventListener('change', fetchServices);
    if (sortPriceSelect) sortPriceSelect.addEventListener('change', fetchServices);

    // Thêm event cho các button filter category
    document.querySelectorAll('.filter-btns .btn[data-category-id]').forEach(btn => {
        btn.addEventListener('click', function(e) {
            e.preventDefault();
            document.querySelectorAll('.filter-btns .btn').forEach(b => b.classList.remove('btn-success'));
            this.classList.add('btn-success');
            fetchServices();
        });
    });

    // Khi load trang, nếu không có dấu ? thì thêm vào để đồng bộ trạng thái
    if (!window.location.search) {
        history.replaceState(null, '', window.location.pathname + '?');
    }
});

