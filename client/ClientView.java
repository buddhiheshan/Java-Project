package client;

import javax.swing.*;
import java.awt.*;


public class ClientView {
    JFrame frame;
    JPanel panel_container;
    UserPanel name_panel;
    SymbolPanel symbol_panel;
    BidPanel bid_panel;
    CardLayout card_layout = new CardLayout();



    public ClientView(){
        frame = new JFrame("Auction Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel_container = new JPanel();
        panel_container.setPreferredSize(new Dimension(600,600));
        panel_container.setLayout(card_layout);

        name_panel = new UserPanel();
        symbol_panel = new SymbolPanel();
        bid_panel = new BidPanel();


        panel_container.add(name_panel, "username");
        panel_container.add(symbol_panel, "symbol");
        panel_container.add(bid_panel, "bid");

        card_layout.show(panel_container, "username");
        frame.getContentPane().add(panel_container);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
    
    
}