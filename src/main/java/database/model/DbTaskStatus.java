package database.model;

/**
 * This class has statuses as string, but in DB they are stored as enums
 */
public class DbTaskStatus {
  public static final String BACKLOG = "Бэклог";
  public static final String IN_PROGRESS = "В процессе";
  public static final String IN_REVIEW = "На проверке";
  public static final String DONE = "Выполненное";
  public static final String DROPPED = "Отменено";
}
