package Clients.Clinical;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;

import Clients.Client;
import Objects.Allergy;
import Objects.Drug;
import Objects.Patient;
import Tools.Clock;
import Tools.CustomColours;
import Tools.Query;
import Tools.Viewpoint;

import java.awt.Window.Type;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AllergyWindow {

	private JFrame frame;
	private JPanel contentPane;
	private JTable getAllergy;
	private JTable addAllergy;
	private DefaultTableModel new_allergies;
	private DefaultTableModel allergy_list;
	private JButton btnNewButton;
	private JButton cancel;

	/**
	 * Launch the application.
	 * 
	 * @param Clientpatient
	 * @param client
	 */
	public static void openWindow(Client client, Patient patient, ArrayList<Drug> drugs) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AllergyWindow window = new AllergyWindow(client, patient, drugs);
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
	public AllergyWindow(Client client, Patient patient, ArrayList<Drug> drugs) {
		initialize(client, patient, drugs);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Client client, Patient patient, ArrayList<Drug> general_list) {
		frame = new JFrame();
		frame.setTitle("Allergies Management");
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.setType(Type.POPUP);
		frame.getContentPane().setBackground(CustomColours.interChangableWhite());
		frame.getContentPane().setLayout(null);
		frame.setBounds(100, 100, 585, 604);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(CustomColours.interChangableWhite());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		/******************************************************/
		Query q = new Query(Viewpoint.Clinical);
		q.setFunction("getPatientAllergies");
		q.addArgument("" + patient.getPatient_id());
		client.send(q);
		int size = new Gson().fromJson(client.read(), Integer.class);
		ArrayList<Allergy> active_list = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			active_list.add(new Gson().fromJson(client.read(), Allergy.class));
		}

		ArrayList<Integer> active_drug_list = new ArrayList<>();
		ArrayList<Allergy> new_list = new ArrayList<>();
		ArrayList<Drug> non_selected = new ArrayList<>();
		for (Allergy a : active_list) {
			active_drug_list.add(a.getDrug_id());
		}

		for (Drug under : general_list) {
			if (!exists(active_drug_list, under.getId())) {
				non_selected.add(under);
			}
		}

		/******************************************************/
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(63, 102, 460, 163);
		contentPane.add(scrollPane_1);
		String headers[] = { "Name", "Commercial Name" };
		String data2[][] = new String[non_selected.size()][2];
		int index = 0;
		for (Drug non : non_selected) {
			data2[index][0] = non.getName();
			data2[index][1] = non.getCommercial_name();
			index++;
		}
		allergy_list = new DefaultTableModel(data2, headers);
		contentPane.add(scrollPane_1);
		getAllergy = new JTable();
		scrollPane_1.setViewportView(getAllergy);
		getAllergy = new JTable(allergy_list);
		scrollPane_1.setViewportView(getAllergy);
		getAllergy.setDefaultEditor(Object.class, null);

		getAllergy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int p = getAllergy.getSelectedRow();
				boolean flag = true;
				for (Allergy al : new_list)
					if (al.getDrug_id() == non_selected.get(p).getId()) {
						flag = false;
						return;
					}
				if (flag) {
					new_allergies.addRow(data2[p]);
					Allergy toBeAdded = new Allergy();
					toBeAdded.setAccepted(false);
					toBeAdded.setDate(Clock.currentSQLTime());
					toBeAdded.setDrug_id(non_selected.get(p).getId());
					toBeAdded.setPatient_id(patient.getPatient_id());
					new_list.add(toBeAdded);
				}
			}
		});
		/******************************************************/
		/******************************************************/
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(63, 299, 460, 163);
		contentPane.add(scrollPane_2);
		String data3[][] = new String[active_list.size()][2];
		index = 0;
		for (Allergy exis : active_list) {
			data3[index][0] = getAllergyDrugFromID(general_list, exis).getName();
			data3[index][1] = getAllergyDrugFromID(general_list, exis).getCommercial_name();
			index++;
		}
		new_allergies = new DefaultTableModel(data3, headers);
		contentPane.add(scrollPane_2);
		addAllergy = new JTable();
		scrollPane_2.setViewportView(addAllergy);
		addAllergy = new JTable(new_allergies);
		scrollPane_2.setViewportView(addAllergy);

		btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Allergy inse : new_list) {
					Query insert = new Query(Viewpoint.Clinical);
					insert.setFunction("insertAllergy");
					insert.addArgument(new Gson().toJson(inse));
					client.send(insert);
				}
				frame.dispose();
			}
		});
		btnNewButton.setForeground(CustomColours.interChangableWhite());
		btnNewButton.setBackground(CustomColours.Green());
		btnNewButton.setBounds(308, 490, 166, 37);
		contentPane.add(btnNewButton);

		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		cancel.setBackground(CustomColours.Red());
		cancel.setForeground(CustomColours.interChangableWhite());
		cancel.setBounds(95, 490, 166, 37);
		contentPane.add(cancel);

		JLabel lblNewLabel = new JLabel("Allergies Management");
		lblNewLabel.setForeground(CustomColours.interChangableBlack());
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel.setBounds(146, 23, 328, 56);
		contentPane.add(lblNewLabel);
		addAllergy.setDefaultEditor(Object.class, null);

		/******************************************************/
	}

	private boolean exists(ArrayList<Integer> non_selected, int id) {
		for (Integer s : non_selected)
			if (s == id)
				return true;
		return false;
	}

	private Drug getAllergyDrugFromID(ArrayList<Drug> general_list, Allergy exis) {
		for (Drug d : general_list)
			if (exis.getDrug_id() == d.getId())
				return d;
		return null;
	}
}
