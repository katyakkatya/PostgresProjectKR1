package database.model.extended_filters;

public record SubtasksCompletionFilter(
  SubtasksCompletionFilterType type,
  SubtasksCompletionFilterField field
) {
  public enum SubtasksCompletionFilterType {
    ALL_COMPLETED,
    SOME_COMPLETED
  }

  public enum SubtasksCompletionFilterField {
    COMPLETED, NOT_COMPLETED
  }
}
