
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;


class AddDialog extends Dialog {
    String name;
    String desc;
    LocalDate date;
    LocalTime time;
    TextField nn = new TextField("Enter name");
    JButton ads = new JButton("Add");
    JButton cans = new JButton("Cansel");
    TextArea ss = new TextArea("Event Description");
    TextField ti = new TextField("Enter time");
    UtilDateModel model = new UtilDateModel();
    JDatePanelImpl datePanel = new JDatePanelImpl(model);
    JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);




    public AddDialog(Frame parent, TodoItem item){
        super(parent, true);
        nn.setColumns(50);
        setLayout(new BorderLayout());
        JPanel inputs = new JPanel();
        JPanel i1 = new JPanel();
        i1.add(new JLabel("Name:"));
        i1.add(nn);
        ss.setColumns(20);
       // ss.setSize(400, 200);
        ss.setBounds(0,50,400,200);
        inputs.add(i1, BorderLayout.NORTH);
        inputs.add(ss, BorderLayout.CENTER);



        inputs.add(datePicker, BorderLayout.SOUTH);
        inputs.add(ti, BorderLayout.SOUTH);
        ti.setColumns(5);

        JPanel dt = new JPanel();
        dt.add(ads);
        dt.add(cans);

        add(dt,BorderLayout.SOUTH);
        add(inputs, BorderLayout.CENTER);
        setSize(500,500);

//        addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent windowEvent){
//                dispose();
//            }
//        })
        cans.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                item.setName(String.valueOf(Integer.MIN_VALUE));
                dispose();
            }
        });
        ads.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //date=LocalDate.parse(datePicker.getJFormattedTextField().getText());
                boolean q = true;
                boolean t=false,d= false;
                while (!t) {
                    name = nn.getText();
                    if(name.length()<=50){
                        t=true;
                    }else{
                        JOptionPane.showMessageDialog(null, "Name length must be under 50 symbols ", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
                while (!d) {
                    desc = ss.getText();
                    if(desc.length()<=500){
                        d=true;
                    }else{
                        JOptionPane.showMessageDialog(null, "Descriptions length must be under 500 symbols ", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }

                try {
                    time = LocalTime.parse(ti.getText());
                } catch (java.lang.Exception ee) {
                    JOptionPane.showMessageDialog(null, "Enter a valid time format", "ERROR", JOptionPane.ERROR_MESSAGE);
                    q=false;
                }
                try {
                    Date da = (Date) datePicker.getModel().getValue();
                    //date = LocalDate.of(da.getYear(),da.getMonth(),da.getDay());
                    date = Instant.ofEpochMilli(da.getTime())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                } catch (java.lang.Exception ee) {
                    JOptionPane.showMessageDialog(null, "Invalid date", "ERROR", JOptionPane.ERROR_MESSAGE);
                    q=false;
                }
                if(q) {
                    item.setCompleted(false);
                    item.setDate(date);
                    item.setName(name);
                    item.setNotes(desc);
                    item.setTime(time);
                    dispose();
                }
            }
        });
    }

    public boolean action(Event evt, Object arg){
        if(arg.equals("Close")){
            dispose();
            return true;
        }
        return false;
    }

}