package database.request;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public record TaskListRequest(List<String> statuses, @Nullable Long authorId, @Nullable TaskListSorting sorting,
                              FormattingOptions formattingOptions) {
}
