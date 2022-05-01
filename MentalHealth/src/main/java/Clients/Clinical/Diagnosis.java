package Clients.Clinical;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.google.gson.Gson;

import Clients.Client;
import Objects.Allergy;
import Objects.Condition;
import Objects.Doctor;
import Objects.Drug;
import Objects.Patient;
import Objects.PatientRecord;
import Objects.Treatment;
import Tools.Clock;
import Tools.CustomColours;
import Tools.Query;
import Tools.Viewpoint;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextPane;
import java.awt.Color;

public class Diagnosis {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void openWindow(Client client, Doctor doctor, Patient patient, ArrayList<Drug> drugs,
			PatientRecord last) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Diagnosis window = new Diagnosis(client, doctor, patient, drugs, last);
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
	public Diagnosis(Client client, Doctor doctor, Patient patient, ArrayList<Drug> drugs, PatientRecord last) {
		initialize(client, doctor, patient, drugs, last);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Client client, Doctor doctor, Patient patient, ArrayList<Drug> drugs, PatientRecord last) {
		System.out.println(last.getTreatment_id());
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(CustomColours.interChangableWhite());
		frame.setTitle("Create a diagnosis");
		frame.setBounds(100, 100, 602, 603);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel condition = new JLabel("Condition:");
		condition.setForeground(CustomColours.interChangableBlack());
		condition.setFont(new Font("Tahoma", Font.PLAIN, 18));
		condition.setBounds(49, 180, 97, 22);
		frame.getContentPane().add(condition);

		JComboBox<String> conditionDropdown = new JComboBox<String>();

		Query q = new Query(Viewpoint.Clinical);
		q.setFunction("getConditions");
		client.send(q);
		Integer size = new Gson().fromJson(client.read(), Integer.class);
		ArrayList<Condition> conds = new ArrayList<>();

		for (int i = 0; i < size; i++)
			conds.add(new Gson().fromJson(client.read(), Condition.class));

		int selected = -1;

		for (int i = 0; i < size; i++) {
			Condition con = conds.get(i);
			conditionDropdown.addItem(con.getName());
			if (con.getCondition_id() == last.getCondition_id())
				selected = i;
		}

		conditionDropdown.setSelectedIndex(selected);
		conditionDropdown.setBounds(156, 183, 245, 22);
		frame.getContentPane().add(conditionDropdown);

		JLabel Treatment = new JLabel("Treatment:");
		Treatment.setForeground(CustomColours.interChangableBlack());
		Treatment.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Treatment.setBounds(49, 237, 97, 22);
		frame.getContentPane().add(Treatment);

		JComboBox<String> treatmentDropdown = new JComboBox<String>();
		treatmentDropdown.addItem("No Treatment");
		System.out.println("TR: " + last.getTreatment_id());
		int selected_drug = 0;
		for (int i = 0; i < size; i++) {
			Drug dr = drugs.get(i);
			treatmentDropdown.addItem(dr.getCommercial_name());
			if (last.getTreatment() != null && last.getTreatment().getDrug_id() == dr.getId()) {
				System.out.println("selectd: " + i + " " + drugs.get(i).getCommercial_name());
				selected_drug = i + 1;
			}
		}
		treatmentDropdown.setSelectedIndex(selected_drug);
		treatmentDropdown.setBounds(156, 240, 245, 22);
		frame.getContentPane().add(treatmentDropdown);

		JLabel name = new JLabel(patient.getName() + " " + patient.getSurname());
		name.setFont(new Font("Tahoma", Font.PLAIN, 29));
		if (last.isThreat())
			name.setForeground(CustomColours.Red());
		else
			name.setForeground(CustomColours.interChangableBlack());
		name.setBounds(49, 28, 245, 61);
		frame.getContentPane().add(name);

		JCheckBox selfharm = new JCheckBox("Self Harm");
		if (last.isSelf_harm())
			selfharm.setSelected(true);
		selfharm.setFont(new Font("Tahoma", Font.PLAIN, 15));
		selfharm.setForeground(CustomColours.interChangableBlack());
		selfharm.setBackground(CustomColours.interChangableWhite());
		selfharm.setHorizontalAlignment(SwingConstants.CENTER);
		selfharm.setBounds(392, 53, 156, 23);
		frame.getContentPane().add(selfharm);

		JCheckBox threat = new JCheckBox("Possible Threat");
		if (last.isThreat())
			threat.setSelected(true);
		threat.setFont(new Font("Tahoma", Font.PLAIN, 15));
		threat.setHorizontalAlignment(SwingConstants.CENTER);
		threat.setForeground(CustomColours.interChangableBlack());
		threat.setBackground(CustomColours.interChangableWhite());
		threat.setBounds(410, 92, 156, 23);
		frame.getContentPane().add(threat);

		JLabel abuseLabel = new JLabel("Abuse:");
		abuseLabel.setForeground(CustomColours.interChangableBlack());
		abuseLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		abuseLabel.setBounds(49, 290, 97, 22);
		frame.getContentPane().add(abuseLabel);

		JComboBox<String> abuseDropdown = new JComboBox<String>();

		abuseDropdown.addItem("None");
		abuseDropdown.addItem("Overdose");
		abuseDropdown.addItem("Underdose");
		if (last.isOverdose() && !last.isUnderdose())
			abuseDropdown.setSelectedIndex(1);
		else if (!last.isOverdose() && last.isUnderdose())
			abuseDropdown.setSelectedIndex(2);
		else
			abuseDropdown.setSelectedIndex(0);
		abuseDropdown.setBounds(156, 293, 245, 22);
		frame.getContentPane().add(abuseDropdown);

		JSpinner doseCounter = new JSpinner();
		int start;
		if (last.getTreatment_id() == -1)
			start = 0;
		else
			start = last.getTreatment().getDose();
		doseCounter.setModel(new SpinnerNumberModel(start, 0, 7, 1));
		doseCounter.setBounds(411, 241, 40, 20);
		frame.getContentPane().add(doseCounter);

		JLabel commentLabel = new JLabel("Comments:");
		commentLabel.setForeground(CustomColours.interChangableBlack());
		commentLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		commentLabel.setBounds(49, 357, 97, 22);
		frame.getContentPane().add(commentLabel);

		JTextPane textPane = new JTextPane();
		Border border = BorderFactory.createLineBorder(CustomColours.interChangableBlack(), 1);

		// set the border of this component
		textPane.setBorder(border);
		textPane.setBounds(156, 357, 245, 101);
		frame.getContentPane().add(textPane);

		JButton submitBtn = new JButton("Submit");
		q = new Query(Viewpoint.Clinical);
		q.setFunction("getPatientAllergies");
		q.addArgument("" + patient.getPatient_id());
		client.send(q);
		size = new Gson().fromJson(client.read(), Integer.class);
		ArrayList<Allergy> getPatientAllergies = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			getPatientAllergies.add(new Gson().fromJson(client.read(), Allergy.class));
			// System.out.println(getPatientAllergies.get(i).getDrug_id());
		}

