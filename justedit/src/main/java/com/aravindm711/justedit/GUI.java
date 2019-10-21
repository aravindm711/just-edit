package com.aravindm711.justedit;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.text.DefaultEditorKit;

public class GUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private final JTextArea textArea;
    private final JMenuBar menuBar;
    private final JMenu menuFile, menuEdit;
    private final JMenuItem newFile, openFile, saveFile, quit, cut,
                            copy, paste, clearFile, wordWrap;

    private HighlightText highlighter = new HighlightText(Color.GRAY);
    private SupportedKeywords kw = new SupportedKeywords();

    private boolean edit = false;
    private File chosenFile;

    public GUI() {
        // Initial Properties of window
        setSize(700, 1000);
        setTitle("Untitled - " + App.NAME);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initial properties of textarea
        textArea = new JTextArea("", 0, 0);
        textArea.setBackground(new Color(20, 20, 20));
        textArea.setForeground(Color.WHITE);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
        textArea.setCaretColor(new Color(255, 0, 0));
        textArea.setTabSize(4);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                edit = true;
                highlighter.highLight(textArea, kw.getJavaKeywords());
                highlighter.highLight(textArea, kw.getJavaKeywords());
            }
        });

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setViewportView(textArea);
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

        wordWrap = new JMenuItem();
        wordWrap.setText("Word Wrap");
        wordWrap.setToolTipText("Word Wrap");
        wordWrap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
        wordWrap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setLineWrap((textArea.getLineWrap() == false)? true: false);
            }
        });

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
        menuEdit.add(wordWrap);
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            if (edit && textArea.getText() != "") {
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
                    ClearTextArea.clear(textArea);
                    setTitle("Untitled - " + App.NAME);
                } else if (n == 1) {
                    setTitle("Untitled - " + App.NAME);
                    ClearTextArea.clear(textArea);
                }
                edit = false;
            } else {
                ClearTextArea.clear(textArea);
            }
        } else if (e.getSource() == openFile) {
            if (edit) {
                saveFile();
            }

            JFileChooser open = new JFileChooser();
            int option = open.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                ClearTextArea.clear(textArea);
                try {
                    chosenFile = open.getSelectedFile();
                    setTitle(chosenFile.getName() + " | " + App.NAME);
                    Scanner scan = new Scanner(new FileReader(chosenFile.getPath()));
                    while (scan.hasNext()) {
                        textArea.append(scan.nextLine() + "\n");
                    }
                    scan.close();

                    // enableAutoComplete(openFile);
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
                ClearTextArea.clear(textArea);
            }
        } else {}
    }

    public void saveFile() {
        try {
            int option = 0;
            if (!edit) {
                JFileChooser fileChoose = new JFileChooser();
                option = fileChoose.showSaveDialog(this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    chosenFile = fileChoose.getSelectedFile();
                    setTitle(chosenFile.getName() + " - " + App.NAME);
                    // enableAutoComplete(openFile);
                }
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(chosenFile.getPath()));
            out.write(textArea.getText());
            out.close();

            edit = (option == JFileChooser.APPROVE_OPTION)? true: false;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}