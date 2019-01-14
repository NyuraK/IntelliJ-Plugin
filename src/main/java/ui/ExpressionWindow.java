package ui;

import plugin.MyConfiguration;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Deprecated
public class ExpressionWindow extends JFrame {
    private JPanel root;
    private JButton addButton;
    private JTextField textField1;
    private JPanel panel;

    public ExpressionWindow(MyConfiguration configurable) {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel component = new JLabel(textField1.getText());
                panel.add(component);
//                scrollPanel.add(new UIExprItem(textField1.getText()));
//                scrollPanel.revalidate();

                textField1.setText("");
                pack();
                repaint();
            }
        });

        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        root.getAccessibleContext();
    }

    public JPanel getRootComponent() {
        return root;
    }

    private void createUIComponents() {
//        scrollPanel = new JBScrollPane();
//        panel = new JPanel();
//        panel.add(scrollPanel);
//        root.add(panel);
//        pack();
//        UIExprItem1 = new UIExprItem("default");
//        UIExprItem1.setPreferredSize(new Dimension(70, 30));
    }


//    private class AddNewItemAction implements ActionListener {
//        private CheckboxTreeTable myTable;
//        private final boolean input;
//
//        public AddNewItemAction(CheckboxTreeTable table, boolean input) {
//            myTable = table;
//            this.input = input;
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            DefaultMutableTreeNode selectedNode = getSelectedNode(myTable);
//            GrepExpressionItem userObject;
//            if (input) {
//                GrepExpressionItem item = new GrepExpressionItem();
//                item.setInputFilter(true);
//                item.action(GrepExpressionItem.ACTION_REMOVE);
//                item.setGrepExpression(".*unwanted line.*");
//                item.setEnabled(true);
//                userObject = item;
//            } else {
//                GrepExpressionItem item = new GrepExpressionItem();
//                item.setGrepExpression("foo");
//                item.setEnabled(true);
//                item.setContinueMatching(true);
//                item.setHighlightOnlyMatchingText(true);
//                item.getStyle().setBackgroundColor(new GrepColor(true, JBColor.CYAN));
//                userObject = item;
//            }
//            final CheckedTreeNode newChild = new GrepExpressionItemTreeNode(userObject);
//            if (selectedNode == null) {
//                DefaultMutableTreeNode root = (DefaultMutableTreeNode) myTable.getTree().getModel().getRoot();
//                DefaultMutableTreeNode lastChild = getLastChild(root);
//                if (lastChild == null) {
//                    GrepExpressionGroupTreeNode aNew = new GrepExpressionGroupTreeNode(new GrepExpressionGroup("new"));
//                    aNew.add(newChild);
//                    root.add(aNew);
//                } else {
//                    lastChild.add(newChild);
//                }
//            } else if (selectedNode.getUserObject() instanceof GrepExpressionGroup) {
//                selectedNode.add(newChild);
//            } else {
//                GrepExpressionGroupTreeNode parent = (GrepExpressionGroupTreeNode) selectedNode.getParent();
//                parent.insert(newChild, parent.getIndex(selectedNode) + 1);
//            }
//            rebuildProfile();
//            TableUtils.reloadTree(myTable);
//            TableUtils.selectNode(newChild, myTable);
//            myTable.requestFocus();
//        }
//
//    }

}
