package database.model.extended_filters;

enum RelativesFilterType {
  HAS_RELATIVES,
  NO_RELATIVES
}

enum RelativesFilterField {
  AUTHOR,
  CONNECTED_TASKS,
  SUBTASKS
}

public record RelativesFilter(
  RelativesFilterType type,
  RelativesFilterField field
) {
}