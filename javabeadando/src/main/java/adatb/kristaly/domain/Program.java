package adatb.kristaly.domain;

import java.util.Date;

public class Program {
    private int programID;
    private String programName;
    private Date programStart;
    private int duration;
    private int maxPerson;

    public Program(int programID, String programName, Date programStart, int duration, int maxPerson) {
        this.programID = programID;
        this.programName = programName;
        this.programStart = programStart;
        this.duration = duration;
        this.maxPerson = maxPerson;
    }

    public int getProgramID() {
        return programID;
    }

    public void setProgramID(int programID) {
        this.programID = programID;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Date getProgramStart() {
        return programStart;
    }

    public void setProgramStart(Date programStart) {
        this.programStart = programStart;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMaxPerson() {
        return maxPerson;
    }

    public void setMaxPerson(int maxPerson) {
        this.maxPerson = maxPerson;
    }
}
