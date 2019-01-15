package ui;

import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.ColorPicker;
import plugin.MyConfiguration;
import stuff.ExpressionItem;
import stuff.Operation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyForm extends JFrame {
    private static final Icon ICON = IconLoader.getIcon("/color-palette.png");
    private JPanel root = new JPanel();

    /*For highlighting*/
    private JButton addButton = new JButton("Add");
    public JTextField textField1 = new JTextField();
    private JButton colorButton = new JButton();
    private JPanel panel = new JPanel();
    private JPanel panelForEl = new JPanel();
    private JRadioButton caseButton = new JRadioButton("Case insensitive", null, false);

    /*For excluding*/
    private JButton addButton2 = new JButton("Add");
    public JTextField textField2 = new JTextField();
    private JPanel panel2 = new JPanel();
    private JPanel panelForEl2 = new JPanel();
    private JRadioButton caseButton2 = new JRadioButton("Case insensitive", null, false);

    private boolean changed = false;
    private Color color;

    public JComponent getRootComponent() {
        return root;
    }

    public JPanel getPanel(Operation operation) {
        if (operation == Operation.ADD)
            return panelForEl;
        else return panelForEl2;
    }

    public MyForm() {
        placeElements();
        pack();
        if (!MyConfiguration.getInstance().getExpressionItems().isEmpty()) {
            for (ExpressionItem item: MyConfiguration.getInstance().getExpressionItems()) {
                panelForEl.add(new UIExprItem(item.getExpression(), item.getColor(), caseButton.isSelected(), Operation.ADD));
            }
            root.revalidate();
        }
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expression = textField1.getText();
                //TODO проверять на наличие в
                if (!expression.isEmpty() && !containsItem(expression)) {
                    panelForEl.add(new UIExprItem(expression, color, caseButton.isSelected(), Operation.ADD));
                    caseButton.setSelected(false);
                    root.revalidate();
                }
                textField1.setText("");
                MyConfiguration.getInstance().setOperation(Operation.ADD);
                changed = true;
            }
        });

        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = ColorPicker.showDialog(panel, "Background color", Color.white, true, null, true);
            }
        });

        addButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expression = textField2.getText();
                //TODO проверять на наличие в
                if (!expression.isEmpty()) {
                    panelForEl2.add(new UIExprItem(expression, Color.white, caseButton2.isSelected(), Operation.DELETE));
                    caseButton.setSelected(false);
                    root.revalidate();
                }
                textField2.setText("");
//                MyConfiguration.getInstance().setOperation(Operation.ADD);
                changed = true;
            }
        });

    }

    private boolean containsItem(String expression) {
        Component[] componentList = panelForEl.getComponents();
        for (Component c : componentList) {
            if (c instanceof UIExprItem) {
                if (((UIExprItem) c).getExpression().equals(expression)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void placeElements() {
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panelForEl.setLayout(new BoxLayout(panelForEl, BoxLayout.Y_AXIS));
        colorButton.setSize(32, 32);
        colorButton.setIcon(ICON);
        colorButton.setBorder(BorderFactory.createEmptyBorder());
        JComponent[] allComponents = { textField1, colorButton, caseButton, addButton};
        add(allComponents, panel);
        addToRoot(panel, panelForEl);

        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panelForEl2.setLayout(new BoxLayout(panelForEl2, BoxLayout.Y_AXIS));
        JComponent[] allComponents2 = { textField2, caseButton2, addButton2};
        add(allComponents2, panel2);
        addToRoot(panel2, panelForEl2);
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        colorButton.setSize(32, 32);
//        colorButton.setIcon(ICON);
//        colorButton.setBorder(BorderFactory.createEmptyBorder());
//        GroupLayout layout = new GroupLayout(root);
//        layout.setAutoCreateGaps(true);
//        layout.setAutoCreateContainerGaps(true);
//        layout.setVerticalGroup(
//                layout.createSequentialGroup()
//                        .addComponent(textField1)
//                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
//                                .addComponent(caseButton)
//                                .addComponent(colorButton))
//                        .addComponent(addButton)
//                        .addComponent(panel)
//        );
//        root.setLayout(layout);

    }

    private void add(JComponent[] allComponents, JPanel panel) {
        for (JComponent component : allComponents) {
            component.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(component);
        }
    }

    private void addToRoot(JPanel panel, JPanel panelForEl){
        panelForEl.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        root.add(panel);
        root.add(panelForEl);
    }

    public void addUIItem(String expression, Color color, Operation operation) {
        if (!expression.isEmpty() && !containsItem(expression)) {
            if (operation == Operation.ADD)
                panelForEl.add(new UIExprItem(expression, color, false, operation));
            else panelForEl2.add(new UIExprItem(expression, color, false, operation));
            root.revalidate();
        }
    }

    public boolean isChanged() {
        return changed;
    }

}
