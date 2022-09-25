import org.hsqldb.HsqlDateTime;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Timer;

public class TodoItem {
    private String name;
    private String notes;
    private LocalDate date;
    private LocalTime time;
    private boolean completed;
    private int id ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TodoItem () {

        name = new String();
        notes = new String("");
        //date = new LocalDate();
        date = LocalDate.now();
        completed = false;
        id=id++;
    }

    public TodoItem(TodoItem addible) {
        this.name=addible.getName();
        this.date=addible.getDate();
        this.completed=addible.getCompleted();
        this.notes=addible.getNotes();
        this.time=addible.getTime();
        this.id = addible.id;
    }

    public TodoItem(String name, String notes, LocalDate date, LocalTime time, boolean completed) {
        this.name = name;
        this.notes = notes;
        this.date = date;
        this.time = time;
        this.completed = completed;
    }
    public TodoItem(String name, String notes, LocalDate date, LocalTime time, boolean completed, int id) {
        this.name = name;
        this.notes = notes;
        this.date = date;
        this.time = time;
        this.completed = completed;
        this.id = id;
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

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate d) {
        date = d;
    }

    public LocalTime getTime() {

        return time;
    }

    public void setTime(LocalTime t) {
        time = t;
    }


    public boolean getCompleted() {

        return completed;
    }

    @Override
    public String toString() {
        return  "name='" + name + '\n' +
                "notes='" + notes + '\n' +
                "date=" + date +'\n'+
                "time=" + time +'\n'+
                "completed=" + completed +'\n'+
                "ID = " + id+'\n';

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