package database.request;

public record CreateDatabaseRequest(String tableName) implements DatabaseRequest {}
