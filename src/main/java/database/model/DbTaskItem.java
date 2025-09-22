package database.model;

import java.util.Date;

public record DbTaskItem(Long id, String title, Date startedAt, DbTaskStatus status, Integer subtaskCount,
                         Integer completedSubtasksCount) {
}
