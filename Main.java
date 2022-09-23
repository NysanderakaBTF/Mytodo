import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class Main extends JFrame {
    ArrayList<TodoItem> tdl = new ArrayList<>();

    int i = 0;
    JTextField text1, text2, text3, text4;
    JTextArea dispinfo = new JTextArea();
    TodoItem addible = new TodoItem();

    // JTable Header
    public static final String[] columns = {
            "Name", "Date", "Completed",
    };
    // Create the table model
    private DefaultTableModel model = new DefaultTableModel(columns, 0);
    // Create the JTable
    private JTable table = new JTable(model);
    // Create the main panel
    private JPanel mainPanel = new JPanel(new BorderLayout());
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
        JPanel textPanel = new JPanel(new BorderLayout());
        text1 = new JTextField();
        text2 = new JTextField();
        text3 = new JTextField();
        //Add JTextFields to the panel
        textPanel.add(text1, BorderLayout.NORTH);
        textPanel.add(text2, BorderLayout.CENTER);
        textPanel.add(text3, BorderLayout.SOUTH);

        //Add panels and table to the main panel

        dispinfo.setEditable(false);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addible.setName(String.valueOf(Integer.MIN_VALUE));
                AddDialog a = new AddDialog(null, addible);
                a.setVisible(true);
                if(addible.getName()!=String.valueOf(Integer.MIN_VALUE)){
                    tdl.add(addible);

                    model.addRow(new Object[]{
                            addible.getName(),
                            addible.getTime().toString(),
                            addible.getCompleted()
                    });
                    //TODO: add database push
                    addible.setName(String.valueOf(Integer.MIN_VALUE));
                }
            }
        });


        mainPanel.add(textPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(table), BorderLayout.WEST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(listPanel,BorderLayout.CENTER);
        mainPanel.add(dispinfo, BorderLayout.EAST);
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

