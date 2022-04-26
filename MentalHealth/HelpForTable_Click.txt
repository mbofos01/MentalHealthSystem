import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class Photos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table_1;

	public Photos(int user, MainClass conn) {
		setTitle("Photos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 802, 392);
		setIconImage(new ImageIcon(getClass().getResource("Icon.png")).getImage());

		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Add photo");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				PhotoForm phF = new PhotoForm(user, conn, 0,-1);
				phF.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setBounds(49, 114, 102, 23);
		contentPane.add(btnNewButton);

		// SHOW
		String col[] = { "Photo ID", "Source", "URL", "width", "Height", "Owner", "Album" };
		Show obj = new Show();
		String data[][] = obj.ShowPhoto(user, conn);
		DefaultTableModel model = new DefaultTableModel(data, col);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(49, 146, 708, 183);
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
				int p=table_1.getSelectedRow();
				dispose();
				PhotoForm update = new PhotoForm(user, conn, 1,p);
				update.setVisible(true);
			}
		});
		JLabel lblNewLabel = new JLabel("");
		Image image = new ImageIcon(this.getClass().getResource("login.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(image));
		lblNewLabel.setBounds(368, 24, 102, 84);
		contentPane.add(lblNewLabel);

		JButton btnNewButton_1 = new JButton("BACK");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				dispose();
				Menu men = new Menu(user, conn);
				men.setVisible(true);
			}
		});
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(new Color(102, 102, 255));
		btnNewButton_1.setBounds(10, 11, 80, 23);
		contentPane.add(btnNewButton_1);

	}
}
