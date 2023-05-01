package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Represents a menu bar with functioning menu items
public class MenuBar extends CollectionTable {
    private JMenuBar menuBar;
    private JMenu menu;
    private static final String ICON_STORE = "./data/DukeCheers.png";
    private ImageIcon imageIcon;

    // EFFECTS: create the menu bar and add one menu
    public MenuBar() {
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menuBar.add(menu);
        // image icon setup taken from https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon
        imageIcon = new ImageIcon(new ImageIcon(ICON_STORE).getImage().getScaledInstance(108, 92, Image.SCALE_SMOOTH));
    }

    // MODIFIES: this
    // EFFECTS: Add save and load options to menu bar
    public JMenuBar createMenuBar() {
        addSaveOption();
        addLoadOption();
        return menuBar;
    }

    // MODIFIES: this
    // EFFECTS: Add functioning save option to menu bar
    public void addSaveOption() {
        JMenuItem menuItemSave = new JMenuItem("Save Data");
        menuItemSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.saveCollection();
                JOptionPane.showConfirmDialog(frame, "saved!", "success",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, imageIcon);
            }
        });
        menu.add(menuItemSave);
    }

    // MODIFIES: this
    // EFFECTS: Add functioning load option to menu bar
    public void addLoadOption() {
        JMenuItem menuItemLoad = new JMenuItem("Load Data");
        menuItemLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.loadCollecitonToTable();
                JOptionPane.showConfirmDialog(frame, "loaded!", "success",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, imageIcon);
            }
        });
        menu.add(menuItemLoad);
    }
}
