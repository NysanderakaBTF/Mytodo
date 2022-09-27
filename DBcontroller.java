import java.sql.*;
import java.util.Vector;

public class DBcontroller {
    Connection con = null;
    Statement stm =null;
    String namet = "reminders_table";
    int res;
    public DBcontroller(){
        try {
            //Registering the HSQLDB JDBC driver
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            //Creating the connection with HSQLDB
            con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
            if (con!= null){
                System.out.println("Connection created successfully");

            }else{
                System.out.println("Problem with creating connection");
            }

        }  catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    public void InsertData(TodoItem item) {
        try {
            stm = con.createStatement();
            //  PreparedStatement ps = con.prepareStatement("INSERT INTO reminders_table (name, notes, date, time, completed, id) VALUES (?, ?, ?, ?, ?, ?);",Statement.RETURN_GENERATED_KEYS);
            PreparedStatement ps = con.prepareStatement("INSERT INTO reminders_table (name, notes, date, time, completed) VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, item.getName());
            ps.setString(2, item.getNotes());
            ps.setDate(3, Date.valueOf(item.getDate()));
            ps.setTime(4, Time.valueOf(item.getTime()));
            ps.setBoolean(5, item.getCompleted());
            //  ps.setInt(6,item.getId());


            System.out.println(ps);
            ps.execute();

            ResultSet res = ps.getGeneratedKeys();
            //System.out.println(res.getInt(1));
            //if(res.next()){
            try {
                res.next();
                item.setId(res.getInt(1));
                item.setId(item.getId());
                System.out.println(item.getId());
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void ExtractData(Vector<TodoItem> ve){
        String QUERY = "select * from reminders_table";
        try {
            stm = con.createStatement();
            PreparedStatement ps = con.prepareStatement(QUERY);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String name = rs.getString("name");
                String notes = rs.getString("notes");
                Date date = rs.getDate("date");
                Time time = rs.getTime("time");
                boolean completed = rs.getBoolean("completed");
                int id = rs.getInt("id");
                //System.out.println(id+" "+time);
                ve.add(new TodoItem(name, notes, date.toLocalDate(),time.toLocalTime(),completed, id));
            }


        } catch (SQLException e) {
            System.out.println(e);
        }
        //System.out.println(ve);
    }
    public void UpdateData(TodoItem item){
        try {
            String QUERY = "update reminders_table set name = ?, notes = ?, date = ?, time = ? , completed = ? where id = ?;";
            PreparedStatement ps = con.prepareStatement(QUERY);
            ps.setString(1,item.getName());
            ps.setString(2,item.getNotes());
            ps.setDate(3, Date.valueOf(item.getDate()));
            ps.setTime(4, Time.valueOf(item.getTime()));
            ps.setBoolean(5,item.getCompleted());
            ps.setInt(6,item.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);;
        }

    }
    public void DeleteData(TodoItem item){
        try {
            String QUERY = "delete from reminders_table where id = ?;";
            PreparedStatement ps = con.prepareStatement(QUERY);
            ps.setInt(1,item.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);;
        }

    }
}

