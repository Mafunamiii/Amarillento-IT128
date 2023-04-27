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
                setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Resource/LightLogoBG.png")));
		pos.frame.setVisible(false);
		
		
		this.setTitle("GrabPanda Inventory System");
		this.setSize(600,400); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(new Color (68,81,115));
		
		
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(290,0,300,400);
		panel.setBackground(new Color(205,208,246));
		
		shieldIcon = new JLabel("Log in");
		shieldIcon.setBounds(110,30,500,100);
		shieldIcon.setFont(new Font("Roboto", Font.BOLD,24));
		shieldIcon.setForeground(new Color(205,208,246));
                
                ImageIcon icon = new ImageIcon(getClass().getResource("/Resource/DarkmodeComplete.png"));
                Image img = icon.getImage();
                Image resizedImg = img.getScaledInstance(190, 70, Image.SCALE_SMOOTH);
		
		usernameIcon = new JLabel();
		usernameIcon.setBounds(20,150,25,20);
		usernameIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/userimage.png")));
		usernameIcon.setBackground(new Color(205,208,246));
		
		username1 = new JTextField();
		username1.setBounds(60,140,180,30);
		username1.setBackground(new Color(205,208,246));
		
		passwordIcon = new JLabel();
		passwordIcon.setBounds(20,200,25,20);
		passwordIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/password.png")));
		passwordIcon.setBackground(new Color(205,208,246));
		
		password1 = new JPasswordField();
		password1.setBounds(60,190,180,30);
		password1.setBackground(new Color(205,208,246));
		
		showbutton = new JButton("SHOW");
		showbutton.setBounds(60,230,90,20);
		showbutton.setBackground(new Color(150, 178,252));
		showbutton.setFocusable(false);
		showbutton.addActionListener(this);
		
		loginButton = new JButton("Log In");
		loginButton.setBounds(150,230,90,20);
		loginButton.setBackground(new Color(150, 178,252));
		loginButton.setFocusable(false);
		loginButton.addActionListener(this);
		
		cart = new JLabel();
		cart.setBounds(50,30,200,200);
		cart.setBackground(new Color(128,128,128));
		cart.setIcon(new ImageIcon(resizedImg));
		
		possign = new JLabel();
		possign.setText("Log In");
		possign.setBounds(120,30,400,200);
		possign.setFont(new Font("MV Boli", Font.BOLD,24));
		possign.setForeground(new Color(0,255,255));
		

		


		panel.add(cart);
		panel.add(loginButton);
		this.add(showbutton);
		this.add(password1);
		this.add(passwordIcon);
		this.add(username1);
		this.add(usernameIcon);
		this.add(panel);
		this.add(shieldIcon);
		this.setVisible(true);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==loginButton) {
		    String username = "1";
		    String password = "1";
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
