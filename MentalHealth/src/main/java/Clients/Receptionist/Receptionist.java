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
import Objects.Appointment;
import Objects.Patient;
import Objects.ReceptionistObj;
import Objects.Treatment;
import Tools.Clock;
import Tools.CreatePDF;
import Tools.Query;
import Tools.Viewpoint;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

/**
 * Main Interface for Health Service Staff. As stated in the basic structure
 * each viewpoint must have a client object as an argument.
 *
 * @author Demetra Hadjicosti
 *
 */
public class Receptionist {
	/**
	 * The frame (window of the application)
	 */
	private JFrame frmReceptionist;
	/**
	 * Table that displays the appointments
	 */
	private JTable tblAppointment;
	/**
	 * Search field for searching patients
	 */
	private JTextField txtSearch;
	/**
	 * Table that displays the patients
	 */
	private JTable tblPatient;
	/**
	 * Scroll panel for patient table
	 */
	private JScrollPane scrollPane;

	/**
	 * Launch the Application
	 *
	 * @param client Client object for server client communication
	 * @param model  Receptionist instance
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
	 * Create the Application
	 *
	 * @param client Client object for server client communication
	 * @param model  Receptionist instance
	 */
	public Receptionist(Client client, ReceptionistObj model) {
		initialize(client, model);
	}

