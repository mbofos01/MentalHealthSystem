package UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

@SuppressWarnings("unused")
public class Test {
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test window = new Test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.getContentPane().setBackground(CustomColours.White());
		frame.getContentPane().setLayout(null);

		final JButton btnNewButton = new JButton("New button");
		btnNewButton.setForeground(CustomColours.Green());
		btnNewButton.setBackground(CustomColours.Indigo());

		final JLabel lblNewLabel = new JLabel("Changing dark theme colours by Apple HCI");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setForeground(CustomColours.Blue());
		lblNewLabel.setBounds(53, 25, 345, 34);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomColours.changeTheme();
				btnNewButton.setForeground(CustomColours.White());
				lblNewLabel.setForeground(CustomColours.Blue());
				btnNewButton.setForeground(CustomColours.Green());
				if (CustomColours.isDark())
					frame.getContentPane().setBackground(CustomColours.Black());
				else
					frame.getContentPane().setBackground(CustomColours.White());
			}
		});
		btnNewButton.setBounds(139, 120, 161, 50);
		frame.getContentPane().add(btnNewButton);

		frame.getContentPane().add(lblNewLabel);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
