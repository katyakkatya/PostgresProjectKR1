package database.request;

public record GetUsersWithTasksRequest(String regexQuery, int minTasks) {
}