	/**
	 * Initialize the contents of the frame
	 *
	 * @param client Client object for server client communication
	 * @param model  Receptionist instance
	 */
	private void initialize(Client client, ReceptionistObj model) {
		frmReceptionist = new JFrame();
		frmReceptionist.setResizable(false);
		frmReceptionist.setTitle("Receptionist");
		frmReceptionist.getContentPane().setBackground(Color.WHITE);
		frmReceptionist.setBounds(100, 100, 646, 596);
		frmReceptionist.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmReceptionist.getContentPane().setLayout(null);

		Query q = new Query(Viewpoint.Receptionist);
		q.setFunction("getPatients");
		client.send(q);

		Integer size = new Gson().fromJson(client.read(), Integer.class);
		ArrayList<Patient> patient_list = new ArrayList<Patient>();
		for (int i = 0; i < size; i++)
			patient_list.add(new Gson().fromJson(client.read(), Patient.class));
		// SHOW
		String col[] = { "Patient ID", "Name", "Surname", "Telephone", "Email" };
		int index = 0;
		String data[][] = new String[patient_list.size()][col.length];
		for (Patient p : patient_list) {
			data[index][0] = p.getPatient_id() + "";
			data[index][1] = p.getName();
			data[index][2] = p.getSurname();
			data[index][3] = p.getTelephone();
			data[index][4] = p.getEmail();
			index++;
		}

		DefaultTableModel modelPatient = new DefaultTableModel(data, col);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(63, 322, 518, 128);
		frmReceptionist.getContentPane().add(scrollPane);
		tblPatient = new JTable();
		scrollPane.setViewportView(tblPatient);
		tblPatient = new JTable(modelPatient);
		scrollPane.setViewportView(tblPatient);
		tblPatient.setDefaultEditor(Object.class, null);

		q = new Query(Viewpoint.Receptionist);
		q.setFunction("getAppointments");
		q.addArgument(model.getClinic_id() + "");
		client.send(q);
		size = new Gson().fromJson(client.read(), Integer.class);
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		for (int i = 0; i < size; i++)
			appointments.add(new Gson().fromJson(client.read(), Appointment.class));
		// SHOW
		String col1[] = { "App.ID", "Name", "Surname", "Date", "Time", "Type", "Attended", "Receptionist" };
		index = 0;
		String data1[][] = new String[appointments.size()][col1.length];
		for (Appointment p : appointments) {
			data1[index][0] = p.getAppoint_id() + "";
			String name = "", surname = "";
			for (Patient p1 : patient_list) {
				if (p.getPatient_id() == p1.getPatient_id()) {
					name = p1.getName();
					surname = p1.getSurname();
				}
			}
			data1[index][1] = name;
			data1[index][2] = surname;
			data1[index][3] = p.getDate();
			data1[index][4] = p.getTime();
			data1[index][5] = p.getType();
			if (p.isAttended() == false)
				data1[index][6] = "No";
			else
				data1[index][6] = "Yes";
			data1[index][7] = p.getReceptionist_id() + "";
			index++;
		}

		DefaultTableModel modelAppointment = new DefaultTableModel(data1, col1);
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(20, 54, 584, 153);
		frmReceptionist.getContentPane().add(scrollPane1);
		tblAppointment = new JTable();
		scrollPane1.setViewportView(tblAppointment);
		tblAppointment = new JTable(modelAppointment);
		scrollPane1.setViewportView(tblAppointment);
		tblAppointment.setDefaultEditor(Object.class, null);

		tblAppointment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int p = tblAppointment.getSelectedRow();
				frmReceptionist.dispose();
				int a = 0, patr_id = -1;
				for (Appointment p1 : appointments) {
					if (appointments.get(p).getAppoint_id() == p1.getAppoint_id()) {
						a = appointments.get(p).getAppoint_id();
						patr_id = p1.getPatient_id();
					}
				}
				Create_Appointment.main(client, model, 1, a, patr_id);
			}
		});

		JComboBox<String> cmb_Patient = new JComboBox<String>();
		cmb_Patient.setBounds(245, 471, 165, 21);
		frmReceptionist.getContentPane().add(cmb_Patient);

		q = new Query(Viewpoint.Receptionist);
		q.setFunction("getPatients");
		client.send(q);
		size = new Gson().fromJson(client.read(), Integer.class);
		patient_list = new ArrayList<Patient>();
		for (int i = 0; i < size; i++) {
			Patient toAdd = new Gson().fromJson(client.read(), Patient.class);
			if (toAdd.isAlive())
				patient_list.add(toAdd);
		}
		index = 0;
		data = new String[patient_list.size()][5];
		for (Patient p1 : patient_list) {
			data[index][0] = p1.getPatient_id() + "";
			data[index][1] = p1.getName();
			data[index][2] = p1.getSurname();
			data[index][3] = p1.getTelephone();
			data[index][4] = p1.getEmail();
			String add = data[index][0] + " " + data[index][1] + " " + data[index][2];
			cmb_Patient.insertItemAt(add, index);
			index++;
		}
		cmb_Patient.setSelectedIndex(0);

		JButton btnPresc = new JButton("Generate Last Perscription for selected patient");
		btnPresc.setForeground(new Color(255, 255, 255));
		btnPresc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Query q = new Query(Viewpoint.Receptionist);
				q.setFunction("showAllTreatments");
				q.addArgument(cmb_Patient.getSelectedItem().toString().split(" ")[0]);
				client.send(q);
				Treatment app = new Gson().fromJson(client.read(), Treatment.class);
				app.setDate(Clock.currentSQLTime());
				if (app.equals(null)) {
					JOptionPane.showMessageDialog(frmReceptionist.getContentPane(),
							"Prescription cannot be done, no previous prescription is recorder.");
				} else {
					if (app.isAccepted()) {
						q = new Query(Viewpoint.Receptionist);
						q.setFunction("addTreatment");
						q.addArgument(new Gson().toJson(app));
						client.send(q);
						JOptionPane.showMessageDialog(frmReceptionist.getContentPane(), "Prescription repeated.");
					} else
						JOptionPane.showMessageDialog(frmReceptionist.getContentPane(),
								"Prescription cannot be done, because treatment has not been accepted yet.");
				}
			}
		});
		btnPresc.setBackground(new Color(204, 0, 51));
		btnPresc.setBounds(162, 502, 334, 34);
		frmReceptionist.getContentPane().add(btnPresc);

		txtSearch = new JTextField();
		txtSearch.setText("Search patient by name...");
		txtSearch.setBounds(63, 293, 162, 19);
		frmReceptionist.getContentPane().add(txtSearch);
		txtSearch.setColumns(10);

		JButton btnSearch = new JButton("Search");
		btnSearch.setForeground(new Color(255, 255, 255));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmReceptionist.getContentPane().remove(scrollPane);
				frmReceptionist.repaint();

				String search = txtSearch.getText();
				Query q = new Query(Viewpoint.Receptionist);
				q.setFunction("getPatientsSearch");
				q.addArgument(search);
				client.send(q);

				Integer size = new Gson().fromJson(client.read(), Integer.class);
				ArrayList<Patient> patient_list = new ArrayList<Patient>();
				for (int i = 0; i < size; i++)
					patient_list.add(new Gson().fromJson(client.read(), Patient.class)); // SHOW
				String col[] = { "Patient ID", "Name", "Surname", "Telephone", "Email" };
				int index = 0;
				String data[][] = new String[patient_list.size()][col.length];
				for (Patient p : patient_list) {
					data[index][0] = p.getPatient_id() + "";
					data[index][1] = p.getName();
					data[index][2] = p.getSurname();
					data[index][3] = p.getTelephone();
					data[index][4] = p.getEmail();
					index++;
				}

				DefaultTableModel modelPatient = new DefaultTableModel(data, col);
				scrollPane = new JScrollPane();
				scrollPane.setBounds(63, 322, 518, 128);
				frmReceptionist.getContentPane().add(scrollPane);
				tblPatient = new JTable();
				scrollPane.setViewportView(tblPatient);
				tblPatient = new JTable(modelPatient);
				scrollPane.setViewportView(tblPatient);
				tblPatient.setDefaultEditor(Object.class, null);

			}
		});
		btnSearch.setBackground(new Color(0, 153, 255));
		btnSearch.setBounds(235, 291, 85, 21);
		frmReceptionist.getContentPane().add(btnSearch);

		JButton btnNewAppointment = new JButton("New Appointment");
		btnNewAppointment.setForeground(new Color(255, 255, 255));
		btnNewAppointment.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				frmReceptionist.dispose();
				Create_Appointment.main(client, model, -1, 0, -1);
			}
		});
		btnNewAppointment.setBackground(new Color(0, 204, 102));
		btnNewAppointment.setBounds(427, 217, 177, 21);
		frmReceptionist.getContentPane().add(btnNewAppointment);

		JLabel lblNewLabel = new JLabel("Appointments:");
		lblNewLabel.setBounds(20, 31, 98, 13);
		frmReceptionist.getContentPane().add(lblNewLabel);

		JLabel lblPatients = new JLabel("Patients:");
		lblPatients.setBounds(63, 270, 98, 13);
		frmReceptionist.getContentPane().add(lblPatients);

		JButton btnReset = new JButton("Reset Table");
		btnReset.setForeground(new Color(255, 255, 255));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Query q = new Query(Viewpoint.Receptionist);
				q.setFunction("getPatients");
				client.send(q);

				Integer size = new Gson().fromJson(client.read(), Integer.class);
				ArrayList<Patient> patient_list = new ArrayList<Patient>();
				for (int i = 0; i < size; i++)
					patient_list.add(new Gson().fromJson(client.read(), Patient.class));
				// SHOW
				String col[] = { "Patient ID", "Name", "Surname", "Telephone", "Email" };
				int index = 0;
				String data[][] = new String[patient_list.size()][col.length];
				for (Patient p : patient_list) {
					data[index][0] = p.getPatient_id() + "";
					data[index][1] = p.getName();
					data[index][2] = p.getSurname();
					data[index][3] = p.getTelephone();
					data[index][4] = p.getEmail();
					index++;
				}
				frmReceptionist.getContentPane().remove(scrollPane);
				frmReceptionist.repaint();

				DefaultTableModel modelPatient = new DefaultTableModel(data, col);
				scrollPane = new JScrollPane();
				scrollPane.setBounds(63, 322, 518, 128);

				frmReceptionist.getContentPane().add(scrollPane);
				tblPatient = new JTable();
				scrollPane.setViewportView(tblPatient);
				tblPatient = new JTable(modelPatient);
				scrollPane.setViewportView(tblPatient);
				tblPatient.setDefaultEditor(Object.class, null);
				txtSearch.setText("Search patient by name...");
			}
		});
		btnReset.setBackground(new Color(0, 153, 255));
		btnReset.setBounds(473, 291, 108, 21);
		frmReceptionist.getContentPane().add(btnReset);

		// LOGOUT
		JButton btnNewButton_1 = new JButton("Log Out");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmReceptionist.dispose();
				ReceptionistLogin.main(null);
			}
		});
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(new Color(204, 0, 51));
		btnNewButton_1.setBounds(524, 10, 80, 23);
		frmReceptionist.getContentPane().add(btnNewButton_1);

		JButton btnNewAppointment_1 = new JButton("Generate Report of missed Appointments");
		btnNewAppointment_1.setForeground(new Color(255, 255, 255));
		btnNewAppointment_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Patient> missed = new ArrayList<>();
				for (Appointment ap : appointments) {
					if (ap.getDate().equals(Clock.currentSQLTime()) && ap.isAttended() == false) {
						Query pat = new Query(Viewpoint.Receptionist);
						pat.setFunction("getPatientByID");
						pat.addArgument("" + ap.getPatient_id());
						client.send(pat);
						Patient pate = new Gson().fromJson(client.read(), Patient.class);
						missed.add(pate);
					}
				}
				CreatePDF.createMissed(missed);
			}
		});
		btnNewAppointment_1.setBackground(new Color(204, 0, 51));
		btnNewAppointment_1.setBounds(20, 217, 270, 21);
		frmReceptionist.getContentPane().add(btnNewAppointment_1);

		JButton addPatient = new JButton("New Patient");
		addPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreatePatient.openWindow(client, model);
				frmReceptionist.dispose();

			}
		});
		addPatient.setForeground(Color.WHITE);
		addPatient.setBackground(new Color(0, 153, 255));
		addPatient.setBounds(335, 291, 128, 21);
		frmReceptionist.getContentPane().add(addPatient);

	}
}
