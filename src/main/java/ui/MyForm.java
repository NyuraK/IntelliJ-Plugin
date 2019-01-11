package ui;

import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyForm {
    private JPanel root = new JPanel();
    private JButton addButton = new JButton("Add");
    private JTextField textField1 = new JTextField();
    private JPanel panel = new JPanel();

    public JComponent getRootComponent() {
        return root;
    }

    public MyForm() {
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JComponent[] allComponents = { textField1, addButton, panel };
        add(allComponents);
//        root.add(textField1);
//        root.add(addButton);
//        root.add(scrollPanel);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expression = textField1.getText();
                if (!expression.isEmpty()) {
                    panel.add(new UIExprItem(expression));
                    panel.revalidate();
                }
                textField1.setText("");
            }
        });
    }

    private void add(JComponent[] allComponents) {
        for (JComponent component : allComponents) {
//            component.setAlignmentX(Component.CENTER_ALIGNMENT);
            root.add(component);
        }
    }


}
