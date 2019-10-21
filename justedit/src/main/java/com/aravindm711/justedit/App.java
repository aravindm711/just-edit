package com.aravindm711.justedit;

import javax.swing.JTextPane;

public class App extends JTextPane {

    private static final long serialVersionUID = 1L;
    public static final String NAME = "JustEdit";

    public static void main( String[] args ) {
        new GUI().setVisible(true);
    }

}
