package ui;

import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.ColorPicker;
import plugin.MyConfigurable;
import stuff.ExpressionItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyForm {
    private static final Icon ICON = IconLoader.getIcon("/color-palette.png");


    private JPanel root = new JPanel();
    private JButton addButton = new JButton("Add");
    public JTextField textField1 = new JTextField();
    private JButton colorButton = new JButton();
    private JPanel panel = new JPanel();
    private boolean changed = false;
    private Color color;


    private MyConfigurable configuration;

    public JComponent getRootComponent() {
        return root;
    }

    public JPanel getPanel() {
        return panel;
    }

    public MyForm(MyConfigurable configurable) {
        this.configuration = configurable;
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        colorButton.setSize(32, 32);
        colorButton.setIcon(ICON);
        colorButton.setBorder(BorderFactory.createEmptyBorder());
        JComponent[] allComponents = { textField1, colorButton, addButton, panel};
        add(allComponents);
        if (!configuration.getExpressionItems().isEmpty()) {
            for (ExpressionItem item: configuration.getExpressionItems()) {
                panel.add(new UIExprItem(item.getExpression(), item.getColor(), configuration));
            }
            root.revalidate();
        }
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expression = textField1.getText();
                //TODO проверять на наличие в
                if (!expression.isEmpty()) {
                    panel.add(new UIExprItem(expression, color, configuration));
                    root.revalidate();
                }
                textField1.setText("");
                changed = true;
            }
        });

        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = ColorPicker.showDialog(panel, "Background color", Color.BLUE, true, null, true);
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
