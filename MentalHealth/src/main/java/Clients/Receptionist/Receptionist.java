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
import Tools.Query;
import Tools.Viewpoint;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

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
		frmReceptionist.setResizable(false);
		frmReceptionist.setTitle("Receptionist");
		frmReceptionist.getContentPane().setBackground(Color.WHITE);
		frmReceptionist.setBounds(100, 100, 639, 531);
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
			data[index][0] = p.getPatient_id() + "";
			data[index][1] = p.getName();
			data[index][2] = p.getSurname();
			data[index][3] = p.getTelephone();
			data[index][4] = p.getEmail();
			index++;
		}

		DefaultTableModel modelPatient = new DefaultTableModel(data, col);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(63, 301, 518, 128);
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
		scrollPane1.setBounds(20, 43, 584, 153);
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
				int a = 0;
				for (Appointment p1 : appointments) {
					if (appointments.get(p).getAppoint_id() == p1.getAppoint_id()) {
						a = appointments.get(p).getAppoint_id();
					}
				}
				Create_Appointment.main(client, model, 1, a);
			}
		});

		JButton btnPresc = new JButton("Generate Last Perscription for selected patient");
		btnPresc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Query q = new Query(Viewpoint.Receptionist);
				q.setFunction("showAllTreatments");
				int p = tblPatient.getSelectedRow();
				int a = 0;
				for (Patient p1 : patient_list) {
					if (patient_list.get(p).getPatient_id() == p1.getPatient_id()) {
						a = patient_list.get(p).getPatient_id();
					}
				}
				q.addArgument(a + "");
				client.send(q);
				Treatment app = new Treatment();
				app = new Gson().fromJson(client.read(), Treatment.class);
				// app.setAccepted(true);
				//
				if (app.isAccepted()) {
					q = new Query(Viewpoint.Receptionist);
					q.setFunction("addTreatment");
					q.addArgument(new Gson().toJson(app));
					client.send(q);
				} else {
					JOptionPane.showMessageDialog(frmReceptionist.getContentPane(),
							"Prescription cannot be done, because treatment has not been accepted yet.");
				}
			}
		});
		btnPresc.setBackground(new Color(0, 204, 102));
		btnPresc.setBounds(63, 439, 309, 34);
		frmReceptionist.getContentPane().add(btnPresc);

		txtSearch = new JTextField();
		txtSearch.setText("Search patient by name...");
		txtSearch.setBounds(63, 264, 162, 19);
		frmReceptionist.getContentPane().add(txtSearch);
		txtSearch.setColumns(10);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String search = txtSearch.getText();
				Query q = new Query(Viewpoint.Receptionist);
				q.setFunction("getPatientsSearch");
				q.addArgument(search);
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
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(63, 301, 518, 128);
				frmReceptionist.getContentPane().add(scrollPane);
				tblPatient = new JTable();
				scrollPane.setViewportView(tblPatient);
				tblPatient = new JTable(modelPatient);
				scrollPane.setViewportView(tblPatient);
				tblPatient.setDefaultEditor(Object.class, null);
			}
		});
		btnSearch.setBackground(new Color(0, 153, 255));
		btnSearch.setBounds(496, 263, 85, 21);
		frmReceptionist.getContentPane().add(btnSearch);

		JButton btnNewAppointment = new JButton("New Appointment");
		btnNewAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmReceptionist.dispose();
				Create_Appointment.main(client, model, -1, 0);
			}
		});
		btnNewAppointment.setBackground(new Color(0, 204, 102));
		btnNewAppointment.setBounds(427, 206, 177, 21);
		frmReceptionist.getContentPane().add(btnNewAppointment);

		JLabel lblNewLabel = new JLabel("Appointments:");
		lblNewLabel.setBounds(20, 20, 98, 13);
		frmReceptionist.getContentPane().add(lblNewLabel);

		JLabel lblPatients = new JLabel("Patients:");
		lblPatients.setBounds(63, 241, 98, 13);
		frmReceptionist.getContentPane().add(lblPatients);

		JButton btnReset = new JButton("Reset Table");
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

				DefaultTableModel modelPatient = new DefaultTableModel(data, col);
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(63, 301, 518, 128);
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
		btnReset.setBounds(473, 444, 108, 21);
		frmReceptionist.getContentPane().add(btnReset);

		JButton btnNewButton_1 = new JButton("Log Out");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmReceptionist.dispose();
				ReceptionistLogin.main(null);
			}
		});
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(new Color(215, 0, 21));
		btnNewButton_1.setBounds(524, 10, 80, 23);
		frmReceptionist.getContentPane().add(btnNewButton_1);
	}
}
