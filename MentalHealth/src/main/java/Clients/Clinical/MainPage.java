package Clients.Clinical;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;

import Clients.Client;
import Objects.Appointment;
import Objects.Doctor;
import Objects.Drug;
import Objects.Patient;
import Tools.Clock;
import Tools.CustomColours;
import Tools.Query;
import Tools.Viewpoint;

import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * This application window is used to provide the doctors a main page to select
 * their actions
 * 
 * @author Michail Panagiotis Bofos
 *
 */
public class MainPage {
	/**
	 * JFrame that creates the window
	 */
	private JFrame frame;
	/**
	 * JPanel for the table to be inserted
	 */
	private JPanel contentPane;
	/**
	 * JTable for the drugs to be stored
	 */
	private JTable drug_table;
	/**
	 * JTable for the doctors patient to be stored
	 */
	private JTable patient_table;
	/**
	 * JTable for the appointments to be shown
	 */
	private JTable appointTable;

	/**
	 * Launch the application.
	 * 
	 * @param client Client object for server client communication
	 * @param doctor Doctor object - the one how is logged in
	 */
	public static void openWindow(Client client, Doctor doctor) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage window = new MainPage(client, doctor);
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
	 * @param client Client object for server client communication
	 * @param doctor Doctor object - the one how is logged in
	 */
	public MainPage(Client client, Doctor doctor) {
		initialize(client, doctor);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @param client Client object for server client communication
	 * @param doctor Doctor object - the one how is logged in
	 */
	private void initialize(Client client, Doctor doctor) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1181, 523);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clinical Viewpoint");

		contentPane = new JPanel();
		contentPane.setBackground(CustomColours.interChangableWhite());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		Query q = new Query(Viewpoint.Clinical);
		q.setFunction("getDrugs");
		client.send(q);

		Integer size = new Gson().fromJson(client.read(), Integer.class);
		// System.out.println(size);
		ArrayList<Drug> drug_list = new ArrayList<Drug>();
		for (int i = 0; i < size; i++)
			drug_list.add(new Gson().fromJson(client.read(), Drug.class));
		// SHOW
		String col[] = { "Name", "Commercial Name" };
		int index = 0;
		String data[][] = new String[drug_list.size()][col.length];
		for (Drug dru : drug_list) {
			data[index][0] = dru.getName();
			data[index][1] = dru.getCommercial_name();
			index++;
		}

