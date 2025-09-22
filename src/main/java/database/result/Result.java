package database.result;

import org.jetbrains.annotations.Nullable;

public record Result<T>(
  @Nullable T data,
  @Nullable String errorMessage,
  Boolean success
) {}