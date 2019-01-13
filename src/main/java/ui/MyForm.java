package ui;

import plugin.MyConfigurable;
import stuff.ExpressionItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyForm {
    private JPanel root = new JPanel();
    private JButton addButton = new JButton("Add");
    public JTextField textField1 = new JTextField();
    private JPanel panel = new JPanel();
    private boolean changed = false;

    private MyConfigurable configuration;

    public JComponent getRootComponent() {
        return root;
    }

    public MyForm(MyConfigurable configurable) {
        this.configuration = configurable;
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JComponent[] allComponents = { textField1, addButton, panel};
        add(allComponents);
        if (!configuration.getExpressionItems().isEmpty()) {
            for (ExpressionItem item: configuration.getExpressionItems()) {
                panel.add(new UIExprItem(item.getExpression(), configuration));
            }
            root.revalidate();
        }
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expression = textField1.getText();
                //TODO проверять на наличие
                if (!expression.isEmpty()) {
                    panel.add(new UIExprItem(expression, configuration));
                    root.revalidate();
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
        return changed |= changed;
    }

}
