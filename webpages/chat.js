const chatBox = document.getElementById('chat-box');
const messageInput = document.getElementById('message-input');
const sendButton = document.getElementById('send-button');

// 连接到 WebSocket 服务器
const socket = new WebSocket('ws://localhost:8888/ws?token=abc');

// 当连接打开时，输出一条消息
socket.addEventListener('open', (event) => {
    console.log('Connected to WebSocket server');
});

// 接收消息
socket.addEventListener('message', (event) => {
    const message = document.createElement('div');
    message.textContent = event.data;
    chatBox.appendChild(message);
    chatBox.scrollTop = chatBox.scrollHeight; // 滚动到底部
});

// 发送消息
sendButton.addEventListener('click', () => {
    const message = messageInput.value;
    if (message) {
        socket.send(message);
        messageInput.value = ''; // 清空输入框
    }
});

// 按下 Enter 键发送消息
messageInput.addEventListener('keypress', (event) => {
    if (event.key === 'Enter') {
        sendButton.click();
    }
});