package org.example;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;


public class Tools {
    //-----------------Add text at the end--------------------------
    public static void append(String line, JTextPane textArea) {
        try {
            Document doc = textArea.getDocument();
            doc.insertString(doc.getLength(), line, null);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }
    //--------------------------------------------------------------

    //-----------------Method to show the numbers in the view-------
    public static void viewNumbersScreen(boolean numbers, JTextPane textArea, JScrollPane scroll) {
        if(numbers) {
            scroll.setRowHeaderView(new TextLineNumber(textArea));
        }
        else {
            scroll.setRowHeaderView(null);
        }
    }

    public static void viewNumberSet(int counter, boolean numbers, ArrayList<JTextPane> textAreaList, ArrayList<JScrollPane> scrollList) {
        if(numbers) {
            for (int i = 0; i < counter; i++) {
                scrollList.get(i).setRowHeaderView(new TextLineNumber(textAreaList.get(i)));
            }
        }
        else {
            for (int i = 0; i < counter; i++) {
                scrollList.get(i).setRowHeaderView(null);
            }
        }
    }
    //--------------------------------------------------------------
    //-------------------------Appearance --------------------------
    public static void aBanckground(int counter, String typeB, int textSize, ArrayList<JTextPane> list){

        //If the TypeB is white change all the backgrounds to white
        if(typeB.equals("white")) {
            for (int i = 0; i < counter; i++) {
                list.get(i).selectAll();

                StyleContext sc = StyleContext.getDefaultStyleContext();

                //apply Text Color
                AttributeSet attributeSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);

                //Apply text Style
                sc.addAttribute(attributeSet, StyleConstants.FontFamily, "Arial");

                //To change the size of the text
                sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.FontSize, textSize);


                //Apply the new text color, text style and background to all text panel
                list.get(i).setCharacterAttributes(attributeSet, false);
                list.get(i).setBackground(Color.WHITE);
            }
        }
        else if(typeB.equals("dark")) {
            for (int i = 0; i < counter; i++) {
                list.get(i).selectAll();

                StyleContext sc = StyleContext.getDefaultStyleContext();

                //apply Text Color
                AttributeSet attributeSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.WHITE);

                //Apply text Style
                sc.addAttribute(attributeSet, StyleConstants.FontFamily, "Arial");

                sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.FontSize, textSize);

                //Apply the new text color, text style and background to all text panel
                list.get(i).setCharacterAttributes(attributeSet, false);
                list.get(i).setBackground(new Color(32, 33, 36));
            }
        }

        //--------------------------------------------------------------

    }
    //-------------------------CloseButton --------------------------
    public static JButton addButton(URL url, Object objContainer, String descriptionButton) {

        //Create a button with an icon image
        JButton button = new JButton(new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH)));
        button.setToolTipText(descriptionButton);
        ((Container) objContainer).add(button);

        return button;
    }

    //--------------------------------------------------------------

    //--------------------Text Size--------------------------------
    public static void textSize(int size, int counter, ArrayList<JTextPane> textList) {
        for (int i = 0; i < counter; i++) {
            textList.get(i).selectAll();

            StyleContext sc = StyleContext.getDefaultStyleContext();

            //To change the size of the text
            AttributeSet attributeSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.FontSize, size);

            //Apply the new size to the text
            textList.get(i).setCharacterAttributes(attributeSet, false);
        }

    }
    //--------------------------------------------------------------
    //--------------------Disable and active the Item from the Menu based on if there are tabs opens

    public static void disableMenuItems(JMenuItem[] listItems) {
        for(JMenuItem a: listItems) {
            a.setEnabled(false);
        }
    }

    public static void activeMenuItems(JMenuItem[] listItems) {
        for(JMenuItem a: listItems) {
            a.setEnabled(true);
        }
    }
    // --------------------------------------------------------------
    // ---------------------Google Search---------------------
    public static void GoogleFind(String text) {
        URI uri;
        String googleUrl = "https://www.google.com/search?q=";
        String query = googleUrl + text;

        try {
            uri = new URI(query);
            Desktop.getDesktop().browse(uri);

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    // --------------------------------------------------------------
}
