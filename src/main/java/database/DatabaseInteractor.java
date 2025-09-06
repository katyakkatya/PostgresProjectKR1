package database;

import database.request.CreateDatabaseRequest;

public interface DatabaseInteractor {
    /*
    * Creates database and returns true on success
     */
    boolean createDatabase(CreateDatabaseRequest request);
}
