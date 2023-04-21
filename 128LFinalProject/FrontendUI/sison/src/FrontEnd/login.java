package FrontEnd;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class login extends JFrame implements ActionListener{
		JFrame frame;
		JLabel username;
		JLabel usernameIcon, passwordIcon;
		JLabel shieldIcon , cart , possign, softwaresign;
		JTextField username1;
		JPasswordField password1;
		JPanel panel;
		JButton showbutton, loginButton;
		
		
	posUI pos = new posUI();	
		
	login(){
		
		pos.frame.setVisible(false);
		
		
		this.setTitle("POS SYSTEM");
		this.setSize(600,400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(new Color (51,153,255));
		
		
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(290,0,300,400);
		panel.setBackground(new Color(192,192,192));
		
		shieldIcon = new JLabel();
		shieldIcon.setBounds(100,30,100,100);
		shieldIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pic/shield.png")));
		shieldIcon.setBackground(new Color(128,128,128));
		
		usernameIcon = new JLabel();
		usernameIcon.setBounds(20,150,25,20);
		usernameIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pic/userimage.png")));
		usernameIcon.setBackground(new Color(128,128,128));
		
		username1 = new JTextField();
		username1.setBounds(60,140,180,30);
		username1.setBackground(new Color(0,255,255));
		
		passwordIcon = new JLabel();
		passwordIcon.setBounds(20,200,25,20);
		passwordIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pic/password.png")));
		passwordIcon.setBackground(new Color(128,128,128));
		
		password1 = new JPasswordField();
		password1.setBounds(60,190,180,30);
		password1.setBackground(new Color(0,255,255));
		
		showbutton = new JButton("SHOW");
		showbutton.setBounds(60,230,90,20);
		showbutton.setBackground(new Color(0,255,255));
		showbutton.setFocusable(false);
		showbutton.addActionListener(this);
		
		loginButton = new JButton("Log In");
		loginButton.setBounds(150,230,90,20);
		loginButton.setBackground(new Color(0,255,255));
		loginButton.setFocusable(false);
		loginButton.addActionListener(this);
		
		cart = new JLabel();
		cart.setBounds(20,30,200,200);
		cart.setBackground(new Color(128,128,128));
		cart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pic/cart.png")));
		
		possign = new JLabel();
		possign.setText("Log In");
		possign.setBounds(120,30,400,200);
		possign.setFont(new Font("MV Boli", Font.BOLD,24));
		possign.setForeground(new Color(0,255,255));
		
		softwaresign = new JLabel();
		softwaresign.setText("Page");
		softwaresign.setBounds(120,50,400,200);
		softwaresign.setFont(new Font("MV Boli", Font.BOLD,24));
		softwaresign.setForeground(new Color(0,255,255));
		
		panel.add(softwaresign);
		panel.add(possign);
		panel.add(cart);
		panel.add(loginButton);
		this.add(showbutton);
		this.add(password1);
		this.add(passwordIcon);
		this.add(username1);
		this.add(usernameIcon);
		this.add(shieldIcon);
		this.add(panel);
		
		this.setVisible(true);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==loginButton) {
		    String username = "happylim";
		    String password = "happylim";
                    String username2 = "sison";
                    String password2 = "sison";
                            
		    
		    
		    String inputUsername = username1.getText();
		    
			
			String inputPassword = password1.getText();
		    
		    if (inputUsername.equals(username)||inputUsername.equals(username2) && inputPassword.equals(password)||inputPassword.equals(password2)) {
		       this.setVisible(false);
                       pos.frame.setVisible(true);
		       
		} 
		    else {
		        // The user has entered invalid credentials
		         JOptionPane.showMessageDialog(null, "Your username & password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                     
		    }
		}
		if(e.getSource()==showbutton) {
			 char echoChar = password1.getEchoChar();
			    if (echoChar == 0) {
			        password1.setEchoChar('\u2022');  
			        showbutton.setText("Show Password");
			    } else {
			        password1.setEchoChar((char) 0); 
			        showbutton.setText("Hide Password");
			    }
		}
		
		
	}

	
}
