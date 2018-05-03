package view;

import javax.swing.*;
import java.awt.*;

public class ArticleView extends JFrame {

    private JTextField authorTextField;
    private JTextField abstractTextField;
    private JTextField titleTextField;
    private JTextArea textArea;
    private FeedView feedView;
    private String id;

    public ArticleView(FeedView feedView, boolean editable) {
        this.feedView = feedView;
        initialize(editable);
    }

    private void initialize(boolean editable) {
        setTitle("Article");
        setBounds(100, 100, 600, 379);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.NORTH);

        JPanel panel_3 = new JPanel();
        panel.add(panel_3);

        JLabel lblTitle = new JLabel("Title");
        panel_3.add(lblTitle);

        titleTextField = new JTextField();
        titleTextField.setEditable(editable);
        titleTextField.setColumns(10);
        panel_3.add(titleTextField);

        JPanel panel_2 = new JPanel();
        panel.add(panel_2);

        JLabel lblAbstract = new JLabel("Abstract");
        panel_2.add(lblAbstract);

        abstractTextField = new JTextField();
        abstractTextField.setEditable(editable);
        abstractTextField.setColumns(10);
        panel_2.add(abstractTextField);

        JPanel panel_1 = new JPanel();
        panel.add(panel_1);

        JLabel lblNewLabel = new JLabel("Author");
        panel_1.add(lblNewLabel);

        authorTextField = new JTextField();
        authorTextField.setEditable(editable);
        panel_1.add(authorTextField);
        authorTextField.setColumns(10);

        JButton btnSave = new JButton("Save");
        btnSave.setVisible(editable);
        btnSave.addActionListener(e -> createOrSave());
        panel.add(btnSave);

        textArea = new JTextArea();
        textArea.setEditable(editable);
        getContentPane().add(textArea, BorderLayout.CENTER);
    }

    private void createOrSave() {
        if (id == null)
            id = "new";
        feedView.saveArticle(id, titleTextField.getText(), abstractTextField.getText(), authorTextField.getText(), textArea.getText());
        this.dispose();
    }

    public void setArticleInformation(String[] strings) {
        id = strings[0];
        titleTextField.setText(strings[1]);
        abstractTextField.setText(strings[2]);
        authorTextField.setText(strings[3]);
        textArea.setText(strings[4]);
    }

}
