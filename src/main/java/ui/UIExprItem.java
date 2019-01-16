package ui;

import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.components.JBCheckBox;
import plugin.MyConfiguration;
import stuff.ExpressionItem;
import stuff.Operation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIExprItem extends JPanel {
    private static final Icon ICON = IconLoader.getIcon("/rubbish-bin.png");
    private static UIExprItem ourInstance = new UIExprItem();
    ExpressionItem item;
    private JLabel expression;
    private JButton picker = new JButton();
    private JButton deleteBtn = new JButton();

    private UIExprItem() {
    }

    public UIExprItem(String text, Color color, boolean caseMode, Operation operation) {
        expression = new JLabel(text);
        picker.setBackground(color);
        picker.setForeground(color);
        picker.setEnabled(false);
        deleteBtn.setIcon(ICON);
        deleteBtn.setBorder(BorderFactory.createEmptyBorder());

        this.add(expression);
        this.add(picker);
        this.add(deleteBtn);

        item = new ExpressionItem();
        item.setStyle(Color.BLACK, color)
                .setExpression(text).setCaseSensitive(caseMode).setOperation(operation);
        MyConfiguration.getInstance().setExpressionItems(item);

        addListeners();
    }

    public static UIExprItem getInstance() {
        return ourInstance;
    }

    private void addListeners() {

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = MyConfiguration.getInstance().getForm().getPanel(item.getOperation());
                Component[] componentList = panel.getComponents();
                for (Component c : componentList) {
                    if (c instanceof UIExprItem) {
                        if (((UIExprItem) c).expression.getText().equals(expression.getText())) {
                            panel.remove(c);
                        }
                    }
                }
                MyConfiguration.getInstance().getForm().getRootComponent().revalidate();
                MyConfiguration.getInstance().getForm().getRootComponent().repaint();

                MyConfiguration.getInstance().deleteItem(item);

            }
        });
    }

    public String getExpression() {
        return expression.getText();
    }
}
