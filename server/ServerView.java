package server;

import javax.swing.*;

import java.awt.*;

public class ServerView {
    JFrame frame;
    JPanel panel;
    JLabel title;
    JLabel [][] detail_grid;
    JTable table;
    JTextArea text_area;
    String [][] data = {
        {"FB", "", ""},
        {"VRTU", "", ""},
        {"MSFT", "", ""},
        {"GOOGL", "", ""},
        {"YHOO", "", ""},
        {"XLNX", "", ""},
        {"TSLA", "", ""},
        {"TXN", "", ""}
    };

    public ServerView(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Auction Server");
        frame.setPreferredSize(new Dimension(1000,700));
        frame.setLayout(new BorderLayout());

        String [] column_names = {"Symbol", "Company Name", "Current Price"};

        title = new JLabel("Auctions", JLabel.CENTER);
        title.setFont(new Font("",Font.BOLD,50));
        frame.add(title, BorderLayout.NORTH);

        table = new JTable(data, column_names);
        table.setRowHeight(50);
        table.setFillsViewportHeight(true);
        JScrollPane table_sp = new JScrollPane(table);
        frame.add(table_sp, BorderLayout.CENTER);

        text_area = new JTextArea(11,10);

        JScrollPane text_sp = new JScrollPane(text_area);
        frame.add(text_sp, BorderLayout.SOUTH);

        

        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public String getRowSymbol(int row){
        return data[row][0];
    }
    public void setRow(Company company, int row){
        data[row][1] = company.getName();
        data[row][2] = "$" + Float.toString(company.getCurrentPrice());
        frame.repaint();
    }
    public void addNewEntry(String bid_detail){
        text_area.append(bid_detail);
        text_area.setCaretPosition(text_area.getDocument().getLength());
    }
}