		submitBtn.setForeground(CustomColours.interChangableWhite());
		submitBtn.setBackground(CustomColours.Green());
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index2 = treatmentDropdown.getSelectedIndex() - 1;
				boolean warned = false, addTreament = false;
				Integer treat_id = -1;
				if (index2 != -1) {
					for (Allergy allergy_drug : getPatientAllergies) {
						// System.out.println(drugs.get(index2).getId() + " " +
						// allergy_drug.getDrug_id());
						if (drugs.get(index2).getId() == allergy_drug.getDrug_id()) {

							// System.out.println("ALLERGY");
							int option = JOptionPane.showConfirmDialog(null,
									"          " + patient.getName() + " " + patient.getSurname() + " is allergic to "
											+ drugs.get(index2).getCommercial_name()
											+ "! \n Do you real want to prescribe them "
											+ drugs.get(index2).getCommercial_name() + "?",
									"Allergy", JOptionPane.YES_NO_OPTION);

							if (option == 0) { // The ISSUE is here
								warned = true;
							} else {
								return;
							}
						}
					}
					Treatment treat = new Treatment();
					treat.setComments(textPane.getText());
					treat.setDoctor_id(doctor.getId());
					treat.setDose(Integer.parseInt(doseCounter.getValue().toString()));
					treat.setDrug_id(drugs.get(index2).getId());
					treat.setWarning(warned);
					treat.setPatient_id(patient.getPatient_id());
					/**
					 * insert treatment and fetch its id
					 */
					Query addTreatmentQuery = new Query(Viewpoint.Clinical);
					addTreatmentQuery.setFunction("addTreatment");
					addTreatmentQuery.addArgument(new Gson().toJson(treat));
					client.send(addTreatmentQuery);
					treat_id = new Gson().fromJson(client.read(), Integer.class);
					addTreament = true;

				} else {
					addTreament = false;
				}

				int index = conditionDropdown.getSelectedIndex();
				PatientRecord record = new PatientRecord();
				record.setCondition_id(index);
				record.setDoctor_id(doctor.getId());
				record.setDate(Clock.currentSQLTime());
				record.setPatient_id(patient.getPatient_id());
				record.setThreat(threat.isSelected());
				record.setSelf_harm(selfharm.isSelected());

				int index3 = abuseDropdown.getSelectedIndex();
				if (index3 == 0) {
					record.setOverdose(false);
					record.setUnderdose(false);
				} else if (index3 == 1) {
					record.setOverdose(true);
					record.setUnderdose(false);
				} else if (index3 == 2) {
					record.setOverdose(false);
					record.setUnderdose(true);
				}

				if (addTreament) {
					System.out.println("New treatment id: " + treat_id);
					record.setTreatment_id(treat_id);
				} else {
					record.setTreatment_id(-1);
				}
				Query addRecordQuery = new Query(Viewpoint.Clinical);
				addRecordQuery.setFunction("addRecord");
				addRecordQuery.addArgument(new Gson().toJson(record));
				client.send(addRecordQuery);
				frame.dispose();
				PatientView.openWindow(client, doctor, patient, drugs);
			}
		});
		submitBtn.setBounds(424, 492, 117, 29);
		frame.getContentPane().add(submitBtn);

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				PatientView.openWindow(client, doctor, patient, drugs);
			}
		});
		cancel.setForeground(CustomColours.interChangableWhite());
		cancel.setBackground(CustomColours.Red());
		cancel.setBounds(49, 495, 117, 29);
		frame.getContentPane().add(cancel);

	}
}
