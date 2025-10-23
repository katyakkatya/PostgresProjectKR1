package database.model;

import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;

public record DbTaskDetail(Long id, String title, Date startedAt, DbTaskStatus status, List<String> subtaskTitles,
                           List<Boolean> subtaskStatus, List<DbTaskItem> relatedTasks, @Nullable User author) {
}
