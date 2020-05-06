package desktopClient;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import sendhttp.RMI_Service;

public class Login extends JFrame{

	private JPanel contentPane;
	private JTextField txtemail;
	private JPasswordField txtpassword;
	private static Login frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Login();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(350, 250, 500, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Sensor Monitoring System");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblTitle.setBounds(105, 55, 300, 50);
		contentPane.add(lblTitle);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEmail.setBounds(100, 159, 77, 16);
		contentPane.add(lblEmail);

		JLabel lblPass = new JLabel("Password:");
		lblPass.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPass.setBounds(100, 217, 85, 22);
		contentPane.add(lblPass);

		JLabel lblFail = new JLabel("Login failed.");
		lblFail.setForeground(Color.RED);
		lblFail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFail.setBounds(210, 382, 185, 22);
		contentPane.add(lblFail);
		lblFail.setVisible(false);

		txtemail = new JTextField();
		txtemail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtemail.setBounds(195, 154, 205, 27);
		contentPane.add(txtemail);
		txtemail.setColumns(10);

		txtpassword = new JPasswordField();
		txtpassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtpassword.setBounds(195, 215, 205, 27);
		contentPane.add(txtpassword);
		txtpassword.setColumns(10);

		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String email = txtemail.getText();
				String password = txtpassword.getText();

				RMI_Service service;
				String result = null;
				try {
					service = (RMI_Service) Naming.lookup("rmi://localhost:5099/FireSensorService");
					result = service.loginDetails(email, password);
				} catch (MalformedURLException | RemoteException | NotBoundException ex) {
					ex.printStackTrace();
				}
				System.out.println(result);

				if (result.equalsIgnoreCase("success")) {
					dispose();
					RMI_Test rmitest = new RMI_Test(true);
					rmitest.main(null);
				} else {
					lblFail.setVisible(true);
				}

			}
		});
		btnLogin.setBounds(140, 300, 100, 40);
		contentPane.add(btnLogin);
		
		JButton btnGuest = new JButton("Guest");
		btnGuest.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnGuest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				RMI_Test rmitest = new RMI_Test(false);
				rmitest.main(null);
			}
		});
		btnGuest.setBounds(255, 300, 100, 40);
		contentPane.add(btnGuest);
	}
}
