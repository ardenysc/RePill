package ui;

import model.Event;
import model.EventLog;
import model.Pill;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import javax.swing.*;

// Represents functioning table GUI that displays pill collection
public class CollectionTable {
    protected PillTableModel tableModel = new PillTableModel();
    protected JTable table = new JTable();
    protected JFrame frame;
    protected JTextField name;
    protected JTextField count;
    protected JTextField dosage;
    protected JTextArea textArea;

    // MODIFIES: this
    // EFFECTS: creates the GUI
    public void createUI() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());

        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JPanel buttonPanelSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanelSouth.add(addButton);
        buttonPanelSouth.add(deleteButton);

        table.setModel(tableModel);
        table.getTableHeader().setReorderingAllowed(false);

        addButtonSetup(addButton);
        deleteButtonSetup(deleteButton);

        frame.add(table.getTableHeader(), BorderLayout.NORTH);
        frame.add(table, BorderLayout.CENTER);
        frame.add(buttonPanelSouth, BorderLayout.SOUTH);

        MenuBar menuBar = new MenuBar();
        frame.setJMenuBar(menuBar.createMenuBar());

        frame.setTitle(tableModel.getCollectionName());
        frame.setPreferredSize(new Dimension(500, 400));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExitBehavior();
        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets behavior when window closes
    //          prints out all the logged events
    public void setExitBehavior() {
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Iterator<Event> eventLog = EventLog.getInstance().iterator();
                while (eventLog.hasNext()) {
                    System.out.println(eventLog.next().toString());
                }
                System.exit(0);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: sets up add button to function
    public void addButtonSetup(JButton addButton) {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel();
                createAddPillWindow(panel);
                int result = JOptionPane.showConfirmDialog(frame, panel, "New Pill",
                        JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String pillName = name.getText();
                    int pillCount = Integer.parseInt(count.getText());
                    int pillDosage = Integer.parseInt(dosage.getText());
                    String pillDescription = textArea.getText();
                    Pill newPill = new Pill(pillName, pillCount, pillDosage, pillDescription);
                    tableModel.addRow(newPill);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: sets up delete button to function
    public void deleteButtonSetup(JButton deleteButton) {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.deleteRow();
            }
        });
    }

    // EFFECTS: creates popup window to add pill
    public void createAddPillWindow(JPanel panel) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        decimalFormat.setGroupingUsed(false);
        name = new JTextField(5);
        count = new JFormattedTextField(decimalFormat);
        dosage = new JFormattedTextField(decimalFormat);
        count.setColumns(3);
        dosage.setColumns(2);
        textArea = new JTextArea(4, 10);

        panel.add(new JLabel("Name:"));
        panel.add(name);

        panel.add(new JLabel("Count:"));
        panel.add(count);

        panel.add(new JLabel("Dosage:"));
        panel.add(dosage);

        panel.add(Box.createHorizontalStrut(15));
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(textArea));
    }
}
