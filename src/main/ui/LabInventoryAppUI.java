package ui;

import model.Item;
import model.TypeList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class LabInventoryAppUI extends JFrame implements ActionListener {

    private static final String JSON_STORE = "./data/labInventory.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JPanel contentPane;
    private JPanel topPanel;
    private JPanel mdlPanel;
    private JPanel btnPanel;
    private JTable table;
    private JLabel hint;
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
    private JButton loadButton;
    private Object[] data;
    private DefaultTableModel model;
    private Item itemA;
    private Item itemB;
    private TypeList typeList;
    private JMenuItem loadItem;

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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        revalidate();
        setVisible(true);
    }

    public void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        loadItem = new JMenuItem("load");

        loadItem.addActionListener(this);
        fileMenu.add(loadItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            typeList = jsonReader.read();
            for (model.Type a : typeList.getTypes()) {
                data[0] = a.getTypeName();
                for (Item b: a.getItemsForType(a.getTypeName())) {
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

    public void addItem() {
        String thisTypeName = textTypeName.getText();
        for (model.Type i : typeList.getTypes()) {
            if (i.getTypeName().equals(thisTypeName)) {
                if (i.addItemToType(thisTypeName, itemA)) {
                    i.addItemToType(i.getTypeName(), itemA);
                    addDataToTable();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "This item has been added", "Error", 0);
                }
            }
        }
    }

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


    public void actionForDeleteButton() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = table.getSelectedRow();
                if (i >= 0) {
                    model.removeRow(i); // Why there are exception
                    String itemName = (String) model.getValueAt(i, 1);
                    int initialAmount = (int) model.getValueAt(i, 2);
                    String location = (String) model.getValueAt(i, 3);
                    String vendor = (String) model.getValueAt(i, 4);
                    String updated = (String) model.getValueAt(i, 5);
                    int cutoff = (int) model.getValueAt(i, 6);
                    ArrayList<String> notes = (ArrayList<String>) model.getValueAt(i, 8);
                    itemB = new Item(itemName, initialAmount, location, vendor, updated, cutoff);
                    itemB.getNotes().addAll(notes);
                    removeItem();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Selection Error", "Error", 0);
                }
            }
        });
    }

    // remove selected item from typeList
    public void removeItem() {
        int i = table.getSelectedRow();
        String typeName = (String) model.getValueAt(i, 0);
        for (model.Type j : typeList.getTypes()) {
            if (j.getTypeName().equals(typeName)) {
                j.removeItemFromType(typeName, itemB);
            }
        }
    }

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
