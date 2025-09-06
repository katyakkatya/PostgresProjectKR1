package database

import database.request.CreateDatabaseRequest

class StubDatabaseInteractor: DatabaseInteractor {
    override fun createDatabase(request: CreateDatabaseRequest?): Boolean {
        return true
    }
}