
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
import java.util.Date;


class AddDialog extends Dialog {
    String name;
    String desc;
    Date date;
    Time time;
    TextField nn = new TextField("Enter name");
    JButton aaa = new JButton("Close");
    TextArea ss = new TextArea("Event Description");
    TextField ti = new TextField("Enter time");



    public AddDialog(Frame parent, TodoItem item){
        super(parent, true);
        nn.setColumns(50);
        setLayout(new BorderLayout());
        JPanel inputs = new JPanel();
        JPanel i1 = new JPanel();
        i1.add(new JLabel("Name:"));
        i1.add(nn);
        inputs.add(i1, BorderLayout.NORTH);
        inputs.add(ss, BorderLayout.CENTER);

        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);

        inputs.add(datePicker, BorderLayout.SOUTH);

        JPanel dt = new JPanel();
        dt.add(aaa);
        dt.add(ti);
        ti.setColumns(5);

        add(dt,BorderLayout.SOUTH);
        add(inputs, BorderLayout.CENTER);
        setSize(500,500);

//        addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent windowEvent){
//                dispose();
//            }
//        })

        aaa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //date=LocalDate.parse(datePicker.getJFormattedTextField().getText());
                boolean q = true;
                name = nn.getText();
                desc = ss.getText();
                try {
                    time = Time.valueOf(ti.getText());
                } catch (java.lang.Exception ee) {
                    JOptionPane.showMessageDialog(null, "Enter a valid time format", "ERROR", JOptionPane.ERROR_MESSAGE);
                    q=false;
                }
                try {
                    date = (((Date) datePicker.getModel().getValue()));
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