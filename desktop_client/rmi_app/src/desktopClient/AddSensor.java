package desktopClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import desktopClient.AddSensor;
import desktopClient.RMI_Test;
import sendhttp.RMI_Service;

public class AddSensor extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JTextField txtsensorid;
	private JTextField txtfloorno;
	private JTextField txtroomno;
	private JTextField txtsmokelvl;
	private JTextField txtcolvl;
	private boolean res;
	
	public static void main(String[] args) {
		try {
			AddSensor dialog = new AddSensor();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AddSensor() {
		setBounds(250, 300, 360, 350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblSensorName = new JLabel("Sensor Name:");
		lblSensorName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSensorName.setBounds(25, 25, 100, 30);
		contentPanel.add(lblSensorName);

		JLabel lblFloorNo = new JLabel("Floor No:");
		lblFloorNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFloorNo.setBounds(25, 75, 100, 30);
		contentPanel.add(lblFloorNo);

		JLabel lblRoomNo = new JLabel("Room No:");
		lblRoomNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRoomNo.setBounds(25, 125, 100, 30);
		contentPanel.add(lblRoomNo);
		
		JLabel lblSmokeLevel = new JLabel("Smoke Level:");
		lblSmokeLevel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSmokeLevel.setBounds(25, 175, 100, 30);
		contentPanel.add(lblSmokeLevel);

		JLabel lblCOLevel = new JLabel("CO2 Level:");
		lblCOLevel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCOLevel.setBounds(25, 225, 100, 30);
		contentPanel.add(lblCOLevel);
		
		txtsensorid = new JTextField();
		txtsensorid.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtsensorid.setBounds(150, 25, 170, 30);
		contentPanel.add(txtsensorid);
		txtsensorid.setColumns(10);

		txtfloorno = new JTextField();
		txtfloorno.setBounds(150, 75, 170, 30);
		contentPanel.add(txtfloorno);
		txtfloorno.setColumns(10);

		txtroomno = new JTextField();
		txtroomno.setBounds(150, 125, 170, 30);
		contentPanel.add(txtroomno);
		txtroomno.setColumns(10);
		
		txtsmokelvl = new JTextField();
		txtsmokelvl.setBounds(150, 175, 170, 30);
		contentPanel.add(txtsmokelvl);
		txtsmokelvl.setColumns(10);
		
		txtcolvl = new JTextField();
		txtcolvl.setBounds(150, 225, 170, 30);
		contentPanel.add(txtcolvl);
		txtcolvl.setColumns(10);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton saveButton = new JButton("Save");
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						String id = txtsensorid.getText();
						int floor = Integer.parseInt(txtfloorno.getText());
						int room = Integer.parseInt(txtroomno.getText()); 
						int smoke = Integer.parseInt(txtsmokelvl.getText()); 
						int co = Integer.parseInt(txtcolvl.getText()); 

						RMI_Service service;

						try {
							service = (RMI_Service) Naming.lookup("rmi://localhost:5099/FireSensorService");
							try {
								res = service.addSensor(id, floor, room, co, smoke);
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}
						} catch (MalformedURLException | RemoteException | NotBoundException ex) {
							ex.printStackTrace();
						}

						dispose(); 
						RMI_Test rmiTest = new RMI_Test(true);
						rmiTest.main(null);
					}
				});
				saveButton.setActionCommand("Save");
				buttonPane.add(saveButton);
				getRootPane().setDefaultButton(saveButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose(); 
						RMI_Test rmiTest = new RMI_Test(true);
						rmiTest.main(null); 
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
