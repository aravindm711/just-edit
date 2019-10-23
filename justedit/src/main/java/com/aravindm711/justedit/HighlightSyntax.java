package com.aravindm711.justedit;

import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.AttributeSet;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.text.Document;
import javax.swing.text.BadLocationException;
import java.util.*;

class Pair { 
    private int x; 
    private int y;

    public Pair(int x, int y) { 
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    public int getSum() {
        return x + y;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
} 

public class HighlightSyntax {

    private StyleContext sc;
    private AttributeSet aset;
    private SupportedKeywords kw = new SupportedKeywords();

    public HighlightSyntax() {
        sc = StyleContext.getDefaultStyleContext();
    }

    public void paintText(JTextPane textPane, int pos, String text, Color c) {
        aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Monospaced");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        
        textPane.setCaretPosition(pos);
        textPane.setCharacterAttributes(aset, false);
        textPane.replaceSelection(text);
    }

    public void highlight(JTextPane textPane, String fileExtension) {
        try {
            if (kw.supportedLanguage(fileExtension)) {
                String [] keyWords = kw.getKeywords(fileExtension);
                ArrayList<Pair> keyWordPositions = new ArrayList<>();
                Document doc = textPane.getDocument();
                String text = doc.getText(0, doc.getLength());
                for (int i = 0; i < keyWords.length; i++) {
                    int pos = 0;
                    while ((pos = text.indexOf(keyWords[i], pos)) >= 0) {
                        keyWordPositions.add(new Pair(pos, keyWords[i].length()));
                        pos += keyWords[i].length();
                    }
                }
                System.out.println(keyWordPositions);
                Collections.sort(keyWordPositions, new Comparator<Pair>() {
                    @Override
                    public int compare(Pair p1, Pair p2) {
                        return p1.getX() - p2.getX();
                    }
                });

                int pos = 0;
                String prev;
                for (int i = 0; i < keyWordPositions.size(); i++) {
                    prev = text.substring(pos, keyWordPositions.get(i).getX());
                    System.out.println(prev);
                    paintText(textPane, pos, prev, Color.WHITE);
                    pos = keyWordPositions.get(i).getX();
                    paintText(textPane, pos, text.substring(pos, pos + keyWordPositions.get(i).getY() + 1), Color.RED);
                    System.out.println(text.substring(pos, pos + keyWordPositions.get(i).getY() + 1));
                    pos = keyWordPositions.get(i).getSum() + 1;
                }
            }
        } catch (BadLocationException e) {}
    }
}