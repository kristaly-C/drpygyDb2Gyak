package adatb.kristaly.domain;

public class Szoba {
    private int id;
    private int maxperson;
    private boolean includeWC;
    private boolean includeShover;
    private int freeslot;

    public Szoba(int id, int maxperson, boolean includeWC, boolean includeBathroom, int freeslot) {
        this.id = id;
        this.maxperson = maxperson;
        this.includeWC = includeWC;
        this.includeShover = includeBathroom;
        this.freeslot = freeslot;
    }
    public Szoba(int id, int maxperson, int includeWC, int includeShover, int freeslot) {
        this.id = id;
        this.maxperson = maxperson;
        this.includeWC = (includeWC == 1) ? true : false ;
        this.includeShover = (includeShover == 1) ? true : false ;
        this.freeslot = freeslot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxperson() {
        return maxperson;
    }

    public void setMaxperson(int maxperson) {
        this.maxperson = maxperson;
    }

    public boolean isIncludeWC() {
        return includeWC;
    }

    public int isIncludeWCNum() {
        if(this.includeShover){
            return 1;
        }
        return 0;
    }

    public void setIncludeWC(boolean includeWC) {
        this.includeWC = includeWC;
    }

    public boolean isIncludeShover() {
        return includeShover;
    }

    public int isIncludeShoverNum() {
        if(includeShover){
            return 1;
        }
        return 0;
    }

    public void setIncludeShover(boolean includeShover) {
        this.includeShover = includeShover;
    }

    public int getFreeslot() {
        return freeslot;
    }

    public void setFreeslot(int freeslot) {
        this.freeslot = freeslot;
    }

    @Override
    public String toString() {
        return  "id [" + id +
                "] szabad hely [" + freeslot +
                "/" + maxperson +
                "] WC ["  + ((includeWC) ? "van" : "nincs") +
                "] Shover [" + ((includeShover) ? "van" : "nincs") +
                "]";
    }
}
