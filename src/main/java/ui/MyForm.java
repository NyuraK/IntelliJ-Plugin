package ui;

import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import plugin.MyConfigurable;
import stuff.ExpressionItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MyForm {
    private JPanel root = new JPanel();
    private JButton addButton = new JButton("Add");
    public JTextField textField1 = new JTextField();
    private JPanel panel = new JPanel();
    private Boolean changed = false;
//    private JBList<UIExprItem> myItems = new JBList<>();

    private MyConfigurable configuration;

    public JComponent getRootComponent() {
        return root;
    }

    public MyForm(MyConfigurable configurable) {
        this.configuration = configurable;
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JComponent[] allComponents = { textField1, addButton, panel };
        add(allComponents);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expression = textField1.getText();
                if (!expression.isEmpty()) {
                    panel.add(new UIExprItem(expression, configuration));
                    panel.revalidate();
                }
                textField1.setText("");
                changed = true;
            }
        });
    }

    private void add(JComponent[] allComponents) {
        for (JComponent component : allComponents) {
//            component.setAlignmentX(Component.CENTER_ALIGNMENT);
            root.add(component);
        }
    }


    public boolean isChanged() {
        return changed;
    }
}
