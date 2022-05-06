package Clients.MedicalRecordsStaff;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;

import Clients.Client;
import Objects.Request;
import Tools.CustomColours;
import Tools.Query;
import Tools.Viewpoint;

/**
 * This class represents the Requests. It shows all the requests of this system.
 * If a request is selected, it can be accepted or declined.
 * 
 * @author Ioanna Theophilou
 *
 */
public class Requests {
	/**
	 * the frame
	 */
	private JFrame frmAllRequests;
	/**
	 * the content pane
	 */
	private JPanel contentPane;
	/**
	 * the table used bellow
	 */
	public static JTable table;
	/**
	 * the label used bellow
	 */
	private JLabel lblNewLabel;
	/**
	 * the client
	 */
	@SuppressWarnings("unused")
	private Client client;

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @param client Client object
	 */
	private void initialize(Client client) {

		frmAllRequests = new JFrame();
		frmAllRequests.setTitle("All Requests");
		frmAllRequests.setBounds(100, 100, 910, 504);
		frmAllRequests.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel lblNewLabel_4 = new JLabel("       View all Requests");
		lblNewLabel_4.setFont(new Font("Segoe Script", Font.BOLD, 32));
		frmAllRequests.getContentPane().add(lblNewLabel_4, BorderLayout.NORTH);

		contentPane = new JPanel();
		contentPane.setBackground(CustomColours.interChangableWhite());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmAllRequests.setContentPane(contentPane);
		contentPane.setLayout(null);

		Query q = new Query(Viewpoint.MedicalRecords);
		q.setFunction("getRequests");
		client.send(q);

		Integer size = new Gson().fromJson(client.read(), Integer.class);
		ArrayList<Request> req = new ArrayList<Request>();
		for (int i = 0; i < size; i++)
			req.add(new Gson().fromJson(client.read(), Request.class));
		// SHOW
		String col[] = { "Decription", "Date" };
		int index = 0;
		String data[][] = new String[req.size()][col.length];
		for (Request tr : req) {
			data[index][0] = tr.getDescr();
			data[index][1] = tr.getDt();
			index++;
		}

		DefaultTableModel model = new DefaultTableModel(data, col);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(42, 90, 814, 334);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table = new JTable(model);
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int p = table.getSelectedRow();
				HandleRequests.openWindow(client, req.get(p));
				frmAllRequests.dispose();
			}
		});

		lblNewLabel = new JLabel("All Requests");
		lblNewLabel.setFont(new Font("Segoe Script", Font.PLAIN, 25));
		lblNewLabel.setBounds(351, 20, 255, 60);
		contentPane.add(lblNewLabel);
		table.setDefaultEditor(Object.class, null);

	}

	/**
	 * Static table access.
	 * 
	 * @return JTable object
	 */
	public static JTable getTable() {
		return table;
	}

	/**
	 * Alter JTable.
	 * 
	 * @param table JTable object
	 */
	@SuppressWarnings("static-access")
	public void setTable(JTable table) {
		this.table = table;
	}

	/**
	 * The function to open this window.
	 * 
	 * @param client Client object
	 */
	public static void openWindow(Client client) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Requests window = new Requests(client);
					window.frmAllRequests.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor with client.
	 * 
	 * @param client Client object
	 */
	public Requests(Client client) {
		initialize(client);
	}

}
