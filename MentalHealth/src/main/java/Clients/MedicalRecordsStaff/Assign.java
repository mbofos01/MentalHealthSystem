package Clients.MedicalRecordsStaff;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.google.gson.Gson;

import Clients.Client;
import Objects.Doctor;
import Objects.Patient;
import Tools.Query;
import Tools.Viewpoint;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * To assign the patient with the doctors.
 * 
 * @author Ioanna Theophilou
 *
 */
public class Assign {
	/**
	 * The frame
	 */
	private JFrame frmAssignPatientTo;
	/**
	 * The content pane
	 */
	@SuppressWarnings("unused")
	private JPanel contentPane;
	/**
	 * The table used bellow
	 */
	public static JTable table;
	/**
	 * The label used bellow
	 */
	@SuppressWarnings("unused")
	private JLabel lblNewLabel;
	/**
	 * The client used bellow
	 */
	@SuppressWarnings("unused")
	private Client client;
	/**
	 * The index of the selected item of one of the dropdown lists.
	 */
	@SuppressWarnings("unused")
	private int selected = -1;
	/**
	 * The index of the selected item of the other dropdown list.
	 */
	@SuppressWarnings("unused")
	private int selected1 = -1;

	/**
	 * Initialize the contents of the frame.
	 *
	 * @param client Client Object
	 */
	private void initialize(Client client) {

		frmAssignPatientTo = new JFrame();
		frmAssignPatientTo.setTitle("Assign patient to Doctors");
		frmAssignPatientTo.setBounds(100, 100, 578, 332);
		frmAssignPatientTo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmAssignPatientTo.getContentPane().setLayout(null);

		JComboBox<String> patientDropdown = new JComboBox<String>();
		patientDropdown.setBounds(30, 133, 235, 30);

		Query q = new Query(Viewpoint.MedicalRecords);
		q.setFunction("getPatients");
		client.send(q);

		Integer size = new Gson().fromJson(client.read(), Integer.class);
		ArrayList<Patient> p = new ArrayList<>();

		for (int i = 0; i < size; i++)
			p.add(new Gson().fromJson(client.read(), Patient.class));

		for (int i = 0; i < size; i++) {
			Patient patient = p.get(i);
			patientDropdown.addItem(patient.getName() + " " + patient.getSurname());
			selected = i;
		}

		patientDropdown.setSelectedIndex(0);
		frmAssignPatientTo.getContentPane().add(patientDropdown);
		JComboBox<String> doctorDropdown = new JComboBox<String>();
		doctorDropdown.setBounds(303, 133, 235, 30);

		Query q1 = new Query(Viewpoint.MedicalRecords);
		q1.setFunction("getDoctors");
		client.send(q1);

		Integer size1 = new Gson().fromJson(client.read(), Integer.class);
		ArrayList<Doctor> doc = new ArrayList<>();

		for (int i = 0; i < size1; i++)
			doc.add(new Gson().fromJson(client.read(), Doctor.class));

		for (int i = 0; i < size1; i++) {
			Doctor doctor = doc.get(i);
			doctorDropdown.addItem(doctor.getName() + " " + doctor.getSurname());
			selected1 = i;
		}

		doctorDropdown.setSelectedIndex(0);
		frmAssignPatientTo.getContentPane().add(doctorDropdown);
		JButton btnNewButton = new JButton("Assign");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Query insert = new Query(Viewpoint.MedicalRecords);
				insert.setFunction("insertDoctorPatientRelationship");
				insert.addArgument("" + p.get(patientDropdown.getSelectedIndex()).getPatient_id());
				insert.addArgument("" + doc.get(doctorDropdown.getSelectedIndex()).getId());
				client.send(insert);
				frmAssignPatientTo.dispose();
			}

		});
		btnNewButton.setBackground(new Color(50, 205, 50));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setBounds(360, 214, 100, 55);
		frmAssignPatientTo.getContentPane().add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("Assign Patient to Doctor");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel_1.setBounds(119, 10, 326, 79);
		frmAssignPatientTo.getContentPane().add(lblNewLabel_1);

	}

	/**
	 * The function to open this window.
	 * 
	 * @param client Client object
	 */
	public static void openWindow(Client client) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Assign window = new Assign(client);
					window.frmAssignPatientTo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor with client.
	 * 
	 * @param client Client object
	 */
	public Assign(Client client) {
		initialize(client);
	}
}
