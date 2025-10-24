package database.model;

public enum DbTaskStatus {
  BACKLOG("Бэклог"),
  IN_PROGRESS("В процессе"), IN_REVIEW("На проверке"),
  DONE("Выполненное"), DROPPED("Отменено");

  private final String russianName;

  DbTaskStatus(String name){
    this.russianName = name;
  }

  public static DbTaskStatus converter(String russianName){
    for(DbTaskStatus status : DbTaskStatus.values()){
      if(status.toString().equalsIgnoreCase(russianName))
        return status;
    }

    throw new IllegalStateException("Неизвестный статус");
  }

  @Override
  public String toString(){
    return this.russianName;
  }
}
