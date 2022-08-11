package ui;

import model.Event;
import model.EventLog;
import model.Item;
import model.TypeList;
import persistence.JsonReader;
import persistence.JsonWriter;
import sun.lwawt.macosx.CSystemTray;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class represent the main labInventory APP, it can save and load to/from previous data file
 * The ui will show brief lab inventory information and have options to add new items, or delete an
 * item from the list
 */
public class LabInventoryAppUI extends JFrame implements ActionListener {

    private static final String JSON_STORE = "./data/labInventory.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JPanel contentPane;
    private JPanel topPanel;
    private JPanel mdlPanel;
    private JPanel btnPanel;
    private JTable table;
    private JTextField textTypeName;
    private JTextField textItemName;
    private JTextField textAmount;
    private JTextField textLocation;
    private JTextField textVendor;
    private JTextField textUpdated;
    private JTextField textCutoff;
    private JTextField textNotes;
    private JButton addButton;
    private JButton deleteButton;
    private JButton saveButton;
    private Object[] data;
    private DefaultTableModel model;
    private Item itemA;
    private Item itemB;
    private TypeList typeList;
    private JMenuItem loadItem;
    private int selectedRow;

    // EFFECTS: Constructs a frame which can presenting item information, user input and functional buttons
    public LabInventoryAppUI() {
        super("Lab Inventory");
        setSize(800, 1200);

        addMenuBar();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        contentPane = new JPanel();
        setContentPane(contentPane);
        setLayout(new BorderLayout());
        topPanel = new JPanel();
        mdlPanel = new JPanel();
        btnPanel = new JPanel();

        contentPane.add(topPanel, "North");
        contentPane.add(mdlPanel);
        contentPane.add(btnPanel, "South");
        createTable();
        createEditors();
        createButtons();
        typeList = new TypeList();

        closeWindow();

        pack();
        revalidate();
        setVisible(true);
    }

