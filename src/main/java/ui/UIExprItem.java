package ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.IdeFrame;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBCheckBox;
import plugin.MyConfigurable;
import stuff.ExpressionItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIExprItem extends JPanel {

    public static final Icon ICON = IconLoader.getIcon("/rubbish-bin.png");
    private MyConfigurable configuration;
    private JLabel expression;
    private JButton picker = new JButton();
    private JButton deleteBtn = new JButton();
    private JCheckBox highlightOnlyMatching = new JBCheckBox();
    ExpressionItem item;

    public UIExprItem(String text, MyConfigurable configuration) {
        this.configuration = configuration;
        expression = new JLabel(text);
        picker.setBackground(Color.DARK_GRAY);
        deleteBtn.setIcon(ICON);

        this.add(expression);
        this.add(picker);
        this.add(deleteBtn);
        this.add(highlightOnlyMatching);

        item = new ExpressionItem(text);
        item.setStyle(Color.BLACK, Color.BLUE);
        item.setWholeLine(true);
        item.setExpression(text);
        configuration.setExpressionItems(item);
//        addExpItem(Color.BLUE);

        addListeners();
    }

    private void addListeners() {
        picker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Color color = ColorPicker.showDialog(rootComponent(configuration.getProject()), "Background color", Color.BLUE, true, null, true);
//                picker.setBackground(color);
//                addExpItem(color);
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("DELETE");
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

    private void addExpItem(Color color) {
        item.setStyle(JBColor.BLACK, color);
        item.setWholeLine(true);
//        item.setExpression();
        configuration.setExpressionItems(item);
    }

}
