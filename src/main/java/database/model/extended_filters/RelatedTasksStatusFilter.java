package database.model.extended_filters;

enum RelatedTasksStatusFilterType {
  ALL_HAVE_STATUS,
  SOME_HAVE_STATUS
}

enum RelatedTasksStatusFilterField {
  STATUS
}

public record RelatedTasksStatusFilter(
  RelatedTasksStatusFilterType type,
  RelatedTasksStatusFilterField field
) {
}
