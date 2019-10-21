package com.aravindm711.justedit;

import javax.swing.JTextArea;

public class ClearTextArea {

    public ClearTextArea() {}

    public static void clear(JTextArea textArea) {
        textArea.setText("");
    }

}