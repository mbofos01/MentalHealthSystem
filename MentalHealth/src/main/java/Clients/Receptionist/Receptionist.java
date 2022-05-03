package Clients.Receptionist;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;

import Clients.Client;
import Objects.ReceptionistObj;

import javax.swing.JLabel;

public class Receptionist {

	private JFrame frmReceptionist;
	private JTable table;
	private JTextField txtSearchPatientBy;

	/**
	 * Launch the application.
	 */
	public static void openWin(final Client client, final ReceptionistObj model) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Receptionist window = new Receptionist(client, model);
					window.frmReceptionist.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Receptionist(Client client, ReceptionistObj model) {
		initialize(client, model);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Client client, ReceptionistObj model) {
		frmReceptionist = new JFrame();
		frmReceptionist.setTitle("Receptionist");
		frmReceptionist.getContentPane().setBackground(Color.WHITE);
		frmReceptionist.setBounds(100, 100, 639, 560);
		frmReceptionist.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmReceptionist.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(86, 43, 476, 153);
		frmReceptionist.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Generate Last Perscription for selected patient");
		btnNewButton.setBackground(new Color(0, 204, 102));
		btnNewButton.setBounds(86, 459, 251, 34);
		frmReceptionist.getContentPane().add(btnNewButton);
		
		txtSearchPatientBy = new JTextField();
		txtSearchPatientBy.setText("Search patient by name");
		txtSearchPatientBy.setBounds(86, 264, 162, 19);
		frmReceptionist.getContentPane().add(txtSearchPatientBy);
		txtSearchPatientBy.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Search");
		btnNewButton_1.setBackground(new Color(0, 153, 255));
		btnNewButton_1.setBounds(477, 263, 85, 21);
		frmReceptionist.getContentPane().add(btnNewButton_1);
		
		JButton btnNewAppointment = new JButton("New Appointment");
		btnNewAppointment.setBackground(new Color(0, 204, 102));
		btnNewAppointment.setBounds(430, 205, 132, 21);
		frmReceptionist.getContentPane().add(btnNewAppointment);
		
		JLabel lblNewLabel = new JLabel("Appointments:");
		lblNewLabel.setBounds(86, 20, 98, 13);
		frmReceptionist.getContentPane().add(lblNewLabel);
		
		JLabel lblPatients = new JLabel("Patients:");
		lblPatients.setBounds(86, 241, 98, 13);
		frmReceptionist.getContentPane().add(lblPatients);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(86, 296, 476, 153);
		frmReceptionist.getContentPane().add(scrollPane_1);
	}
}
