package database.request;

import database.model.DbTaskStatus;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record TaskListRequest(List<DbTaskStatus> statuses, @Nullable Long authorId, @Nullable TaskListSorting sorting,
                              FormattingOptions formattingOptions) {
}
