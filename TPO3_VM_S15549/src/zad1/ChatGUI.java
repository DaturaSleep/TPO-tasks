package zad1;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class ChatGUI {
	private JFrame frame;
	private Client client;
	private JTextField formattedTextField;
	private JTextArea textArea;
	private boolean allowSending = false;

	public ChatGUI(Client client) {
		this.client = client;
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 572, 496);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 10, 537, 402);
		frame.getContentPane().add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Lucida Sans Typewriter", Font.ITALIC, 14));
		scrollPane.setViewportView(textArea);

		JButton btnNewButton = new JButton("SEND");
		btnNewButton.setFont(new Font("Magneto", Font.ITALIC, 20));
		btnNewButton.setBounds(420, 422, 126, 27);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (formattedTextField.getText().length() >= 1)
					client.sendMessage(formattedTextField.getText());

				formattedTextField.setText("");
				allowSending = false;

			}

		});

		formattedTextField = new JTextField(10);
		formattedTextField.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					if (formattedTextField.getText().length() >= 1)
						client.sendMessage(formattedTextField.getText());

					formattedTextField.setText("");
					allowSending = false;
				}

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
		formattedTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (formattedTextField.getText().length() > 1)
					allowSending = true;

			}
		});
		formattedTextField.setBounds(10, 422, 403, 27);
		frame.getContentPane().add(formattedTextField);
		frame.setVisible(true);
	}

	public void addText(String text) {
		this.textArea.append(text + "\n");
		this.textArea.setCaretPosition(textArea.getDocument().getLength());
		textArea.update(textArea.getGraphics());

	}
}
