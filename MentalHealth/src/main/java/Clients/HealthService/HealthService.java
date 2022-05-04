package Clients.HealthService;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

import Clients.Client;
import Objects.Clinic;
import Objects.Condition;
import Objects.Drug;
import Objects.HealthServ;
import Objects.Patient;
import Objects.ReportData;
import Tools.CreatePDF;
import Tools.Query;
import Tools.Viewpoint;

import javax.swing.JComboBox;
import javax.swing.JSeparator;

import com.google.gson.Gson;

public class HealthService {

	private JFrame frmHealthService;

	public static void openWindow(final Client client, final HealthServ model) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HealthService window = new HealthService(client, model);
					window.frmHealthService.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HealthService(Client client, HealthServ model) {
		initialize(client, model);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Client client, HealthServ model) {
		frmHealthService = new JFrame();
		frmHealthService.setResizable(false);
		frmHealthService.setTitle("Health Service");
		frmHealthService.getContentPane().setBackground(Color.WHITE);
		frmHealthService.getContentPane().setLayout(null);

		JButton btnWeekReport = new JButton("Generate Weekly Report");
		btnWeekReport.setBackground(new Color(51, 204, 102));
		btnWeekReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Query q1 = new Query(Viewpoint.HealthService);
				q1.setFunction("getClinics");
				client.send(q1);
				Integer size = new Gson().fromJson(client.read(), Integer.class);
				ArrayList<Clinic> clinics = new ArrayList<>();

				for (int i = 0; i < size; i++)
					clinics.add(new Gson().fromJson(client.read(), Clinic.class));

				for (int i = 0; i < size; i++) {
					Query q = new Query(Viewpoint.HealthService);
					q.setFunction("generateWeeklyReport");
					q.addArgument("" + i);
					client.send(q);
					ReportData rp = new Gson().fromJson(client.read(), ReportData.class);
					CreatePDF.createReport(rp);
				}
			}
		});
		btnWeekReport.setBounds(174, 67, 180, 21);
		frmHealthService.getContentPane().add(btnWeekReport);

		JLabel lblNewLabel = new JLabel("Select Condition and Treatment to Generate Report");
		lblNewLabel.setBounds(136, 122, 375, 13);
		frmHealthService.getContentPane().add(lblNewLabel);

		JComboBox<String> cmbCondition = new JComboBox<String>();

		Query q = new Query(Viewpoint.HealthService);
		q.setFunction("getConditions");
		client.send(q);
		Integer size = new Gson().fromJson(client.read(), Integer.class);
		ArrayList<Condition> conds = new ArrayList<>();

		for (int i = 0; i < size; i++)
			conds.add(new Gson().fromJson(client.read(), Condition.class));

		for (int i = 0; i < size; i++) {
			Condition con = conds.get(i);
			cmbCondition.addItem(con.getName());

		}

		cmbCondition.setSelectedIndex(0);
		cmbCondition.setBounds(60, 184, 174, 21);
		frmHealthService.getContentPane().add(cmbCondition);

		JComboBox<String> cmbTreatment = new JComboBox<String>();
		q = new Query(Viewpoint.HealthService);
		q.setFunction("getDrugs");
		client.send(q);

		size = new Gson().fromJson(client.read(), Integer.class);
		ArrayList<Drug> drug_list = new ArrayList<Drug>();
		for (int i = 0; i < size; i++) {
			drug_list.add(new Gson().fromJson(client.read(), Drug.class));
			cmbTreatment.addItem(drug_list.get(i).getCommercial_name());
		}

		cmbTreatment.setBounds(288, 184, 172, 21);
		frmHealthService.getContentPane().add(cmbTreatment);

		JButton btnReportPatient = new JButton("Generate Report with patients");
		btnReportPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indexCond = cmbCondition.getSelectedIndex();
				int indexTreat = cmbTreatment.getSelectedIndex();
				Query q = new Query(Viewpoint.HealthService);
				q.setFunction("getPatientsTreatmentCond");
				q.addArgument(""+ conds.get(indexCond).getCondition_id());
				q.addArgument(""+ drug_list.get(indexTreat).getId());
				client.send(q);
				int size = new Gson().fromJson(client.read(), Integer.class);
				ArrayList<Patient> patients = new ArrayList<Patient>();
				for (int i = 0; i < size; i++) {
					patients.add(new Gson().fromJson(client.read(), Patient.class));
				}
				CreatePDF.createPatientReport(patients, conds.get(indexCond).getName(), drug_list.get(indexTreat).getCommercial_name());
			}
		});
		btnReportPatient.setBackground(new Color(51, 204, 102));
		btnReportPatient.setBounds(136, 232, 256, 21);
		frmHealthService.getContentPane().add(btnReportPatient);

		JLabel lblNewLabel_1 = new JLabel("Condition");
		lblNewLabel_1.setBounds(141, 161, 71, 13);
		frmHealthService.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Treatment");
		lblNewLabel_1_1.setBounds(321, 161, 139, 13);
		frmHealthService.getContentPane().add(lblNewLabel_1_1);

		JSeparator separator = new JSeparator();
		separator.setBounds(131, 146, 276, 2);
		frmHealthService.getContentPane().add(separator);

		JButton btnNewButton_1 = new JButton("Log Out");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmHealthService.dispose();
				HealthServiceLogin.main(null);
			}
		});
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(new Color(215, 0, 21));
		btnNewButton_1.setBounds(391, 299, 97, 23);
		frmHealthService.getContentPane().add(btnNewButton_1);
		frmHealthService.setBackground(Color.WHITE);
		frmHealthService.setBounds(100, 100, 524, 381);
		frmHealthService.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