    // EFFECTS: print to console all the events that have been logged since started before quiting the application
    public void closeWindow() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                EventLog events = EventLog.getInstance();
                for (Event event : events) {
                    System.out.println(event.toString() + "\n\n");
                }
                System.exit(0);
            }
        });
    }


    // EFFECTS: add a menu bar to the main frame
    public void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        loadItem = new JMenuItem("load");

        loadItem.addActionListener(this);
        fileMenu.add(loadItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: Menu item function while clicking the menu
    //          (reads information from saved file and load them to the table on frame)
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            typeList = jsonReader.read();
            for (model.Type a : typeList.getTypes()) {
                data[0] = a.getTypeName();
                for (Item b : a.getItemsForType(a.getTypeName())) {
                    data[1] = b.getItemName();
                    data[2] = b.getAmount();
                    data[3] = b.getLocation();
                    data[4] = b.getVendor();
                    data[5] = b.getUpdated();
                    data[6] = b.getCutOff();
                    data[7] = b.isToOrder();
                    data[8] = b.getNotes();
                    model.addRow(data);
                }
            }
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(null,
                    "Unable to read from file: " + JSON_STORE, "Error", 0);
        }
    }

    // EFFECTS: Creates a table on the frame
    public void createTable() {
        topPanel.setLayout(new GridLayout(1, 0));
        String[] columnNames = {"Type Name", "Item Name", "Amount", "Location",
                "Vendor", "Updated", "CutOff", "ToOrder", "Notes"};
        data = new Object[9];

        table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(800, 400));
        table.setFillsViewportHeight(true);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        table.setModel(model);

        JScrollPane scrollPane = new JScrollPane(table);

        topPanel.add(scrollPane);

    }

    // EFFECTS: Creates labels and fields for user to input item information
    public void createEditors() {
        GridLayout gird = new GridLayout(4, 4, 5, 5);
        mdlPanel.setLayout(gird);

        String[] labelStrings = {"Type Name: ", "Item Name: ", "Amount: ", "Location: ", "Vendor: ", "Updated: ",
                "Cutoff: ", "Notes: "};

        JLabel[] labels = new JLabel[labelStrings.length];
        JComponent[] fields = new JComponent[labelStrings.length];
        int fieldNum = 0;

        setJTextField();
        fields[fieldNum++] = textTypeName;
        fields[fieldNum++] = textItemName;
        fields[fieldNum++] = textAmount;
        fields[fieldNum++] = textLocation;
        fields[fieldNum++] = textVendor;
        fields[fieldNum++] = textUpdated;
        fields[fieldNum++] = textCutoff;
        fields[fieldNum++] = textNotes;

        for (int i = 0; i < labelStrings.length; i++) {
            labels[i] = new JLabel(labelStrings[i],
                    JLabel.TRAILING);
            labels[i].setLabelFor(fields[i]);
            mdlPanel.add(labels[i]);
            mdlPanel.add(fields[i]);
        }
    }

    // EFFECTS: Creates fields for inputting item information
    public void setJTextField() {
        textTypeName = new JTextField(20);
        textItemName = new JTextField(20);
        textAmount = new JTextField(20);
        textLocation = new JTextField(20);
        textVendor = new JTextField(20);
        textUpdated = new JTextField(20);
        textCutoff = new JTextField(20);
        textNotes = new JTextField(20);
    }

    // EFFECTS: Add buttons
    public void createButtons() {
        btnPanel.setLayout(new FlowLayout());
        addButton = new JButton("ADD");
        addButton.setFont(new Font("MV Boli", Font.BOLD, 15));

        deleteButton = new JButton("DELETE");
        deleteButton.setFont(new Font("MV Boli", Font.BOLD, 15));

        saveButton = new JButton("SAVE");
        saveButton.setFont(new Font("MV Boli", Font.BOLD, 15));

        btnPanel.add(addButton);
        btnPanel.add(deleteButton);
        btnPanel.add(saveButton);

        actionForAddButton();
        actionForDeleteButton();
        actionForSaveButton();
    }

    // REQUIRES: input fields related to item information can not be empty
    // MODIFIES: this
    // EFFECTS: Function while clicking the add button
    //         (add the item information input by user to the table and to database)
    public void actionForAddButton() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typeName = textTypeName.getText();
                String itemName = textItemName.getText();
                int initialAmount = Integer.parseInt(textAmount.getText());
                String location = textLocation.getText();
                String vendor = textVendor.getText();
                String updated = textUpdated.getText();
                int cutoff = Integer.parseInt(textCutoff.getText());
                String note = textNotes.getText();
                itemA = new Item(itemName, initialAmount, location, vendor, updated, cutoff);
                itemA.addNote(note);
                typeList.addType(typeName);
                addItem();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Adds item information into the table and list; if the item has been added, it will notify users
    public void addItem() {
        String thisTypeName = textTypeName.getText();
        for (model.Type i : typeList.getTypes()) {
            if (i.getTypeName().equals(thisTypeName)) {
                if (i.addItemToType(thisTypeName, itemA)) {
                    addDataToTable();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "This item has been added", "Error", 0);
                }
            }
        }
    }

    // EFFECTS: Adds data to table on frame
    public void addDataToTable() {
        data[0] = textTypeName.getText();
        data[1] = itemA.getItemName();
        data[2] = itemA.getAmount();
        data[3] = itemA.getLocation();
        data[4] = itemA.getVendor();
        data[5] = itemA.getUpdated();
        data[6] = itemA.getCutOff();
        data[7] = itemA.isToOrder();
        data[8] = itemA.getNotes();
        model.addRow(data);
    }

    // REQUIRES: one row from table has to be selected
    // MODIFIES: this
    // EFFECTS: Function while clicking the delete button
    //          (delete the selected item from the table and from the database)
    public void actionForDeleteButton() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedRow = table.getSelectedRow();
                int i = selectedRow;
                if (i >= 0) {
                    String itemName = (String) table.getValueAt(i, 1);
                    int initialAmount = (int) model.getValueAt(i, 2);
                    String location = (String) table.getValueAt(i, 3);
                    String vendor = (String) table.getValueAt(i, 4);
                    String updated = (String) table.getValueAt(i, 5);
                    int cutoff = (int) table.getValueAt(i, 6);
                    ArrayList<String> notes = (ArrayList<String>) table.getValueAt(i, 8);
                    itemB = new Item(itemName, initialAmount, location, vendor, updated, cutoff);
                    itemB.getNotes().addAll(notes);
                    removeItem();
                    model.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Selection Error", "Error", 0);
                }
            }
        });
    }

    // EFFECTS: Removes selected item from typeList
    public void removeItem() {
        int tableRow = selectedRow;
        String typeName = (String) table.getValueAt(tableRow, 0);
        for (model.Type j : typeList.getTypes()) {
            if (j.getTypeName().equals(typeName)) {
                j.removeItemFromType(typeName, itemB);
            }
        }
    }

    // EFFECTS: Function while clicking the save button
    //          (save information to json file)
    public void actionForSaveButton() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jsonWriter.open();
                    jsonWriter.write(typeList);
                    jsonWriter.close();
                } catch (FileNotFoundException fileNotFoundException) {
                    JOptionPane.showMessageDialog(null,
                            "Unable to read from file: " + JSON_STORE, "Error", 0);
                }
            }
        });
    }


}
