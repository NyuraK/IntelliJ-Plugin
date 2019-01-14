package ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.IdeFrame;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.components.JBCheckBox;
import plugin.MyConfigurable;
import stuff.ExpressionItem;
import stuff.Operation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIExprItem extends JPanel {
    private static UIExprItem ourInstance = new UIExprItem();

    private static final Icon ICON = IconLoader.getIcon("/rubbish-bin.png");
    private JLabel expression;
    private JButton picker = new JButton();
    private JButton deleteBtn = new JButton();
    private JCheckBox highlightOnlyMatching = new JBCheckBox();
    ExpressionItem item;

    private UIExprItem(){}

    public static UIExprItem getInstance() {
        return ourInstance;
    }

    public UIExprItem(String text, Color color, boolean caseMode) {
        expression = new JLabel(text);
        picker.setBackground(color);
        picker.setForeground(color);
        picker.setEnabled(false);
        deleteBtn.setIcon(ICON);
        deleteBtn.setBorder(BorderFactory.createEmptyBorder());

        this.add(expression);
        this.add(picker);
        this.add(deleteBtn);
        this.add(highlightOnlyMatching);

        item = new ExpressionItem();
        item.setStyle(Color.BLACK, color)
                .setExpression(text).setCaseSensitive(caseMode);
        MyConfigurable.getInstance().setExpressionItems(item);

        addListeners();
    }


    private void addListeners() {

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = MyConfigurable.getInstance().form.getPanel();
                Component[] componentList = panel.getComponents();
                for(Component c : componentList){
                    if(c instanceof UIExprItem){
                        if (((UIExprItem) c).expression.getText().equals(expression.getText())) {
                            panel.remove(c);
                        }
                    }
                }
                MyConfigurable.getInstance().form.getRootComponent().revalidate();
                MyConfigurable.getInstance().form.getRootComponent().repaint();
                MyConfigurable.getInstance().setExpressionItems(new ExpressionItem()
                        .setStyle(Color.BLACK, Color.white).setExpression(item.getExpression()));
                MyConfigurable.getInstance().deleteItem(item);
                MyConfigurable.getInstance().setOperation(Operation.DELETE);
            }
        });
    }

    private static JComponent rootComponent(Project project) {
        if (project != null) {
            IdeFrame frame = WindowManager.getInstance().getIdeFrame(project);
            if (frame != null)
                return frame.getComponent();
        }

        JFrame frame = WindowManager.getInstance().findVisibleFrame();
        return frame != null ? frame.getRootPane() : null;
    }


}
