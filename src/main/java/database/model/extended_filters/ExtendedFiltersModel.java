package database.model.extended_filters;

import org.jetbrains.annotations.Nullable;

public record ExtendedFiltersModel(
  @Nullable RelatedTasksStatusFilter relatedTasksFilter,
  @Nullable SubtasksCompletionFilter subtasksFilter,
  @Nullable RelativesFilter relativesFilter
) {
}
