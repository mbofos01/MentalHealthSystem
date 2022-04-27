package Clients.Clinical;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;

import Clients.Client;
import Objects.Drug;
import Tools.Query;
import Tools.Viewpoint;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import static javax.swing.JOptionPane.showMessageDialog;

public class Main {

	private JFrame frame;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
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
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 703, 508);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clinical Viewpoint");

		contentPane = new JPanel();
		contentPane.setBackground(Tools.CustomColours.White());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Add photo");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("SAD");
			}
		});
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setBounds(482, 259, 102, 23);
		contentPane.add(btnNewButton);
		Client client = new Client("clientConf.json");
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
		table_1 = new JTable();
		scrollPane.setViewportView(table_1);
		table_1 = new JTable(model);
		scrollPane.setViewportView(table_1);
		table_1.setDefaultEditor(Object.class, null);
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int p = table_1.getSelectedRow();
				System.out.println(p);
				showMessageDialog(null,
						drug_list.get(p).getCommercial_name() + " side effect: " + drug_list.get(p).getSide_effect(),
						"Possible Side Effects", JOptionPane.INFORMATION_MESSAGE);

			}
		});

		JButton btnNewButton_1 = new JButton("Log Out");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Go back to log in
				System.out.println("Log out clinical staff");
				frame.dispose();

			}
		});
		btnNewButton_1.setForeground(Tools.CustomColours.White());
		btnNewButton_1.setBackground(Tools.CustomColours.Red());
		btnNewButton_1.setBounds(33, 410, 80, 23);
		contentPane.add(btnNewButton_1);
	}

}
