package Clients.Receptionist;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;

import Clients.Client;
import Objects.Patient;
import Objects.ReceptionistObj;
import Tools.Query;
import Tools.Viewpoint;

import javax.swing.JLabel;

public class Receptionist {

	private JFrame frmReceptionist;
	private JTable tblAppointment;
	private JTextField txtSearch;
	private JTable tblPatient;

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
		
		Query q = new Query(Viewpoint.Receptionist);
		q.setFunction("getPatients");
		client.send(q);

		Integer size = new Gson().fromJson(client.read(), Integer.class);
		// System.out.println(size);
		ArrayList<Patient> patient_list = new ArrayList<Patient>();
		for (int i = 0; i < size; i++)
			patient_list.add(new Gson().fromJson(client.read(), Patient.class));
		// SHOW
		String col[] = { "Patient ID", "Name", "Surname", "Telephone", "Email" };
		int index = 0;
		String data[][] = new String[patient_list.size()][col.length];
		for (Patient p : patient_list) {
			data[index][0] = p.getPatient_id()+ "";
			data[index][1] = p.getName();
			data[index][2] = p.getSurname();
			data[index][3] = p.getTelephone();
			data[index][4] = p.getEmail();
			index++;
		}

		DefaultTableModel modelPatient = new DefaultTableModel(data, col);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 154, 249, 128);
		frmReceptionist.getContentPane().add(scrollPane);
		tblPatient = new JTable();
		scrollPane.setViewportView(tblPatient);
		tblPatient = new JTable(modelPatient);
		scrollPane.setViewportView(tblPatient);
		tblPatient.setDefaultEditor(Object.class, null);
		
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(86, 43, 476, 153);
		frmReceptionist.getContentPane().add(scrollPane1);
		
		tblAppointment = new JTable();
		scrollPane1.setViewportView(tblAppointment);
		
		JButton btnPresc = new JButton("Generate Last Perscription for selected patient");
		btnPresc.setBackground(new Color(0, 204, 102));
		btnPresc.setBounds(86, 459, 251, 34);
		frmReceptionist.getContentPane().add(btnPresc);
		
		txtSearch = new JTextField();
		txtSearch.setText("Search patient by name");
		txtSearch.setBounds(86, 264, 162, 19);
		frmReceptionist.getContentPane().add(txtSearch);
		txtSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBackground(new Color(0, 153, 255));
		btnSearch.setBounds(477, 263, 85, 21);
		frmReceptionist.getContentPane().add(btnSearch);
		
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
		
		
		JButton btnReset = new JButton("Reset Table");
		btnReset.setBackground(new Color(0, 153, 255));
		btnReset.setBounds(454, 466, 108, 21);
		frmReceptionist.getContentPane().add(btnReset);
		
		JButton btnNewButton_1 = new JButton("Log Out");
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(new Color(215, 0, 21));
		btnNewButton_1.setBounds(535, 10, 80, 23);
		frmReceptionist.getContentPane().add(btnNewButton_1);
	}
	public static void main(String[] args) {
		
	}
}
