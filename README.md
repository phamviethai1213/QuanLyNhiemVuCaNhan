
**# Personal Task Manager – Refactor Report**

**## 1. Giới thiệu**
**Mục tiêu:** Refactor hàm `addNewTaskWithViolations()` theo nguyên tắc:
- **KISS**: Keep It Simple, Stupid
- **DRY**: Don’t Repeat Yourself
- **YAGNI**: You Aren’t Gonna Need It

**Lợi ích đạt được:**
- Code ngắn gọn, rõ ràng, dễ đọc và bảo trì
- Dễ mở rộng, dễ kiểm thử
- Tránh lỗi phát sinh do lặp lại hoặc thêm tính năng chưa cần thiết

---

**## 2. Quy trình thực hiện**

**// Bước 1: Phân tích code gốc**
- Vi phạm **KISS**: Hàm dài >60 dòng, làm quá nhiều việc
- Vi phạm **DRY**: Lặp lại logic kiểm tra chuỗi rỗng, mức độ ưu tiên, lỗi
- Vi phạm **YAGNI**: Thêm `is_recurring`, `recurrence_pattern` nhưng chưa sử dụng

---

**// Bước 2: Tạo nhánh refactor-code trên GitHub Web**
1. Truy cập repository
2. Click chọn nhánh `main`
3. Gõ tên nhánh mới: `feature/refactor-code`
4. Nhấn Enter để tạo

---

**// Bước 3: Thực hiện refactor**
Tách hàm riêng cho các chức năng nhỏ:
```java
private boolean isNullOrEmpty(String str);           // DRY
private void printError(String message);             // DRY
private LocalDate parseDate(String dateStr);         // KISS
private boolean isValidPriority(String priority);    // DRY
private boolean isDuplicateTask(JSONArray tasks...); // KISS
private JSONObject createTaskObject(...);            // KISS
````

**// Xoá hoặc comment phần chưa cần dùng:**

```java
// newTask.put("is_recurring", isRecurring); // YAGNI
// newTask.put("recurrence_pattern", "Chưa xác định"); // YAGNI
```

---

**// Bước 4: Commit rõ ràng từng thay đổi**

```bash
git add .
git commit -m "refactor: tách hàm kiểm tra chuỗi rỗng"
git commit -m "refactor: thêm hàm parseDate xử lý ngày đến hạn"
git commit -m "chore: loại bỏ is_recurring chưa cần thiết"
```

---

**// Bước 5: Kiểm thử & chạy chương trình**

* Thêm nhiệm vụ hợp lệ
* Thêm nhiệm vụ trùng
* Thêm nhiệm vụ thiếu thông tin
* Kiểm tra kết quả hiển thị đúng

---

**// Bước 6: Tạo Pull Request & Merge**

1. Truy cập tab `Pull Requests` trên GitHub
2. Nhấn nút “New Pull Request”
3. Chọn `base: main`, `compare: feature/refactor-code`
4. Review lại code
5. Nhấn `Merge Pull Request`

---

**## 3. So sánh Trước và Sau Refactor**

| Tiêu chí             | Trước Refactor | Sau Refactor      |
| -------------------- | -------------- | ----------------- |
| Độ dài hàm chính     | > 60 dòng      | < 30 dòng         |
| Lặp lại logic        | Có nhiều       | Gần như không còn |
| Khả năng tái sử dụng | Kém            | Cao               |
| Tính rõ ràng         | Thấp           | Cao               |
| Tuân thủ KISS        | Không          | Có                |
| Tuân thủ DRY         | Không          | Có                |
| Tuân thủ YAGNI       | Không          | Có                |

---

**## 4. Kết luận**

* Việc refactor giúp mã nguồn rõ ràng, dễ kiểm soát và nâng cấp.
* Các nguyên tắc KISS / DRY / YAGNI là công cụ mạnh để viết mã sạch.
* Đây là một phần quan trọng trong phát triển phần mềm chuyên nghiệp.



