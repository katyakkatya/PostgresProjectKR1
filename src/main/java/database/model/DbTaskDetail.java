package database.model;

import java.util.Date;
import java.util.List;

public record DbTaskDetail(Long id, String title, Date startedAt, DbTaskStatus status, List<String> subtaskTitles,
                           List<Boolean> subtaskStatus, List<DbTaskItem> relatedTasks) { }
