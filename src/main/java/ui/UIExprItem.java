package ui;

import javax.swing.*;

public class UIExprItem extends JPanel {

    private JLabel expression;
    //        private ColorChooser color;
    private JButton picker;
    private JButton deleteBtn;
    private JCheckBox highlightOnlyMatching;

    public UIExprItem(String text) {
        expression = new JLabel(text);
        picker = new JButton("Pick color");
        deleteBtn = new JButton("Delete");

        this.add(expression);
        this.add(picker);
        this.add(deleteBtn);
    }

    //TODO insert paint() ?
}
