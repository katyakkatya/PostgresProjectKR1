package database.model.extended_filters;

public record RelativesFilter(
  RelativesFilterType type,
  RelativesFilterField field
) {
  public enum RelativesFilterType {
    HAS_RELATIVES,
    NO_RELATIVES
  }

  public enum RelativesFilterField {
    AUTHOR,
    CONNECTED_TASKS
  }
}