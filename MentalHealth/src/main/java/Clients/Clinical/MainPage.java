package Clients.Clinical;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;

import Clients.Client;
import Objects.Doctor;
import Objects.Drug;
import Objects.Patient;
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

public class MainPage {

	private JFrame frame;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable drug_table;
	private JTable patient_table;

	/**
	 * Launch the application.
	 */
	public static void openWindow(Client client, Doctor doctor) {
		Query q = new Query(Viewpoint.Clinical);
		q.setFunction("see");
		client.send(q);
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
	 * @param doctor
	 * @param client
	 */
	public MainPage(Client client, Doctor doctor) {
		initialize(client, doctor);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Client client, Doctor doctor) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1181, 523);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clinical Viewpoint");

		contentPane = new JPanel();
		contentPane.setBackground(Tools.CustomColours.White());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		Query q = new Query(Viewpoint.Clinical);
		q.setFunction("getDrugs");
		client.send(q);

		Integer size = new Gson().fromJson(client.read(), Integer.class);
		System.out.println(size);
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
		btnNewButton_1.setForeground(Tools.CustomColours.White());
		btnNewButton_1.setBackground(Tools.CustomColours.Red());
		btnNewButton_1.setBounds(33, 410, 80, 23);
		contentPane.add(btnNewButton_1);

		JLabel welcome_label = new JLabel("Welcome Dr. " + doctor.getName().charAt(0) + ". " + doctor.getSurname());
		welcome_label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		welcome_label.setBounds(33, 22, 221, 39);
		contentPane.add(welcome_label);

		JLabel drug_label = new JLabel("Drugs List");
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
		System.out.println(size);
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
				PatientView.openWindow(selectedPatient);

			}
		});
		/*******************************************************/

		JLabel patient_list_label = new JLabel("Patient List");
		patient_list_label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		patient_list_label.setBounds(968, 120, 98, 23);
		contentPane.add(patient_list_label);
	}
}
