package org.example;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Panel extends JPanel {
    Panel(JFrame frame) {

        setLayout(new BorderLayout());

        //------------ Menu -----------------

        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new BorderLayout());
        //Creating the Bar Menu and adding the edit, view, and File menu
        barraMenu = new JMenuBar();
        menuFile = new JMenu("File");
        menuEdit = new JMenu("Edit");
        menuView = new JMenu("View");
        menuConf = new JMenu("Configuration");

        //Creating the text editor to find words
        findText.setVisible(false);
        findButton.setVisible(false);
        panelMenu.add(findButton, BorderLayout.EAST);
        panelMenu.add(findText, BorderLayout.SOUTH);

        //-----------------------Adding each menu to the bar Menu----------------
        barraMenu.add(menuFile);
        barraMenu.add(menuEdit);
        menuView.add(menuConf);
        barraMenu.add(menuView);

        //-----------------------Adding the bar Menu to the panel where the menu goes
        panelMenu.add(barraMenu, BorderLayout.NORTH);

        //--------------------Creating the list of the items of each menu-----------
        String[][][] itemsList = getList();

        //--------------------Assigning the items to each menu----------------------
        int i = 0;
        for (String[][] strings : itemsList) {
            for (String[] string : strings) {
                for (int k = 0; k < 1; k++) {
                    createItemsMenu(string[k], barraMenu.getMenu(i), string[k+1]);
                }
            }
        i++;
        }

        String[][] itemsMenuConf = {{"Normal", "normal"}, {"Dark", "dark"}};
        for(String[] s : itemsMenuConf)
            createItemsMenu(s[0], menuConf, s[1]);

        //Disable the MenuItems that will cause error with no tab open
        Tools.disableMenuItems(listItemToAct);
        //-----------------------------------

        //------- Text Area -----------------
        tabbedPane = new JTabbedPane();

        textAreaList = new ArrayList<>();
        fileList = new ArrayList<>();
        scrollList = new ArrayList<>();
        undoManagers = new ArrayList<>();

        //-----------------------------------
        //------- Tool Area -----------------
        toolBar = new JToolBar(JToolBar.VERTICAL);
        File file = new File("C:\\Users\\maria\\Documents\\Proyectos\\TextEditor\\src\\main\\resources\\x.png");
        try {
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Tools.addButton(url, toolBar, "Close tab").addActionListener(actionEvent -> {
            int selection = tabbedPane.getSelectedIndex();
            //if button is clicked and there are tabs open, close everything
            if(selection != -1) {
                scrollList.get(tabbedPane.getSelectedIndex()).setRowHeader(null);
                tabbedPane.remove(selection);
                textAreaList.remove(selection);
                scrollList.remove(selection);
                undoManagers.remove(selection);
                fileList.remove(selection);
                Tools.disableMenuItems(listItemToAct);

                panelCount--;

                if(tabbedPane.getSelectedIndex() == -1) {
                    doesPanelExist = false;
                }
            }
        });

        File file2 = new File("C:\\Users\\maria\\Documents\\Proyectos\\TextEditor\\src\\main\\resources\\+.png");
        try {
            url2 = file2.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Tools.addButton(url2, toolBar, "Open tab").addActionListener(actionEvent -> createPanel());
        //-----------------------------------
        //--------------------------------------Extra Panel------------------------
        //Adding a pin
        extraPanel = new JPanel();
        extraPanel.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        labelLeft = new JLabel();
        File filePinNoSelected = new File("C:\\Users\\maria\\Documents\\Proyectos\\TextEditor\\src\\main\\resources\\pinNoSelected.png");
        File filePinSelected = new File("C:\\Users\\maria\\Documents\\Proyectos\\TextEditor\\src\\main\\resources\\pinSelected.png");
        try {
            urlPinNoSelected = filePinNoSelected.toURI().toURL();
            urlPinSelected = filePinSelected.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        labelLeft.setIcon(new ImageIcon(new ImageIcon(urlPinNoSelected).getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH)));
        labelLeft.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (!pinChange){
                    labelLeft.setIcon(new ImageIcon(new ImageIcon(urlPinSelected).getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH)));
                    frame.setAlwaysOnTop(true);
                    pinChange = true;
                }
                else {
                    labelLeft.setIcon(new ImageIcon(new ImageIcon(urlPinNoSelected).getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH)));
                    frame.setAlwaysOnTop(false);
                    pinChange = false;
                }
            }
        });
        leftPanel.add(labelLeft);

        //Adding a Slider
        JPanel centerPanel = new JPanel();
        slider = new JSlider(10,60,14);
        //Splitting the slider with lines
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(2);
        slider.setPaintTicks(true);
        slider.addChangeListener(changeEvent -> Tools.textSize(slider.getValue(), panelCount, textAreaList));


        centerPanel.add(slider);

        extraPanel.add(leftPanel, BorderLayout.WEST);
        extraPanel.add(centerPanel, BorderLayout.CENTER);
        //-------------------------------------------------------------------------
        //---------------------------------Pop Menu-------------------------------
        popupMenu = new JPopupMenu();
        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem paste = new JMenuItem("Paste");
        JMenuItem search = new JMenuItem("Search in Google");

        copy.addActionListener(new DefaultEditorKit.CopyAction());
        paste.addActionListener(new DefaultEditorKit.PasteAction());
        cut.addActionListener(new DefaultEditorKit.CutAction());
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textSelected = textAreaList.get(tabbedPane.getSelectedIndex()).getSelectedText();
                Tools.GoogleFind(textSelected);
            }
        });

        popupMenu.add(cut);
        popupMenu.add(copy);
        popupMenu.add(paste);
        popupMenu.add(search);
        //-------------------------------------------------------------------------
        this.add(panelMenu, BorderLayout.NORTH);
        this.add(tabbedPane, BorderLayout.CENTER);
        this.add(toolBar, BorderLayout.WEST);
        this.add(extraPanel, BorderLayout.SOUTH);

    }

    private static String[][][] getList() {
        //--------------------------Items for the bar menu---------------------------
        String[][] itemsMenuFile = {{"New", "new"}, {"Open", "open"}, {"Save", "save"}, {"Save as", "saveAs"}, {"Find", "find"}};
        String[][] itemsMenuEdit = {{"Undo", "undo"}, {"Do", "do"}, {"Copy", "copy"}, {"Paste", "paste"}, {"Cut", "cut"}, {"Search in Google", "search"}, {"Select All", "selectAll"}};
        String[][] itemsMenuView = {{"Numbers View", "view"}};

        return new String[][][]{itemsMenuFile, itemsMenuEdit, itemsMenuView};
    }

    private final JTabbedPane tabbedPane;
    private final JMenuBar barraMenu;
    private final JMenu menuFile, menuEdit, menuView, menuConf;
    private JMenuItem item;
    private boolean pinChange = false;

    private int panelCount = 0; //Numbers of panels created
    private boolean doesPanelExist = false; // to know if a panel has been created

    private JPanel window;
    private JPanel extraPanel;
    private final ArrayList<JTextPane> textAreaList;
    private final ArrayList<File> fileList;
    private final ArrayList<JScrollPane> scrollList;
    private final ArrayList<UndoManager> undoManagers;
    private boolean numbersView = false;
    private String typeB = "white";
    private JToolBar toolBar;
    private URL url, url2, urlPinSelected, urlPinNoSelected;
    private final JLabel labelLeft;
    private final JSlider slider;
    private final JPopupMenu popupMenu;
    private JMenuItem listItemToAct[] = new JMenuItem[11];
    private int nroItemToAct = 0;
    private String textSelected;
    private JTextField findText = new JTextField(10);
    JButton findButton = new JButton("Find");

    public void createPanel() {
        Tools.activeMenuItems(listItemToAct);

        window = new JPanel();
        window.setLayout(new BorderLayout());
        fileList.add(new File(""));
        textAreaList.add(new JTextPane());
        scrollList.add(new JScrollPane(textAreaList.get(panelCount)));
        undoManagers.add(new UndoManager()); //to track any change on the text

        textAreaList.get(panelCount).getDocument().addUndoableEditListener(undoManagers.get(panelCount));

        textAreaList.get(panelCount).setComponentPopupMenu(popupMenu);

        window.add(scrollList.get(panelCount), BorderLayout.CENTER);
        tabbedPane.addTab("Title", window);

        Tools.viewNumbersScreen(numbersView, textAreaList.get(panelCount), scrollList.get(panelCount));

        tabbedPane.setSelectedIndex(panelCount);
        ++panelCount;
        Tools.aBanckground(panelCount, typeB, slider.getValue(), textAreaList);
        doesPanelExist = true;
    }

    public void createItemsMenu(String name, JMenu menuName, String action) {
            item = new JMenuItem(name);

        if(!(name.equals("New") ||  name.equals("Open") ||  name.equals("Dark") ||  name.equals("Normal"))) {
            listItemToAct[nroItemToAct] = item;
            nroItemToAct++;
        }
        menuName.add(item);

        if(name.equals("Open") || name.equals("Do") || name.equals("Configuration")) {
            menuName.addSeparator();
        }
        //If the action is equal to copy, cut or paste proceed to addActionListener with defaultEditorKit
        if(action.equals("copy") || action.equals("cut") || action.equals("paste")) {
            switch (action) {
                case "copy":
                    item.addActionListener(new DefaultEditorKit.CopyAction());
                    break;
                case "paste":
                    item.addActionListener(new DefaultEditorKit.PasteAction());
                    break;
                case "cut":
                    item.addActionListener(new DefaultEditorKit.CutAction());
                    break;
            }
        }
        //Else create a new action listener for the others actions
        else {
            item.addActionListener(actionEvent -> {
                switch (action) {
                    case "new":
                        createPanel();
                        break;
                    case "open":
                        createPanel();
                        JFileChooser chooser = new JFileChooser();
                        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                        int resultFile = chooser.showOpenDialog(textAreaList.get(tabbedPane.getSelectedIndex()));

                        if (resultFile == JFileChooser.APPROVE_OPTION) {
                            try {
                                boolean doesExistFile = false;
                                File openFile = chooser.getSelectedFile();
                                for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                                    if (fileList.get(i).getPath().equals(openFile.getPath())) {
                                        doesExistFile = true;
                                        //Open the tab where the file is already open
                                        tabbedPane.setSelectedIndex(i);

                                        //Eliminate the panels created because we do not need them
                                        eliminate();
                                        break;
                                    }
                                }

                                if (!doesExistFile) {
                                    fileList.set(tabbedPane.getSelectedIndex(), openFile);
                                    FileReader reader = new FileReader(fileList.get(tabbedPane.getSelectedIndex()).getPath());
                                    BufferedReader bufferedReader = new BufferedReader(reader);
                                    String line = "";

                                    String title = fileList.get(tabbedPane.getSelectedIndex()).getName();
                                    tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), title);

                                    while (line != null) {
                                        line = bufferedReader.readLine();
                                        if (line != null) {
                                            Tools.append(line + "\n", textAreaList.get(tabbedPane.getSelectedIndex()));
                                        }
                                    }
                                    Tools.aBanckground(panelCount, typeB, slider.getValue(), textAreaList);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            //Eliminate the panels created because we do not need them
                            eliminate();
                        }
                        break;
                    case "save":
                        if (fileList.get(tabbedPane.getSelectedIndex()).getPath().isEmpty()) {
                            saveAs();
                        } else {
                            save();
                        }
                        break;
                    case "saveAs":
                        saveAs();
                        break;
                    case "find":
                        find();
                        break;
                    case "undo":
                        if (undoManagers.get(tabbedPane.getSelectedIndex()).canUndo()) {
                            undoManagers.get(tabbedPane.getSelectedIndex()).undo();
                        }
                        break;
                    case "do":
                        if (undoManagers.get(tabbedPane.getSelectedIndex()).canRedo()) {
                            undoManagers.get(tabbedPane.getSelectedIndex()).redo();
                        }
                        break;
                    case "search":
                        textSelected = textAreaList.get(tabbedPane.getSelectedIndex()).getSelectedText();
                        Tools.GoogleFind(textSelected);
                        break;
                    case "selectAll":
                        textAreaList.get(tabbedPane.getSelectedIndex()).selectAll();
                        break;
                    case "view":
                        numbersView = !numbersView;
                        Tools.viewNumberSet(panelCount, numbersView, textAreaList, scrollList);
                        break;
                    case "normal":
                        typeB = "white";
                        if(tabbedPane.getTabCount() > 0) {
                            Tools.aBanckground(panelCount, typeB, slider.getValue(), textAreaList);
                        }
                        break;
                    case "dark":
                        typeB = "dark";
                        if(tabbedPane.getTabCount() > 0) {
                            Tools.aBanckground(panelCount, typeB, slider.getValue(), textAreaList);
                        }
                        break;
                }
            });
        }
    }

    public void eliminate() {
        fileList.remove(tabbedPane.getTabCount() - 1 );
        textAreaList.remove(tabbedPane.getTabCount() - 1 );
        scrollList.remove(tabbedPane.getTabCount() - 1 );
        tabbedPane.remove(tabbedPane.getTabCount() - 1 );
        panelCount--;
    }

    public void save() {
        FileWriter fw;
        try {
            fw = new FileWriter(fileList.get(tabbedPane.getSelectedIndex()).getPath());

            String text = textAreaList.get(tabbedPane.getSelectedIndex()).getText();

            for(int i = 0; i < text.length(); i++) {
                fw.write(text.charAt(i));
            }

            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAs() {

        JFileChooser saveFiles = new JFileChooser();
        saveFiles.setApproveButtonText("Save");
        saveFiles.setDialogTitle("Save as");
        int saveValue = saveFiles.showOpenDialog(null);

        if (saveValue == JFileChooser.APPROVE_OPTION) {
                File openFile = saveFiles.getSelectedFile();
                fileList.set(tabbedPane.getSelectedIndex(), openFile);
                tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), openFile.getName());


                //Checking if the file name is already in used
                if (openFile.exists()) {
                    JFrame frame = new JFrame("Warning");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    int confirmAnswer = JOptionPane.showConfirmDialog(frame, "The File already exist. Do you want to replace it?", "Confirm save as", JOptionPane.OK_CANCEL_OPTION);
                    //If the file exists but the user still wants to save it
                    if (confirmAnswer == 0) {
                        save();
                    }
                    //Else, if the user wants to change the name to keep both files
                    else {
                        saveAs();
                    }
                }
                else {
                    save();
                }
        }
    }

    public void find() {
//        String textToFind = JOptionPane.showInputDialog("Find into the document");
//        int indexStart = textAreaList.get(tabbedPane.getSelectedIndex()).getText().indexOf(textToFind);
        findText.setVisible(true);
        findButton.setVisible(true);

        findButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String textToFind = findText.getText();

                int indexStart = textAreaList.get(tabbedPane.getSelectedIndex()).getText().indexOf(textToFind);

                if(indexStart == -1) {
                    JOptionPane.showMessageDialog(null, "No found");
                }
                else {
                    System.out.println(indexStart + " " + (textToFind.length() + indexStart));
                    System.out.println(textAreaList.get(tabbedPane.getSelectedIndex()).getText());
                    System.out.println(textAreaList.get(tabbedPane.getSelectedIndex()).getText().length());
                    textAreaList.get(tabbedPane.getSelectedIndex()).requestFocus();
                    textAreaList.get(tabbedPane.getSelectedIndex()).select(indexStart, textToFind.length() + indexStart);
                }

            }
        });
    }

}
