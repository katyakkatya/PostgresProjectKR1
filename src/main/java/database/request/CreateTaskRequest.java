package database.request;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public record CreateTaskRequest(String title, List<String> subtasks, List<Long> tasksId, @Nullable Long authorId) {
}
