import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PersonalTaskManager {

    private static final String DB_FILE_PATH = "tasks_database.json";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
    private static final List<String> VALID_PRIORITIES = List.of("Thấp", "Trung bình", "Cao");
    
    public JSONObject addNewTask(String title, String description, String dueDateStr, String priorityLevel) {
    if (!isValidTitle(title) || !isValidDueDate(dueDateStr) || !isValidPriority(priorityLevel)) {
        return null;
    }

    LocalDate dueDate = LocalDate.parse(dueDateStr, DATE_FORMATTER);
    JSONArray tasks = loadTasksFromDb();

    if (isDuplicateTask(tasks, title, dueDate)) {
        System.out.println("Lỗi: Nhiệm vụ đã tồn tại.");
        return null;
    }

    JSONObject newTask = buildTask(title, description, dueDate, priorityLevel);
    tasks.add(newTask);
    saveTasksToDb(tasks);
    System.out.println("Đã thêm nhiệm vụ thành công.");
    return newTask;
    }
    private boolean isValidTitle(String title) {
    if (title == null || title.trim().isEmpty()) {
        System.out.println("Lỗi: Tiêu đề không được để trống.");
        return false;
    }
    return true;
}

private boolean isValidDueDate(String dueDateStr) {
    if (dueDateStr == null || dueDateStr.trim().isEmpty()) {
        System.out.println("Lỗi: Ngày đến hạn không được để trống.");
        return false;
    }
    try {
        LocalDate.parse(dueDateStr, DATE_FORMATTER);
        return true;
    } catch (DateTimeParseException e) {
        System.out.println("Lỗi: Ngày đến hạn không hợp lệ. Định dạng đúng: yyyy-MM-dd");
        return false;
    }
}

private boolean isValidPriority(String priority) {
    if (!VALID_PRIORITIES.contains(priority)) {
        System.out.println("Lỗi: Mức độ ưu tiên không hợp lệ. Chỉ chấp nhận: " + VALID_PRIORITIES);
        return false;
    }
    return true;
}
    private boolean isDuplicateTask(JSONArray tasks, String title, LocalDate dueDate) {
    for (Object obj : tasks) {
        JSONObject task = (JSONObject) obj;
        String taskTitle = task.get("title").toString();
        String taskDueDate = task.get("due_date").toString();
        if (taskTitle.equalsIgnoreCase(title) && taskDueDate.equals(dueDate.format(DATE_FORMATTER))) {
            return true;
        }
    }
    return false;
}
    private JSONObject buildTask(String title, String description, LocalDate dueDate, String priority) {
    JSONObject task = new JSONObject();
    task.put("id", generateTaskId());
    task.put("title", title);
    task.put("description", description);
    task.put("due_date", dueDate.format(DATE_FORMATTER));
    task.put("priority", priority);
    task.put("status", "Chưa hoàn thành");
    String now = LocalDateTime.now().format(DATE_TIME_FORMATTER);
    task.put("created_at", now);
    task.put("last_updated_at", now);
    return task;
}
    private String generateTaskId() {
    return String.valueOf(System.currentTimeMillis());
}
    private JSONArray loadTasksFromDb() {
    JSONParser parser = new JSONParser();
    try (FileReader reader = new FileReader(DB_FILE_PATH)) {
        Object obj = parser.parse(reader);
        if (obj instanceof JSONArray) {
            return (JSONArray) obj;
        }
    } catch (IOException | ParseException e) {
        System.err.println("Lỗi khi đọc file database: " + e.getMessage());
    }
    return new JSONArray();
}

private void saveTasksToDb(JSONArray tasksData) {
    try (FileWriter file = new FileWriter(DB_FILE_PATH)) {
        file.write(tasksData.toJSONString());
        file.flush();
    } catch (IOException e) {
        System.err.println("Lỗi khi ghi file database: " + e.getMessage());
    }
}
    public static void main(String[] args) {
    PersonalTaskManager manager = new PersonalTaskManager();
    manager.addNewTask("Học KISS", "Đọc về nguyên tắc lập trình đơn giản", "2025-07-20", "Cao");
    manager.addNewTask("Học DRY", "Tách hàm tránh trùng lặp", "2025-07-21", "Trung bình");
}
}