		DefaultTableModel model = new DefaultTableModel(data, col);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 154, 249, 128);
		contentPane.add(scrollPane);
		drug_table = new JTable();
		scrollPane.setViewportView(drug_table);
		drug_table = new JTable(model);
		scrollPane.setViewportView(drug_table);
		drug_table.setDefaultEditor(Object.class, null);
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		drug_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int p = drug_table.getSelectedRow();
				showMessageDialog(null,
						drug_list.get(p).getCommercial_name() + " side effect: " + drug_list.get(p).getSide_effect(),
						"Possible Side Effects", JOptionPane.INFORMATION_MESSAGE);

			}
		});

		JButton btnNewButton_1 = new JButton("Log Out");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				ClinicalLogin.main(null);

			}
		});
		btnNewButton_1.setForeground(Tools.CustomColours.interChangableWhite());
		btnNewButton_1.setBackground(Tools.CustomColours.Red());
		btnNewButton_1.setBounds(33, 410, 80, 23);
		contentPane.add(btnNewButton_1);

		JLabel welcome_label = new JLabel("Welcome Dr. " + doctor.getName().charAt(0) + ". " + doctor.getSurname());
		welcome_label.setForeground(CustomColours.interChangableBlack());
		welcome_label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		welcome_label.setBounds(33, 22, 331, 39);
		contentPane.add(welcome_label);

		JLabel drug_label = new JLabel("Drugs List");
		drug_label.setForeground(CustomColours.interChangableBlack());
		drug_label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		drug_label.setBounds(115, 120, 91, 23);
		contentPane.add(drug_label);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(889, 154, 249, 128);
		contentPane.add(scrollPane_1);
		/********************************************************/

		q = new Query(Viewpoint.Clinical);
		q.setFunction("getDoctorsPatient");
		q.addArgument("" + doctor.getId());
		client.send(q);

		size = new Gson().fromJson(client.read(), Integer.class);
		// System.out.println(size);
		ArrayList<Patient> patient_list = new ArrayList<Patient>();
		for (int i = 0; i < size; i++)
			patient_list.add(new Gson().fromJson(client.read(), Patient.class));
		// SHOW
		String col2[] = { "Name", "Surname", "Birth Day" };
		index = 0;
		String data2[][] = new String[patient_list.size()][col2.length];
		for (Patient pat : patient_list) {
			data2[index][0] = pat.getName();
			data2[index][1] = pat.getSurname();
			data2[index][2] = pat.getDate();
			index++;
		}

		DefaultTableModel model2 = new DefaultTableModel(data2, col2);
		contentPane.add(scrollPane_1);
		patient_table = new JTable();
		scrollPane_1.setViewportView(patient_table);
		patient_table = new JTable(model2);
		scrollPane_1.setViewportView(patient_table);
		patient_table.setDefaultEditor(Object.class, null);

		patient_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int p = patient_table.getSelectedRow();
				Patient selectedPatient = patient_list.get(p);
				PatientView.openWindow(client, doctor, selectedPatient, drug_list);

			}
		});
		/*******************************************************/

		JLabel patient_list_label = new JLabel("Patient List");
		patient_list_label.setForeground(CustomColours.interChangableBlack());
		patient_list_label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		patient_list_label.setBounds(968, 120, 98, 23);
		contentPane.add(patient_list_label);
		String str = null;
		if (CustomColours.isDark())
			str = "light ";
		else
			str = "dark ";
		JButton darkTheme = new JButton(str + "theme");
		darkTheme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomColours.changeTheme();
				frame.dispose();
				openWindow(client, doctor);

			}
		});
		darkTheme.setBackground(CustomColours.Indigo());
		darkTheme.setForeground(CustomColours.interChangableWhite());

		darkTheme.setBounds(1035, 11, 120, 23);
		contentPane.add(darkTheme);

		JLabel title = new JLabel("Today's Appointments");
		title.setForeground(CustomColours.interChangableBlack());
		title.setFont(new Font("Tahoma", Font.PLAIN, 30));
		title.setBounds(445, 35, 322, 88);
		contentPane.add(title);
		/***********************************************************/
		Query getApps = new Query(Viewpoint.Clinical);
		getApps.setFunction("getAppointmentsOfADoctorsDay");
		getApps.addArgument("" + doctor.getId());
		getApps.addArgument(Clock.currentSQLTime());
		client.send(getApps);

		Integer size_ap = new Gson().fromJson(client.read(), Integer.class);
		// System.out.println(size);
		ArrayList<Appointment> list = new ArrayList<Appointment>();
		for (int i = 0; i < size_ap; i++)
			list.add(new Gson().fromJson(client.read(), Appointment.class));
		String col3[] = { "Patient", "Time", "Type", "Attended", "Updated" };
		int index3 = 0;
		String data3[][] = new String[list.size()][col3.length];
		for (Appointment ap : list) {
			data3[index3][0] = getPatientFullNameByID(ap.getPatient_id(), patient_list);
			data3[index3][1] = ap.getTime();
			data3[index3][2] = ap.getType();
			if (ap.isAttended())
				data3[index3][3] = "YES";
			else
				data3[index3][3] = "NO";

			if (ap.getRecord_id() == -1)
				data3[index3][4] = "NO";
			else
				data3[index3][4] = "YES";

			System.out.println(data3[index3][3]);
			index3++;
		}

		DefaultTableModel model3 = new DefaultTableModel(data3, col3);
		JScrollPane scrollPane3 = new JScrollPane();
		scrollPane3.setBounds(356, 133, 472, 286);
		contentPane.add(scrollPane3);
		appointTable = new JTable();
		scrollPane3.setViewportView(appointTable);
		appointTable = new JTable(model3);
		scrollPane3.setViewportView(appointTable);
		appointTable.setDefaultEditor(Object.class, null);
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		appointTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int p = appointTable.getSelectedRow();
				Patient selectedPatient = null;
				for (Patient s : patient_list)
					if (s.getPatient_id() == list.get(p).getPatient_id())
						selectedPatient = s;

				PatientView.openWindow(client, doctor, selectedPatient, drug_list);
			}
		});
	}

	private String getPatientFullNameByID(int patient_id, ArrayList<Patient> patient_list) {
		for (Patient s : patient_list)
			if (s.getPatient_id() == patient_id)
				return s.getName() + " " + s.getSurname();
		return "null";
	}
}
