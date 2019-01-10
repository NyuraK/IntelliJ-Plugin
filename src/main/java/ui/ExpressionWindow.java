package ui;

import plugin.MyConfigurable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpressionWindow {
    private JPanel root;
    private JButton addButton;

    public ExpressionWindow(MyConfigurable configurable) {
//        addButton.addActionListener(new AddNewItemAction(grepTable, false));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        root.getAccessibleContext();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public JPanel getRootComponent() {
        return root;
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
