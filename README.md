# Bài đăng ký email — Servlet/JSP

Cần JDK 17 trở lên, Maven, Tomcat 9 và PostgreSQL. Code dùng `javax.servlet` như slide. File `Main.java` là ví dụ console, không phải điểm khởi chạy ứng dụng web.

1. Tạo database `weblisting` trong pgAdmin, mở Query Tool của database đó và chạy file `database.sql`.
2. Trong PowerShell chạy Tomcat, đặt các biến (thay user và mật khẩu thật):

```powershell
$env:EMAIL_DB_URL = 'jdbc:postgresql://localhost:5432/weblisting'
$env:EMAIL_DB_USER = 'postgres'
$env:EMAIL_DB_PASSWORD = 'mat-khau-postgresql'
```

3. Tại thư mục project chạy `mvn clean package`. Sao chép `target/ch02email.war` vào thư mục `webapps` của Tomcat 9.
4. Trong cùng cửa sổ PowerShell đã đặt biến môi trường, chạy `& 'DUONG-DAN-TOMCAT\bin\startup.bat'`. Nếu chạy Tomcat từ IntelliJ, đặt ba biến trong Run Configuration của Tomcat thay thế.
5. Truy cập `http://localhost:8080/ch02email/`. Gửi biểu mẫu, sau đó kiểm tra bằng `SELECT * FROM email_subscribers;`.

Tomcat phải được khởi động lại sau khi thay đổi biến môi trường. Không đưa mật khẩu thật vào mã nguồn hay Git. Nếu kết nối lỗi, xem log Tomcat; ứng dụng chỉ hiển thị trang cảm ơn khi lưu thành công.
