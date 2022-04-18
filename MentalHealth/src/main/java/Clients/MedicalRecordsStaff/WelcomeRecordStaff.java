package Clients.MedicalRecordsStaff;

import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.JLabel;

import Clients.Client;
import Objects.RecordsStaff;

public class WelcomeRecordStaff {

	private JFrame frame;

	/**
	 * Launch the application.
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
	 */
	public WelcomeRecordStaff(Client client, RecordsStaff model) {
		initialize(client, model);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Client client, RecordsStaff model) {
		frame = new JFrame();
		frame.setBounds(100, 100, 697, 436);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel(model.getName());
		lblNewLabel.setBounds(69, 55, 110, 28);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel(model.getSurname());
		lblNewLabel_1.setBounds(249, 55, 117, 28);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel(model.getUsername());
		lblNewLabel_2.setBounds(448, 55, 110, 40);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel(model.getEmail());
		lblNewLabel_3.setBounds(69, 125, 172, 28);
		frame.getContentPane().add(lblNewLabel_3);

	}

}
