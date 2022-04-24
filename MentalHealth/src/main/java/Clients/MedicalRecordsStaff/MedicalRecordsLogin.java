package Clients.MedicalRecordsStaff;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.google.gson.Gson;

import Clients.Client;
import Objects.RecordsStaff;
import Tools.Query;
import Tools.Viewpoint;
import Tools.CustomColours;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * In-page for the Medical Records Management Viewpoint. As stated in the basic
 * structure each viewpoint must have a client object as an argument.
 * 
 * @author Michail Panagiotis Bofos
 *
 */
public class MedicalRecordsLogin {

	private JFrame frmMedicalRecordsStaff;
	private JTextField usernameField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client client = new Client("clientConf.json");
					MedicalRecordsLogin window = new MedicalRecordsLogin(client);
					window.frmMedicalRecordsStaff.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public MedicalRecordsLogin(Client client) {
		initialize(client);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final Client client) {
		frmMedicalRecordsStaff = new JFrame();
		frmMedicalRecordsStaff.getContentPane().setForeground(CustomColours.Gray());
		frmMedicalRecordsStaff.setTitle("Medical Records Staff Login Page");
		frmMedicalRecordsStaff.setResizable(false);
		frmMedicalRecordsStaff.setBounds(100, 100, 539, 422);
		frmMedicalRecordsStaff.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMedicalRecordsStaff.getContentPane().setLayout(null);

		usernameField = new JTextField();
		usernameField.setBounds(265, 157, 213, 28);
		frmMedicalRecordsStaff.getContentPane().add(usernameField);
		usernameField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(265, 215, 213, 28);
		frmMedicalRecordsStaff.getContentPane().add(passwordField);

		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		usernameLabel.setBounds(75, 157, 165, 28);
		frmMedicalRecordsStaff.getContentPane().add(usernameLabel);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordLabel.setBounds(75, 215, 165, 28);
		frmMedicalRecordsStaff.getContentPane().add(passwordLabel);

		JButton loginbtn = new JButton("Log In");
		loginbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user1 = usernameField.getText();
				String pass1 = new String(passwordField.getPassword());

				Query query = new Query(Viewpoint.MedicalRecords);
				query.setFunction("login");
				query.addArgument(user1);
				query.addArgument(pass1);
				client.send(query);

				RecordsStaff record = new Gson().fromJson(client.read(), RecordsStaff.class);
				if (record.getId() == -1) {
					JOptionPane.showMessageDialog(null, "           Wrong password!" + '\n', "Error",
							JOptionPane.ERROR_MESSAGE);
					System.exit(0);

				} else {
					System.out.println(record.toString());
					frmMedicalRecordsStaff.dispose();
					WelcomeRecordStaff.openWindow(client, record);
				}

				return;
			}

		});

		loginbtn.setBackground(CustomColours.Green());
		loginbtn.setForeground(CustomColours.White());
		loginbtn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		loginbtn.setBounds(102, 295, 138, 38);
		frmMedicalRecordsStaff.getContentPane().add(loginbtn);

		JButton cancelbtn = new JButton("Cancel");
		cancelbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		cancelbtn.setForeground(CustomColours.White());
		cancelbtn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		cancelbtn.setBackground(CustomColours.Red());
		cancelbtn.setBounds(280, 295, 138, 38);
		frmMedicalRecordsStaff.getContentPane().add(cancelbtn);

		JLabel title = new JLabel("Login to Medical Records");
		title.setForeground(CustomColours.Black());
		title.setVerticalAlignment(SwingConstants.TOP);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.PLAIN, 24));
		title.setBounds(56, 28, 422, 55);
		frmMedicalRecordsStaff.getContentPane().add(title);

		JLabel title2 = new JLabel("Management System");
		title2.setForeground(CustomColours.Black());
		title2.setFont(new Font("Tahoma", Font.PLAIN, 24));
		title2.setBounds(154, 69, 239, 38);
		frmMedicalRecordsStaff.getContentPane().add(title2);
	}
}
