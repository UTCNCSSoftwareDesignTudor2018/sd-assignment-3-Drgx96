package view;

import presenter.Presenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FeedView extends JFrame {

    private static final long serialVersionUID = 6172540968549250545L;
    private JTable table;
    private JTextField userTextField;
    private JTextField passwordTextField;
    private JPanel loggedOutPanel;
    private JPanel loggedInPanel;
    private List<String[]> articles;
    private boolean loggedIn;
    private ArticleView articleView;
    private Presenter presenter;

    public FeedView() {
        setTitle("News");
        initialize();
    }

    private void initialize() {
        setBounds(100, 100, 485, 475);
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        loggedOutPanel = new JPanel();
        getContentPane().add(loggedOutPanel);

        JLabel lblNewLabel = new JLabel("User");
        loggedOutPanel.add(lblNewLabel);

        userTextField = new JTextField();
        loggedOutPanel.add(userTextField);
        userTextField.setColumns(10);

        JLabel lblPassword = new JLabel("Password");
        loggedOutPanel.add(lblPassword);

        passwordTextField = new JTextField();
        loggedOutPanel.add(passwordTextField);
        passwordTextField.setColumns(10);

        JButton btnLogIn = new JButton("Log In");
        btnLogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenter.authenticate(userTextField.getText(), passwordTextField.getText());
            }
        });
        loggedOutPanel.add(btnLogIn);

        loggedInPanel = new JPanel();
        loggedInPanel.setVisible(false);
        getContentPane().add(loggedInPanel);

        JButton add_article = new JButton("Add article");
        add_article.addActionListener(e -> showAddArticleView());
        loggedInPanel.add(add_article);

        JButton remove_article = new JButton("Remove selected article");
        remove_article.addActionListener(e -> removeArticleIfSelected());
        loggedInPanel.add(remove_article);

        JButton btnNewButton = new JButton("Log Out");
        btnNewButton.addActionListener(e -> presenter.logOut());
        loggedInPanel.add(btnNewButton);

        JScrollPane scrollPane = new JScrollPane();
        getContentPane().add(scrollPane);

        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        scrollPane.setViewportView(table);
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    showArticleAtIndex(getTableRow(mouseEvent));
                }
            }
        });
    }

    private void removeArticleIfSelected() {
        int selected = table.getSelectedRow();
        if (selected >= 0)
            presenter.deleteArticle(articles.get(selected)[0]);
    }

    private int getTableRow(MouseEvent mouseEvent) {
        JTable table = (JTable) mouseEvent.getSource();
        Point point = mouseEvent.getPoint();
        return table.rowAtPoint(point);
    }

    public void setAuthenticated(boolean auth) {
        loggedIn = auth;
        loggedInPanel.setVisible(auth);
        loggedOutPanel.setVisible(!auth);
        if (articleView != null)
            articleView.setVisible(false);
    }

    protected void showAddArticleView() {
        if (articleView != null) {
            articleView.setVisible(false);
            articleView.dispose();
        }
        articleView = new ArticleView(this, loggedIn);
        articleView.setVisible(true);
    }

    protected void showArticleAtIndex(int row) {
        if (articleView != null) {
            articleView.setVisible(false);
            articleView.dispose();
        }
        articleView = new ArticleView(this, loggedIn);
        articleView.setArticleInformation(articles.get(row));
        articleView.setVisible(true);
    }

    public void setArticles(List<String[]> articles) {
        DefaultTableModel dtm = new DefaultTableModel() {

            private static final long serialVersionUID = 5245348397698764465L;

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        dtm.addColumn("Title");
        dtm.addColumn("Abstract");
        dtm.addColumn("Author");
        this.articles = articles;
        for (String[] a : articles) {
            String[] attributes = new String[a.length - 1];
            for (int i = 1; i < a.length - 1; i++) {
                attributes[i - 1] = a[i];
            }
            dtm.addRow(attributes);
        }
        table.setModel(dtm);
    }

    public void saveArticle(String id, String title, String abstr, String author, String body) {
        if (id.equals("new"))
            presenter.addArticle(title, abstr, author, body);
        else
            presenter.updateArticle(id, title, abstr, author, body);
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
