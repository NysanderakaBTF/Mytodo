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
            "Name", "Date", "Completed","ID"
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
    JList cat;
    DBcontroller bcontroller = new DBcontroller();
    int displayMode =0;
    public int getItemPosByID(int id){
        for(int i=0;i<tdl.size();i++){
            System.out.println(tdl.get(i).getId()+"                    "+id);
            if(tdl.get(i).getId()==id){
                return i;
            }
        }
        return -1;
    }
    public void updateTableOnSelectionMode(){
        selectedSorter = cat.getSelectedIndex();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        if(selectedSorter==0){
            tdl.forEach(o -> model.addRow(new Object[]{o.getName(), o.getDate().toString(), o.getCompleted(), o.getId()}));
        } else if (selectedSorter == 1) {
            tdl.stream().filter(item-> (item.getCompleted()==false)).forEach(item->model.addRow(new Object[]{item.getName(), item.getDate().toString(), item.getCompleted(), item.getId()}));
        }else if (selectedSorter == 2) {
            tdl.stream().filter(item-> (item.getCompleted()==true)).forEach(item->model.addRow(new Object[]{item.getName(), item.getDate().toString(), item.getCompleted(), item.getId()}));
        }else if (selectedSorter == 3) {
            tdl.stream().filter(item-> (item.getDate().equals(LocalDate.now()))).forEach(item->model.addRow(new Object[]{item.getName(), item.getDate().toString(), item.getCompleted(), item.getId()}));
        }
    }
    public Main()
    {
        super("Тестовое окно");
        JPanel listPanel = new JPanel();
        DefaultListModel<String> list = new DefaultListModel<>();

        list.addElement("All");
        list.addElement("Uncompleted");
        list.addElement("Completed");
        list.addElement("Today");
        cat = new JList<>(list);

        listPanel.add(cat);
        cat.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cat.setSelectedIndex(0);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton addButton = new JButton("Add");
        JButton clearButton = new JButton("Complete");
        JButton updateButton = new JButton("Edit");
        JButton delete = new JButton("Delete");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(addButton);
        //buttonPanel.add(clearButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(delete);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);


        //Add panels and table to the main panel

        dispinfo.setEditable(false);
        dispinfo.setColumns(40);
        dispinfo.setLineWrap(true);

        //db extration
        bcontroller.ExtractData(tdl);
        cat.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
//                selectedSorter = cat.getSelectedIndex();
//                model.getDataVector().removeAllElements();
//                model.fireTableDataChanged();
//                if(selectedSorter==0){
//                    tdl.forEach(o -> model.addRow(new Object[]{o.getName(), o.getDate().toString(), o.getCompleted()}));
//                } else if (selectedSorter == 1) {
//                    tdl.stream().filter(item-> (item.getCompleted()==false)).forEach(item->model.addRow(new Object[]{item.getName(), item.getDate().toString(), item.getCompleted()}));
//                }else if (selectedSorter == 2) {
//                    tdl.stream().filter(item-> (item.getCompleted()==true)).forEach(item->model.addRow(new Object[]{item.getName(), item.getDate().toString(), item.getCompleted()}));
//                }else if (selectedSorter == 3) {
//                    tdl.stream().filter(item-> (item.getDate().equals(LocalDate.now()))).forEach(item->model.addRow(new Object[]{item.getName(), item.getDate().toString(), item.getCompleted()}));
//                }
                updateTableOnSelectionMode();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addible.setName(String.valueOf(Integer.MIN_VALUE));
                AddDialog a = new AddDialog(null, addible);
                a.setVisible(true);
                if( ! addible.getName().equals(String.valueOf(Integer.MIN_VALUE))){
                    tdl.add(new TodoItem(addible));
                    bcontroller.InsertData(addible);
                    model.addRow(new Object[]{
                            addible.getName(),
                            addible.getDate().toString(),
                            addible.getCompleted(),
                            addible.getId()
                    });

                    //TODO: add database push+++++
                    addible.setName(String.valueOf(Integer.MIN_VALUE));
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selr = table.getSelectedRow();
                int pos = getItemPosByID((Integer) table.getValueAt(selr,3));
                EditDialog a = new EditDialog(null, tdl.get(pos));
                a.setVisible(true);
                //TODO:push update to db+++++++
                bcontroller.UpdateData(tdl.get(pos));
                updateTableOnSelectionMode();
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int del = JOptionPane.showConfirmDialog(Main.this,"Do you really want to delete this reminder?","Deletion confirmation",JOptionPane.YES_NO_OPTION);
                if(del == 0) {
                    selecteditem = table.getValueAt(table.getSelectedRow(), 0).toString();
                    LocalDate ld = LocalDate.parse(table.getValueAt(table.getSelectedRow(), 1).toString());
                    // find
                    int sel = getItemPosByID((Integer) table.getValueAt(table.getSelectedRow(), 3));
                    // System.out.println(sel);
                    System.out.println(sel);
                    if (sel != -1) {
                        dispinfo.setText(" ");
                        bcontroller.DeleteData(tdl.get(sel));
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
                System.out.println(event);
                try {
//                    selecteditem = table.getValueAt(table.getSelectedRow(), 0).toString();
//                    LocalDate ld = LocalDate.parse(table.getValueAt(table.getSelectedRow(), 1).toString());
                    // find
                    int sel = getItemPosByID((Integer) table.getValueAt(table.getSelectedRow(), 3));
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

        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                TableModel model = (TableModel)e.getSource();
                String columnName = model.getColumnName(column);
                boolean data= true;
                try {
                     data = (boolean) model.getValueAt(row, column);
                }
                catch (java.lang.Exception aa){
                    System.out.println(aa);
                }
                System.out.println(e.getColumn());
                String sell;
                try {
                    sell = table.getValueAt(table.getSelectedRow(), 0).toString();
                    LocalDate ld = LocalDate.parse(table.getValueAt(table.getSelectedRow(), 1).toString());
                    if(column==2){
                        int pos = getItemPosByID((Integer) table.getValueAt(table.getSelectedRow(), 3));
                       // int pos = getItemPosByID(sell,ld);
                        tdl.get(pos).setCompleted(!tdl.get(pos).getCompleted());

                        bcontroller.UpdateData(tdl.get(pos));

                        dispinfo.setText(tdl.get(pos).toString());
                        updateTableOnSelectionMode();
                    }
                }catch (java.lang.Exception aaa){
                    System.out.println(aaa);
                }

                    //TODO:update db completed state+++++++++


            }
        });


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

}


