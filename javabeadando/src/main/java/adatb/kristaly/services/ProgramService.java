package adatb.kristaly.services;

import adatb.kristaly.repository.ProgramRepo;

import java.sql.Connection;

public class ProgramService {

    Connection connection;

    public ProgramService(Connection connection) {
        this.connection = connection;
    }

    public void deleteTables() {
        ProgramRepo programRepo = new ProgramRepo(connection);
        programRepo.deleteProgramTable();
    }

    public void deleteAll() {
        ProgramRepo programRepo = new ProgramRepo(connection);
        programRepo.deleteAllSzoba();
    }
}
