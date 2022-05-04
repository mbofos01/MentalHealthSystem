package Clients.HealthService;

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
import Objects.HealthServ;

import Tools.Query;
import Tools.Viewpoint;
import Tools.CustomColours;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * In-page for the Health Service Viewpoint. As stated in the basic
 * structure each viewpoint must have a client object as an argument.
 * 
 * @author Demetra Hadjicosti
 *
 */
public class HealthServiceLogin {

	private JFrame frmHealthServiceLogin;
	private JTextField usernameField;
	private JPasswordField passwordField;

	/**s
	 * Launch the application.
	 * 
	 * @param args No arguments needed
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client client = new Client("clientConf.json");
					HealthServiceLogin window = new HealthServiceLogin(client);
					window.frmHealthServiceLogin.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 * 
	 * @param client Client object must be passed in order to communicate with the
	 *               server
	 */
	public HealthServiceLogin(Client client) {
		initialize(client);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @param client Client object must be passed in order to communicate with the
	 *               server
	 */
	private void initialize(final Client client) {
		frmHealthServiceLogin = new JFrame();
		frmHealthServiceLogin.getContentPane().setForeground(CustomColours.Gray());
		frmHealthServiceLogin.setTitle("Health Service Login Page");
		frmHealthServiceLogin.setResizable(false);
		frmHealthServiceLogin.setBounds(100, 100, 539, 422);
		frmHealthServiceLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHealthServiceLogin.getContentPane().setLayout(null);

		usernameField = new JTextField();
		usernameField.setBounds(265, 157, 213, 28);
		frmHealthServiceLogin.getContentPane().add(usernameField);
		usernameField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(265, 215, 213, 28);
		frmHealthServiceLogin.getContentPane().add(passwordField);

		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		usernameLabel.setBounds(75, 157, 165, 28);
		frmHealthServiceLogin.getContentPane().add(usernameLabel);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordLabel.setBounds(75, 215, 165, 28);
		frmHealthServiceLogin.getContentPane().add(passwordLabel);

		JButton loginbtn = new JButton("Log In");
		loginbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user1 = usernameField.getText();
				String pass1 = new String(passwordField.getPassword());

				Query query = new Query(Viewpoint.HealthService);
				query.setFunction("login");
				query.addArgument(user1);
				query.addArgument(pass1);
				client.send(query);

				HealthServ record = new Gson().fromJson(client.read(), HealthServ.class);
				if (record.getId() == -1) {
					JOptionPane.showMessageDialog(null, "           Wrong password!" + '\n', "Error",
							JOptionPane.ERROR_MESSAGE);
					System.exit(0);

				} else {
					System.out.println(record.toString());
					frmHealthServiceLogin.dispose();
					HealthService.openWindow(client, record);
				}

				return;
			}

		});

		loginbtn.setBackground(CustomColours.Green());
		loginbtn.setForeground(CustomColours.White());
		loginbtn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		loginbtn.setBounds(102, 295, 138, 38);
		frmHealthServiceLogin.getContentPane().add(loginbtn);

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
		frmHealthServiceLogin.getContentPane().add(cancelbtn);

		JLabel title = new JLabel("Login to Health Service");
		title.setForeground(CustomColours.Black());
		title.setVerticalAlignment(SwingConstants.TOP);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.PLAIN, 24));
		title.setBounds(56, 28, 422, 55);
		frmHealthServiceLogin.getContentPane().add(title);

		JLabel title2 = new JLabel("Management System");
		title2.setForeground(CustomColours.Black());
		title2.setFont(new Font("Tahoma", Font.PLAIN, 24));
		title2.setBounds(154, 69, 239, 38);
		frmHealthServiceLogin.getContentPane().add(title2);
	}
}
