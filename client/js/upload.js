// Biến lưu trữ WebSocket
let socket;

// Biến lưu trữ số lượng file đã upload
let totalFilesToUpload = 0;
let uploadedFilesCount = 0;

// Lấy email từ sessionStorage
const userEmail = sessionStorage.getItem("userEmail");

// Lấy phần tử spinner
const uploadSpinner = document.getElementById('uploadSpinner');

// Bắt sự kiện click của nút upload
document.getElementById('uploadBtn').addEventListener('click', function() {
    document.getElementById('fileInput').click();
});

// Bắt sự kiện khi người dùng chọn file
document.getElementById('fileInput').addEventListener('change', function(event) {
    const files = event.target.files;
    if (files.length > 0) {
        // Chỉ khởi tạo kết nối WebSocket nếu có file được chọn
        if (!socket || socket.readyState === WebSocket.CLOSED) {
            socket = new WebSocket('ws://localhost:7070');

            socket.onopen = function() {
                console.log('WebSocket connection established.');
                // Bắt đầu upload sau khi kết nối thành công
                startFileUpload(files);
                
            };

            socket.onmessage = function(event) {
                const message = event.data;
                handleServerMessage(message);
            };

            socket.onclose = function() {
                console.log('WebSocket connection closed.');
            };
        }
    }
});

// Bắt đầu upload file
function startFileUpload(files) {
    totalFilesToUpload = files.length; // Cập nhật số lượng file
    uploadedFilesCount = 0; // Đặt lại số lượng file đã upload

    // Hiển thị spinner để thông báo cho người dùng biết quá trình upload đang diễn ra
    uploadSpinner.style.display = 'block';

    for (const file of files) {
        uploadFile(file);// Gọi hàm uploadFile để tải lên file
    }
}

function uploadFile(file) {
    const reader = new FileReader();
    reader.onload = function(event) {
        const base64Data = event.target.result.split(',')[1]; // Lấy dữ liệu base64
        const uniqueFileName = getUniqueFileName(file.name); // Kiểm tra và lấy tên file duy nhất

        // Gửi yêu cầu upload tới server với email và dữ liệu file
        const message = `upload:${userEmail},${uniqueFileName},${base64Data}`; // Cập nhật để gửi email
        socket.send(message);
    };
    reader.readAsDataURL(file);
}

// Hàm này sẽ được gọi khi một file được upload thành công
function onFileUploaded(fileName) {
    uploadedFilesCount++; // Tăng số lượng file đã upload
    updateFileList(fileName); // Cập nhật danh sách file sau khi upload thành công
    if (uploadedFilesCount === totalFilesToUpload) {
        // Nếu đã upload tất cả các file, đóng kết nối WebSocket
        socket.close();
        console.log('All files uploaded, WebSocket connection closed.');
        window.location.href = "../html/view.htm"; // Tải lại trang để hiển thị danh sách mới
    }
}


function handleServerMessage(message) {
    // Nếu server gửi phản hồi về việc upload thành công, gọi hàm onFileUploaded
    if (message.startsWith("File uploaded successfully:")) {
        const fileName = message.split(": ")[1]; // Lấy tên file từ phản hồi
        alert(message);
        onFileUploaded(fileName); // Truyền tên file đã upload
    } else {
        socket.close();
        alert(message); // Xử lý thông điệp khác từ server
    }
}

// Hàm lấy danh sách file từ sessionStorage
function getFileList() {
    const userFiles = sessionStorage.getItem("userFiles");
    return userFiles ? userFiles.split(",") : []; // Chia chuỗi thành mảng
}

// Kiểm tra tên file và thêm số (k) vào nếu trùng lặp
function getUniqueFileName(fileName) {
    const userFiles = getFileList();
    let newFileName = fileName;
    let counter = 1;

    while (userFiles.includes(newFileName)) {
        const fileExtension = fileName.split('.').pop(); // Lấy phần mở rộng của file
        const fileNameWithoutExt = fileName.substring(0, fileName.length - fileExtension.length - 1);
        newFileName = `${fileNameWithoutExt} (${counter}).${fileExtension}`; // Thêm số (k) vào tên file
        counter++;
    }

    return newFileName; // Trả về tên file duy nhất
}

// Cập nhật danh sách file vào sessionStorage
function updateFileList(fileName) {
    const userFiles = getFileList();
    userFiles.push(fileName); // Thêm file mới vào danh sách
    sessionStorage.setItem("userFiles", userFiles.join(",")); // Lưu danh sách dưới dạng chuỗi phân tách bằng dấu phẩy
}

