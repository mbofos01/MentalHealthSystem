package Clients.MedicalRecordsStaff;

import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.JLabel;

import Clients.Client;
import Objects.RecordsStaff;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This application windows is used (as of now) just for displaying some info
 * about the record management staff.
 * 
 * @author Ioanna Theophilou
 *
 */
public class WelcomeRecordStaff {

	private JFrame frame;

	/**
	 * Launch the application.
	 * 
	 * @param client Client object must be passed in order to communicate with the
	 *               server
	 * @param model  RecordStaff object for the person logged in
	 */
	public static void openWindow(final Client client, final RecordsStaff model) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeRecordStaff window = new WelcomeRecordStaff(client, model);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @param client Client object must be passed in order to communicate with the
	 *               server
	 * @param model  RecordStaff object for the person logged in
	 */
	public WelcomeRecordStaff(Client client, RecordsStaff model) {
		initialize(client, model);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @param client Client object must be passed in order to communicate with the
	 *               server
	 * @param model  RecordStaff object for the person logged in
	 */
	private void initialize(Client client, RecordsStaff model) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setForeground(Color.RED);
		frame.setBounds(100, 100, 957, 442);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel(model.getName());
		lblNewLabel.setFont(new Font("Segoe Script", Font.PLAIN, 29));
		lblNewLabel.setBounds(333, 22, 174, 48);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel(model.getSurname());
		lblNewLabel_1.setFont(new Font("Segoe Script", Font.PLAIN, 29));
		lblNewLabel_1.setBounds(517, 22, 195, 48);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_4 = new JLabel("Welcome");
		lblNewLabel_4.setFont(new Font("Segoe Script", Font.BOLD, 32));
		lblNewLabel_4.setBounds(152, 10, 243, 70);
		frame.getContentPane().add(lblNewLabel_4);

		JButton btnNewButton = new JButton("Transactions");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Transactions.openWindow(client);
			}
		});
		btnNewButton.setBackground(SystemColor.inactiveCaption);
		btnNewButton.setFont(new Font("Segoe Script", Font.PLAIN, 20));
		btnNewButton.setBounds(82, 161, 183, 151);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("View Requests");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Requests.openWindow(client);
			}
		});
		btnNewButton_1.setFont(new Font("Segoe Script", Font.PLAIN, 20));
		btnNewButton_1.setBackground(SystemColor.inactiveCaption);
		btnNewButton_1.setBounds(349, 161, 259, 151);
		frame.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_1_1 = new JButton("Assign Patients");
		btnNewButton_1_1.setFont(new Font("Segoe Script", Font.PLAIN, 16));
		btnNewButton_1_1.setBackground(SystemColor.inactiveCaption);
		btnNewButton_1_1.setBounds(676, 162, 183, 151);
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Assign.openWindow(client);
			}
		});
		frame.getContentPane().add(btnNewButton_1_1);

	}
}