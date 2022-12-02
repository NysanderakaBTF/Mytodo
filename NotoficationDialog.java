import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Vector;

public class NotoficationDialog extends Dialog {
    Vector<TodoItem> tdl = new Vector<>();

    int i = 0;
    JLabel msg = new JLabel("Tasks for today");
    JButton cans = new JButton("OK");

    // JTable Header
    public static final String[] columns = {
            "Name", "Date","Time", "Completed"
    };
    // Create the table model
    private DefaultTableModel model = new DefaultTableModel(columns, 0){
        @Override
        public Class getColumnClass(int columnIndex) {
            if(columnIndex==3)
                return Boolean.class;
            else
                return String.class;
        }
    };
    // Create the JTable
    private JTable table = new JTable(model);
    // Create the main panel
    private JPanel mainPanel = new JPanel(new BorderLayout());
    DBcontroller bcontroller = new DBcontroller();
    public NotoficationDialog(Frame parent){
        super(parent, true);
        setSize(500,500);
        bcontroller.ExtractTotaysTasks(tdl);
        mainPanel.add(msg, BorderLayout.NORTH);

        for (TodoItem i: tdl ) {
            model.addRow(new Object[]{
                    i.getName(),
                    i.getDate().toString(),
                    i.getTime().toString(),
                    i.getCompleted()
            });
        }
        table.setEnabled(false);
        mainPanel.add(table,BorderLayout.CENTER);
        mainPanel.add(cans, BorderLayout.SOUTH);
        cans.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(mainPanel);
        setVisible(true);
    }
}
