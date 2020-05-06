package desktopClient;

import java.awt.BorderLayout;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import desktopClient.RMI_Test;
import desktopClient.EditSensor;
import sendhttp.RMI_Service;

public class EditSensor extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private boolean res;
	private JTextField txtSensorName;
	private JTextField txtFloorNo;
	private JTextField txtRoomNo;
	private JTextField txtCOLevel; 
	private JTextField txtSmokeLevel;

	public static void main(String[] args) {
		try {
			EditSensor dialog = new EditSensor(null, null, null, null, null, null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public EditSensor(String id, String sensorID, String floorNo, String roomNo, String c_level, String s_level) {
		setBounds(250, 300, 360, 350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblSensorName = new JLabel("Sensor Name: ");
			lblSensorName.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblSensorName.setBounds(25, 25, 100, 30);
			contentPanel.add(lblSensorName);
		}
		{
			JLabel lblFloorNo = new JLabel("Floor No:");
			lblFloorNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblFloorNo.setBounds(25, 75, 100, 30);
			contentPanel.add(lblFloorNo);
		}
		{
			JLabel lblRoomNo = new JLabel("Room No:");
			lblRoomNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblRoomNo.setBounds(25, 125, 100, 30);
			contentPanel.add(lblRoomNo);
		}
		{
			JLabel lblCOLevel = new JLabel("CO2 Level: ");
			lblCOLevel.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblCOLevel.setBounds(25, 175, 100, 30);
			contentPanel.add(lblCOLevel);
		}
		{
			JLabel lblSmokeLevel = new JLabel("Smoke Level: ");
			lblSmokeLevel.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblSmokeLevel.setBounds(25, 225, 100, 30);
			contentPanel.add(lblSmokeLevel);
		}
		{
			txtSensorName = new JTextField();
			txtSensorName.setBounds(150, 25, 170, 30);
			contentPanel.add(txtSensorName);
			txtSensorName.setColumns(10);
		}
		{
			txtFloorNo = new JTextField();
			txtFloorNo.setBounds(150, 75, 170, 30);
			contentPanel.add(txtFloorNo);
			txtFloorNo.setColumns(10);
		}
		{
			txtRoomNo = new JTextField();
			txtRoomNo.setBounds(150, 125, 170, 30);
			contentPanel.add(txtRoomNo);
			txtRoomNo.setColumns(10);
		}
		{
			txtCOLevel = new JTextField();
			txtCOLevel.setBounds(150, 175, 170, 30);
			contentPanel.add(txtCOLevel);
			txtCOLevel.setColumns(10);
		}
		{
			txtSmokeLevel = new JTextField();
			txtSmokeLevel.setBounds(150, 225, 170, 30);
			contentPanel.add(txtSmokeLevel);
			txtSmokeLevel.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton saveButton = new JButton("Save");
				txtSensorName.setText(sensorID);
				txtFloorNo.setText(floorNo);
				txtRoomNo.setText(roomNo);
				txtCOLevel.setText(c_level);
				txtSmokeLevel.setText(s_level);
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						RMI_Service service;

						try {
							service = (RMI_Service) Naming.lookup("rmi://localhost:5099/FireSensorService");
							try {
								res = service.editSensor(id, txtSensorName.getText(), Integer.parseInt(txtFloorNo.getText()),Integer.parseInt(txtRoomNo.getText()), Integer.parseInt(txtCOLevel.getText()), Integer.parseInt(txtSmokeLevel.getText()));
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
				JButton deleteButton = new JButton("Delete");
				deleteButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						RMI_Service service;

						try {
							service = (RMI_Service) Naming.lookup("rmi://localhost:5099/FireSensorService");
							try {
								res = service.deleteSensor(id);
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
				deleteButton.setActionCommand("Delete");
				buttonPane.add(deleteButton);
				getRootPane().setDefaultButton(deleteButton);
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
				buttonPane.add(cancelButton);
			}
		}
	}
}
