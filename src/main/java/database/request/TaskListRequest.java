package database.request;

import database.model.DbTaskStatus;
import java.util.List;

public record TaskListRequest(List<DbTaskStatus> statuses) {
}
