package com.aravindm711.justedit;

import java.io.File;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;
import javax.swing.text.DefaultEditorKit;

public class GUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private final JTextPane textPane;
    private final JMenuBar menuBar;
    private final JMenu menuFile, menuEdit;
    private final JMenuItem newFile, openFile, saveFile, quit, cut,
                            copy, paste, clearFile;

    private HighlightSyntax syntax = new HighlightSyntax();

    private String fileExtension = "";
    private boolean edit = false;
    private File chosenFile;

    public GUI() {
        // Initial Properties of window
        setSize(1100, 1000);
        setTitle("Untitled - " + App.NAME);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initial properties of textPane
        textPane = new JTextPane();
        textPane.setBackground(new Color(22, 24, 33));
        textPane.setForeground(new Color(198, 200, 209));
        textPane.setFont(new Font("Monospaced", Font.PLAIN, 16));
        textPane.setCaretColor(new Color(198, 200, 209));
        textPane.setBorder(new EmptyBorder(new Insets(1, 5, 1, 1)));
        textPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                edit = true;
                syntax.highlight(textPane, fileExtension);
            }
        });

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setViewportView(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane);
        getContentPane().add(panel);

        // initialise menus
        menuFile = new JMenu("File");
        menuEdit = new JMenu("Edit");

        // initialise menu items
        newFile = new JMenuItem("New");
        openFile = new JMenuItem("Open");
        saveFile = new JMenuItem("Save");
        quit = new JMenuItem("Quit");
        clearFile = new JMenuItem("Clear");

        // set menu bar and add menus
        menuBar = new JMenuBar();
        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        this.setJMenuBar(menuBar);

        // adding action listeners and adding menu items to menuFile
        newFile.addActionListener(this);
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));

        openFile.addActionListener(this);
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));

        saveFile.addActionListener(this);
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));

        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        quit.addActionListener(this);
        
        menuFile.add(newFile);
        menuFile.add(openFile);
        menuFile.add(saveFile);
        menuFile.add(quit);

        // adding action listeners and adding menu items to menuEdit
        cut = new JMenuItem(new DefaultEditorKit.CutAction());
        cut.setText("Cut");
        cut.setToolTipText("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));

        copy = new JMenuItem(new DefaultEditorKit.CopyAction());
        copy.setText("Copy");
        copy.setToolTipText("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));

        paste = new JMenuItem(new DefaultEditorKit.PasteAction());
        paste.setText("Paste");
        paste.setToolTipText("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));

        menuEdit.add(cut);
        menuEdit.add(copy);
        menuEdit.add(paste);
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            if (edit && textPane.getText() != "") {
                String[] options = {"Save and exit", "Don't Save and exit", "Cancel"};
                int n = JOptionPane.showOptionDialog(this, "Do you want to save the file ?", "Question",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (n == 0) {
                    saveFile();
                    this.dispose();
                } else if (n == 1) {
                    this.dispose();
                } else {}
            } else {
                System.exit(99);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quit) {
            if (edit) {
                Object[] options = {"Save and exit", "Don't Save and exit", "Cancel"};
                int n = JOptionPane.showOptionDialog(this, "Do you want to save the file ?", "Question",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (n == 0) {
                    saveFile();
                    this.dispose();
                } else if (n == 1) {
                    this.dispose();
                } else {}
            } else {
                this.dispose();
            }
        } else if (e.getSource() == newFile) {
            if (edit) {
                Object[] options = {"Save", "Don't Save", "Cancel"};
                int n = JOptionPane.showOptionDialog(this, "Do you want to save the open file?", "Question",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
                if (n == 0) {
                    saveFile();
                    ClearEditorArea.clear(textPane);
                    setTitle("Untitled - " + App.NAME);
                } else if (n == 1) {
                    setTitle("Untitled - " + App.NAME);
                    ClearEditorArea.clear(textPane);
                }
                edit = false;
            } else {
                ClearEditorArea.clear(textPane);
            }
        } else if (e.getSource() == openFile) {
            if (edit) {
                saveFile();
            }

            JFileChooser open = new JFileChooser();
            int option = open.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                ClearEditorArea.clear(textPane);
                try {
                    chosenFile = open.getSelectedFile();
                    setTitle(chosenFile.getName() + " - " + App.NAME);
                    Scanner scan = new Scanner(new FileReader(chosenFile.getPath()));
                    String lineNumbers = "", prev;
                    while ((lineNumbers = scan.nextLine()) != null) {
                        prev = textPane.getText();
                        if (prev == "") textPane.setText(lineNumbers);
                        else textPane.setText(prev + "\n" + lineNumbers);
                    }
                    scan.close();

                    setFileExtension();
                    syntax.highlight(textPane, fileExtension);
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
            }
        } else if (e.getSource() == saveFile) {
            saveFile();
        } else if (e.getSource() == clearFile) {
            String[] options = {"Yes", "No"};
            int n = JOptionPane.showOptionDialog(this, "Are you sure to clear the text Area?", "Question",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (n == 0) {
                ClearEditorArea.clear(textPane);
            }
        } else {}
    }

    public void setFileExtension() {
        fileExtension = chosenFile.getName();
        fileExtension = fileExtension.substring(fileExtension.lastIndexOf("."), fileExtension.length());
    }

    public void saveFile() {
        System.out.println("hello");
        try {
            int option = 0;
            if (!edit) {
                JFileChooser fileChoose = new JFileChooser();
                option = fileChoose.showSaveDialog(this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    chosenFile = fileChoose.getSelectedFile();
                    setTitle(chosenFile.getName() + " - " + App.NAME);
                }
                setFileExtension();
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(chosenFile.getPath()));
            out.write(textPane.getText());
            out.close();

            edit = (option == JFileChooser.CANCEL_OPTION)? true: false;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}