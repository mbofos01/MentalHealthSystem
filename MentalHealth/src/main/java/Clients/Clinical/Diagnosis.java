package Clients.Clinical;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.google.gson.Gson;

import Clients.Client;
import Objects.Condition;
import Tools.CustomColours;
import Tools.Query;
import Tools.Viewpoint;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Diagnosis {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Diagnosis window = new Diagnosis();
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
	public Diagnosis() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(CustomColours.interChangableWhite());
		frame.setTitle("Create a diagnosis");
		frame.setBounds(100, 100, 602, 603);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel condition = new JLabel("Condition:");
		condition.setFont(new Font("Tahoma", Font.PLAIN, 18));
		condition.setBounds(49, 180, 97, 22);
		frame.getContentPane().add(condition);

		JComboBox<String> conditionDropdown = new JComboBox<String>();
		Client client = new Client("127.0.0.1", 8082);
		Query q = new Query(Viewpoint.Clinical);
		q.setFunction("getConditions");
		client.send(q);
		Integer size = new Gson().fromJson(client.read(), Integer.class);
		System.out.println(size);
		ArrayList<Condition> conds = new ArrayList<>();

		for (int i = 0; i < size; i++)
			conds.add(new Gson().fromJson(client.read(), Condition.class));
		for (Condition con : conds)
			conditionDropdown.addItem(con.getName());
		conditionDropdown.setBounds(156, 183, 245, 22);
		frame.getContentPane().add(conditionDropdown);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = conditionDropdown.getSelectedIndex();
				System.out.println(conds.get(index).getCondition_id());
			}
		});
		btnNewButton.setBounds(424, 492, 117, 29);
		frame.getContentPane().add(btnNewButton);
	}
}
