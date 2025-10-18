package database.model;

public record UserWithTaskCount(User user, int tasksCount) {
}
