package database.request;

public enum TaskListSortingType {
    BY_DATE("date"),
    BY_TASK_NAME("title");

    private final String columnName;

    TaskListSortingType(String columnName){
        this.columnName = columnName;
    }

    @Override
    public String toString(){
        return this.columnName;
    }
}