package Clients.Receptionist;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import com.google.gson.Gson;

import Clients.Client;
import Objects.Patient;
import Objects.ReceptionistObj;
import Tools.Query;
import Tools.Viewpoint;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This application windows is used to create a new patient for our system.
 * 
 * @author Michail Panagiotis Bofos
 *
 */
public class CreatePatient {

	private JFrame frame;
	private JTextField nameTxt;
	private JTextField surnameTxt;
	private JTextField birthTxt;
	private JTextField emailTxt;
	private JTextField telTxt;

	/**
	 * Launch the application.
	 *
	 * @param client Client object
	 * @param model  ReceptionistObj object
	 */
	public static void openWindow(Client client, ReceptionistObj model) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreatePatient window = new CreatePatient(client, model);
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
	 * @param client Client object
	 * @param model  ReceptionistObj object
	 */
	public CreatePatient(Client client, ReceptionistObj model) {
		initialize(client, model);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @param client Client object
	 * @param model  ReceptionistObj object
	 */
	private void initialize(Client client, ReceptionistObj model) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Create New Patient");
		frame.setBounds(100, 100, 440, 569);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("New Patient Form");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel.setBounds(34, 11, 362, 73);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Name:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(34, 151, 137, 32);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Surname:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(34, 209, 137, 32);
		frame.getContentPane().add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Birthday:");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_2.setBounds(34, 273, 137, 32);
		frame.getContentPane().add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_3 = new JLabel("Email:");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_3.setBounds(34, 339, 137, 32);
		frame.getContentPane().add(lblNewLabel_1_3);

		JLabel lblNewLabel_1_4 = new JLabel("Telephone:");
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_4.setBounds(34, 397, 137, 32);
		frame.getContentPane().add(lblNewLabel_1_4);

		nameTxt = new JTextField();
		nameTxt.setBounds(198, 151, 198, 32);
		frame.getContentPane().add(nameTxt);
		nameTxt.setColumns(10);

		surnameTxt = new JTextField();
		surnameTxt.setColumns(10);
		surnameTxt.setBounds(198, 209, 198, 32);
		frame.getContentPane().add(surnameTxt);

		birthTxt = new JTextField();
		birthTxt.setColumns(10);
		birthTxt.setBounds(198, 273, 198, 32);
		frame.getContentPane().add(birthTxt);

		emailTxt = new JTextField();
		emailTxt.setColumns(10);
		emailTxt.setBounds(198, 339, 198, 32);
		frame.getContentPane().add(emailTxt);

		telTxt = new JTextField();
		telTxt.setColumns(10);
		telTxt.setBounds(198, 397, 198, 32);
		frame.getContentPane().add(telTxt);

		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Patient p = new Patient();
				p.setDate(birthTxt.getText());
				p.setEmail(emailTxt.getText());
				p.setTelephone(telTxt.getText());
				p.setSurname(surnameTxt.getText());
				p.setName(nameTxt.getText());

				Query q = new Query(Viewpoint.Receptionist);
				q.setFunction("insertNewPatient");
				q.addArgument(new Gson().toJson(p));
				client.send(q);
				frame.dispose();
				Receptionist.openWin(client, model);
			}
		});
		btnNewButton.setBackground(new Color(0, 204, 102));
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBounds(226, 465, 151, 32);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Receptionist.openWin(client, model);
			}
		});
		btnNewButton_1.setBackground(new Color(204, 0, 51));
		btnNewButton_1.setForeground(new Color(0, 0, 0));
		btnNewButton_1.setBounds(42, 465, 151, 32);
		frame.getContentPane().add(btnNewButton_1);
	}
}
