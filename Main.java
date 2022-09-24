import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Main extends JFrame {
    Vector<TodoItem> tdl = new Vector<>();

    int i = 0;
    JTextField text1, text2, text3, text4;
    JTextArea dispinfo = new JTextArea();
    TodoItem addible = new TodoItem();

    // JTable Header
    public static final String[] columns = {
            "Name", "Date", "Completed",
    };
    // Create the table model
    private DefaultTableModel model = new DefaultTableModel(columns, 0){
        @Override
        public Class getColumnClass(int columnIndex) {
            if(columnIndex==2)
                return Boolean.class;
            else
                return String.class;
        }
    };
    // Create the JTable
    private JTable table = new JTable(model);
    // Create the main panel
    private JPanel mainPanel = new JPanel(new BorderLayout());
    int selectedSorter;
    String selecteditem;
    public Main()
    {
        super("Тестовое окно");
        JPanel listPanel = new JPanel();
        DefaultListModel<String> list = new DefaultListModel<>();

        list.addElement("All");
        list.addElement("Uncompleted");
        list.addElement("Completed");
        list.addElement("Today");
        JList cat = new JList<>(list);

        listPanel.add(cat);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton addButton = new JButton("Add");
        JButton clearButton = new JButton("Complete");
        JButton updateButton = new JButton("Upd all");
        JButton delete = new JButton("Delete");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(delete);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);

        // This code is called when the Add button is clicked.
//        addButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //Add form data
//                model.addRow(
//                        new Object[]{
//                                text1.getText(),
//                                text2.getText(),
//                                text3.getText()
//                        }
//                );
//            }
//        });

        // This code is called when the Clear button is clicked.
//        clearButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //Clear the form
//                text1.setText("");
//                text2.setText("");
//                text3.setText("");
//            }
//        });

        //Create the JTextFields panel
//        JPanel textPanel = new JPanel(new BorderLayout());
//        text1 = new JTextField();
//        text2 = new JTextField();
//        text3 = new JTextField();
//        //Add JTextFields to the panel
//        textPanel.add(text1, BorderLayout.NORTH);
//        textPanel.add(text2, BorderLayout.CENTER);
//        textPanel.add(text3, BorderLayout.SOUTH);

        //Add panels and table to the main panel

        dispinfo.setEditable(false);
        dispinfo.setColumns(40);
        dispinfo.setLineWrap(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addible.setName(String.valueOf(Integer.MIN_VALUE));
                AddDialog a = new AddDialog(null, addible);
                a.setVisible(true);
                if( ! addible.getName().equals(String.valueOf(Integer.MIN_VALUE))){
                    tdl.add(new TodoItem(addible));

                    model.addRow(new Object[]{
                            addible.getName(),
                            addible.getDate().toString(),
                            addible.getCompleted()
                    });
                    //TODO: add database push
                    addible.setName(String.valueOf(Integer.MIN_VALUE));
                }
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int del = JOptionPane.showConfirmDialog(null,"Do you really want to delete this reminder?","Deletion confirmation",JOptionPane.YES_NO_OPTION);
                if(del == 0) {
                    selecteditem = table.getValueAt(table.getSelectedRow(), 0).toString();
                    LocalDate ld = LocalDate.parse(table.getValueAt(table.getSelectedRow(), 1).toString());
                    // find
                    int sel = getItemPosByName(selecteditem, ld);
                    // System.out.println(sel);
                    System.out.println(sel);
                    if (sel != -1) {
                        dispinfo.setText(" ");
                        tdl.remove(sel);
                        //table.remove(table.getSelectedRow());
                        ((DefaultTableModel)table.getModel()).removeRow(sel);
                    }
                    //TODO: add deletion from db
                    //System.out.println(tdl.get(sel).toString());
                }
            }
        });
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row
                //TODO: if completed changed - push to db and ubpare table
                try {
                    selecteditem = table.getValueAt(table.getSelectedRow(), 0).toString();
                    LocalDate ld = LocalDate.parse(table.getValueAt(table.getSelectedRow(), 1).toString());
                    // find
                    int sel = getItemPosByName(selecteditem, ld);
                    System.out.println(sel);
                    if (sel != -1)
                        dispinfo.setText(tdl.get(sel).toString());
                    //System.out.println(tdl.get(sel).toString());
                }catch (java.lang.Exception e){
                    System.out.println(e);
                    dispinfo.setText(" ");
                }
            }
        });

//        table.getModel().addTableModelListener(new TableModelListener() {
//            @Override
//            public void tableChanged(TableModelEvent e) {
//                int row = e.getFirstRow();
//                int column = e.getColumn();
//                TableModel model = (TableModel)e.getSource();
//                String columnName = model.getColumnName(column);
//                Object data = model.getValueAt(row, column);
//                if(column == 2){
//                    //TODO:update db completed state
//                    System.out.println(data.getClass());
//                }
//
//            }
//        });


        //mainPanel.add(textPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(table), BorderLayout.WEST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(listPanel,BorderLayout.EAST);
        mainPanel.add(dispinfo, BorderLayout.CENTER);
    }
    //Get the main panel
    public JComponent getComponent() {
        return mainPanel;
    }
    // start the application in thread-safe
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame("Add automatically to JTable");
                f.getContentPane()
                        .add(new Main().getComponent());
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setSize(1100,500);
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }
        });
    }

//    public static void main(String[] args) {
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                JFrame.setDefaultLookAndFeelDecorated(true);
//                new Main();
//            }
//        });
    public int getItemPosByName(String name, LocalDate dd){
        for(int i=0;i<tdl.size();i++){
           // System.out.println(tdl.get(i).getName()+ name + tdl.get(i).getDate() + dd);
            if(tdl.get(i).getName().equals(name) && tdl.get(i).getDate().toString().equals(dd.toString())){
                return i;
            }
        }
        return -1;
    }
}

