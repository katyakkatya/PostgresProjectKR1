package database.request;

import java.util.List;

public record CreateTaskRequest(String title, List<String> subtasks, List<Long> tasksId, Long authorId) {
}
