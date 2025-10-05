document.addEventListener('DOMContentLoaded', function() {
    const chatToggle = document.querySelector('.chat-toggle-btn');
    const chatBox = document.querySelector('.chat-box');
    const chatClose = document.querySelector('.chat-close-btn');
    const chatClear = document.querySelector('.chat-clear-btn');
    const chatInput = document.querySelector('.chat-input');
    const chatSend = document.querySelector('.chat-send-btn');
    const chatMessages = document.querySelector('.chat-messages');

    console.log('Chat elements:', {
        chatToggle,
        chatBox,
        chatClose,
        chatClear,
        chatInput,
        chatSend,
        chatMessages
    });

    if (!chatToggle || !chatBox || !chatClose || !chatClear || !chatInput || !chatSend || !chatMessages) {
        console.error('Một hoặc nhiều phần tử không được tìm thấy!');
        return;
    }

    chatToggle.addEventListener('click', function() {
        chatBox.classList.toggle('active');
    });

    chatClose.addEventListener('click', function() {
        chatBox.classList.remove('active');
    });

    chatClear.addEventListener('click', function() {
        chatMessages.innerHTML = '<p>Chào bạn! Hãy chọn một tùy chọn hoặc nhập câu hỏi về sức khỏe sinh sản:</p>';
    });

    document.querySelectorAll('.chat-option').forEach(option => {
        option.addEventListener('click', function(e) {
            e.preventDefault();
            const action = this.getAttribute('data-action');
            addMessage(`Bạn đã chọn: ${action === 'schedule' ? 'Đặt lịch tư vấn trục tuyến' : 'Đặt câu hỏi'}`, 'user');
        });
    });

    chatSend.addEventListener('click', function() {
        const message = chatInput.value.trim();
        if (message) {
            addMessage(`Bạn: ${message}`, 'user');
            respondToMessage(message);
            chatInput.value = '';
        }
    });

    // Thêm sự kiện Enter để gửi tin nhắn
    chatInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter' && chatInput.value.trim()) {
            const message = chatInput.value.trim();
            addMessage(`Bạn: ${message}`, 'user');
            respondToMessage(message);
            chatInput.value = '';
        }
    });

    function addMessage(text, sender = 'bot') {
        const p = document.createElement('p');
        p.textContent = text;
        if (sender === 'user') {
            p.classList.add('user-message');
            // Đảm bảo tin nhắn user nằm sát góc phải
            p.style.marginLeft = 'auto';
            p.style.maxWidth = '70%'; 
        } else {
            p.classList.add('bot-message');
            p.style.maxWidth = '70%'; 
        }
        chatMessages.appendChild(p);
        chatMessages.scrollTop = chatMessages.scrollHeight;

        const messages = chatMessages.getElementsByTagName('p');
        if (messages.length > 6) {
            chatMessages.removeChild(messages[1]);
        }
    }

    function respondToMessage(message) {
        const keywords = {
            'chu kỳ kinh nguyệt': 'Chu kỳ kinh nguyệt trung bình là 28-35 ngày. Nếu bạn gặp bất thường, nên tham khảo ý kiến bác sĩ.',
            'thai kỳ': 'Trong thai kỳ, bạn nên đi khám định kỳ và bổ sung dinh dưỡng như axit folic. Hãy liên hệ tư vấn để biết thêm chi tiết.',
            'sinh sản': 'Sức khỏe sinh sản cần được theo dõi thường xuyên. Bạn có thể đặt lịch xét nghiệm hoặc tư vấn trực tiếp.',
            'stis': 'Các bệnh lây qua đường tình dục (STIs) cần được xét nghiệm sớm. Hãy đặt lịch để được hỗ trợ.',
            'sức khỏe phụ nữ': 'Sức khỏe phụ nữ bao gồm chăm sóc trước và sau sinh. Hãy đặt lịch khám để được tư vấn chi tiết.',
            'sức khỏe nam giới': 'Sức khỏe nam giới liên quan đến sinh sản cần kiểm tra định kỳ, đặc biệt là xét nghiệm STIs.',
            'đặt lịch khám': 'Bạn có thể đặt lịch khám trực tuyến 24/7. Vui lòng cung cấp thông tin để chúng tôi hỗ trợ.',
            'chuẩn bị mang thai': 'Trước khi mang thai, bạn nên kiểm tra sức khỏe tổng quát và bổ sung vitamin cần thiết. Liên hệ để được tư vấn thêm.',
            'bệnh phụ khoa': 'Các bệnh phụ khoa như viêm nhiễm cần được khám sớm. Hãy đặt lịch với chuyên gia của chúng tôi.',
            'truyền thông sức khỏe': 'Chúng tôi cung cấp thông tin về sức khỏe sinh sản qua các bài viết và tư vấn trực tiếp. Bạn muốn biết thêm không?',
            'default': 'Câu hỏi của bạn rất thú vị! Hãy cung cấp thêm thông tin hoặc chọn một tùy chọn để được hỗ trợ tốt hơn.'
        };

        const lowerMessage = message.toLowerCase();
        let response = keywords['default'];

        for (let key in keywords) {
            if (lowerMessage.includes(key)) {
                response = keywords[key];
                break;
            }
        }

        setTimeout(() => addMessage(`IVIE: ${response}`, 'bot'), 500);
    }
});