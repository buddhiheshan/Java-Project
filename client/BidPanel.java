package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//suppress warnings
@SuppressWarnings("serial")

public class BidPanel extends JPanel{
    JLabel user_label, symbol_label, company_label, currentbid_label, newbid_label, user_value, symbol_value, company_value, currentbid_value;
    JTextField newbid_textbox;
    JTextArea bid_logger;
    JButton bid_button, quit_button;


    

    public BidPanel(){
        super();
        this.setBackground(new Color(204, 236, 239));
        this.setPreferredSize(new Dimension(600,600));
        this.setLayout(null);


        user_label = new JLabel("User :");
		user_label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		user_label.setBounds(28, 65, 87, 24);
		this.add(user_label);
		
		symbol_label = new JLabel("Symbol :");
		symbol_label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		symbol_label.setBounds(28, 99, 76, 24);
		this.add(symbol_label);
		
		company_label = new JLabel("Company :");
		company_label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		company_label.setBounds(28, 133, 87, 24);
		this.add(company_label);
		
		currentbid_label = new JLabel("Current Bid :");
		currentbid_label.setFont(new Font("Tahoma", Font.PLAIN, 17));
		currentbid_label.setBounds(28, 167, 104, 24);
		this.add(currentbid_label);
		
		newbid_label = new JLabel("New Bid :");
		newbid_label.setFont(new Font("Tahoma", Font.PLAIN, 17));
		newbid_label.setBounds(28, 201, 92, 30);
		this.add(newbid_label);

		newbid_textbox = new JTextField();
		newbid_textbox.setFont(new Font("Tahoma", Font.PLAIN, 17));
		newbid_textbox.setBounds(130, 201, 142, 30);
		this.add(newbid_textbox);
		// textField.setColumns(10);
		
		bid_button = new JButton("Place Bid");
		bid_button.setFont(new Font("Tahoma", Font.BOLD, 15));
		bid_button.setBounds(319, 201, 163, 30);
		this.add(bid_button);
		
		user_value = new JLabel("label1");
		user_value.setFont(new Font("Tahoma", Font.PLAIN, 15));
		user_value.setBounds(130, 65, 175, 24);
		this.add(user_value);
		
		symbol_value = new JLabel("label2");
		symbol_value.setHorizontalAlignment(SwingConstants.LEFT);
		symbol_value.setFont(new Font("Tahoma", Font.PLAIN, 15));
		symbol_value.setBounds(130, 99, 175, 24);
		this.add(symbol_value);
		
		company_value = new JLabel("label3");
		company_value.setFont(new Font("Tahoma", Font.PLAIN, 15));
		company_value.setBounds(130, 133, 175, 24);
		this.add(company_value);
		
		currentbid_value = new JLabel("label4");
		currentbid_value.setFont(new Font("Tahoma", Font.PLAIN, 15));
		currentbid_value.setBounds(130, 167, 175, 24);
		this.add(currentbid_value);
		
		bid_logger = new JTextArea();
		bid_logger.setFont(new Font("Monospaced", Font.PLAIN, 14));
		bid_logger.setBounds(28, 273, 526, 171);
		this.add(bid_logger);
		
		quit_button = new JButton("Quit");
		quit_button.setFont(new Font("Tahoma", Font.BOLD, 17));
		quit_button.setBounds(218, 476, 87, 36);
		this.add(quit_button);
    }

    //Method to add action listener to button
    public void addButtonListener(ActionListener listenForButton, JButton button){
        button.addActionListener(listenForButton);           
    } 
}