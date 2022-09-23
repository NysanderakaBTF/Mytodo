import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Timer;

public class TodoItem {
    private String name;
    private String notes;
    private Date date;
    private Time time;
    private boolean completed;


    public TodoItem () {

        name = new String();
        notes = new String("");
        //date = new LocalDate();
        date = getDate();
        completed = false;
    }

    public String getName() {

        return name;
    }
    public String nameProperty() {

        return name;
    }
    public void setName(String s) {

        name = s;
    }

    public String getNotes() {

        return notes;
    }
    public void setNotes(String s) {

        notes = s;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date d) {
        date = d;
    }

    public Time getTime() {

        return time;
    }

    public void setTime(Time t) {
        time = t;
    }


    public boolean getCompleted() {

        return completed;
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "name='" + name + '\'' +
                ", notes='" + notes + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", completed=" + completed +
                '}';
    }

    public void setCompleted(boolean b) {

        completed = b;
    }



    /*
     * Reminders are equal when their names are same on the same date.
     */
    @Override
    public boolean equals(Object obj) {

        if (obj instanceof TodoItem) {

            TodoItem r = (TodoItem) obj;

            if ((r.getName().equals(name) &&
                    (r.getDate().equals(date)))) {

                return true;
            }
        }

        return false;
    }
}