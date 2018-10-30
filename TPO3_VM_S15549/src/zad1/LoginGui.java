package zad1;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import java.awt.Color;

public class LoginGui extends JPanel {
	private JTextField textField;
	private JFrame frame;

	public LoginGui() {
		setLayout(null);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		textField.setBounds(10, 37, 324, 35);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblWrongNickname = new JLabel("Wrong Nickname");
		lblWrongNickname.setForeground(new Color(240,240,240,240));
		lblWrongNickname.setBounds(121, 98, 118, 13);
		add(lblWrongNickname);

		JLabel lblNewLabel = new JLabel("Size of your nickname must be more ");
		lblNewLabel.setBounds(10, 70, 324, 13);
		add(lblNewLabel);

		JLabel lblThanSymbols = new JLabel("than 3 symbols");
		lblThanSymbols.setBounds(10, 82, 324, 13);
		add(lblThanSymbols);

		JLabel lblNickname = new JLabel("Nickname:");
		lblNickname.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNickname.setBounds(10, 24, 69, 13);
		add(lblNickname);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().length() > 3) {
					new Thread(() -> {
						new Client(textField.getText());
					}).start();
					frame.dispose();
				}
				else {
					textField.setText("");
					lblWrongNickname.setForeground(Color.RED);
					
				}
				
			}
		});

		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLogin.setBounds(249, 93, 85, 21);
		add(btnLogin);

		frame = new JFrame("LoginWindow");
		frame.setSize(360, 175);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		
		
		frame.setVisible(true);
		frame.setResizable(false);
	}

}
