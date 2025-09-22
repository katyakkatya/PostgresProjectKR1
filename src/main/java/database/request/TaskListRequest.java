package database.request;

import java.util.List;

public record TaskListRequest(List<String> filters) {
  static String[] FILTERS = {"by_status"};
}
