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
}
