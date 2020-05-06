package desktopClient;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import desktopClient.EditSensor;

public class ViewSensor extends JPanel {
	private String id; 
	private String sensorId;
	private int floorNo;
	private int roomNo;
	private int c_level;
	private int s_level;
	private boolean status;
	private boolean isLogged; 
	private JFrame contentFrame; 
	
	public ViewSensor(String id, String sensorId, int floorNo, int roomNo, int c_level, int s_level, boolean status, boolean isLogged, JFrame contentFrame) {
		super();
		setLayout(null);
		this.id = id;
		this.sensorId = sensorId;
		this.floorNo = floorNo;
		this.roomNo = roomNo;
		this.c_level = c_level;
		this.s_level = s_level;
		this.status = status;
		this.isLogged = isLogged; 
		this.contentFrame = contentFrame; 
		
		//Layout
		JPanel panel = new JPanel();
		panel.setBackground(this.c_level > 5 || this.s_level > 5 ? new Color(217, 83, 79) : new Color(92, 184, 92));
		panel.setBounds(0, 5, 390, 130);
		add(panel);
		panel.setLayout(null);
		 
		//Section 1
		JLabel label_1 = new JLabel("Sensor:");
		label_1.setForeground(new Color(0, 0, 0));
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_1.setBounds(25, 25, 200, 30);
		panel.add(label_1);
		
		JLabel label_sensorId = new JLabel(this.sensorId);
		label_sensorId.setForeground(new Color(0, 0, 0));
		label_sensorId.setFont(new Font("Tahoma", Font.BOLD, 18));
		label_sensorId.setBounds(100, 25, 200, 30);
		panel.add(label_sensorId);
		
		JLabel label_2 = new JLabel("Floor:");
		label_2.setForeground(new Color(0, 0, 0));
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_2.setBounds(25, 50, 200, 30);
		panel.add(label_2);
		
		JLabel label_floorNo = new JLabel("" + this.floorNo);
		label_floorNo.setForeground(new Color(0, 0, 0));
		label_floorNo.setFont(new Font("Tahoma", Font.BOLD, 18));
		label_floorNo.setBounds(100, 50, 200, 30);
		panel.add(label_floorNo);
		
		JLabel label_3 = new JLabel("Room:");
		label_3.setForeground(new Color(0, 0, 0));
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_3.setBounds(25, 75, 200, 30);
		panel.add(label_3);
		
		JLabel label_roomNo = new JLabel("" + this.roomNo);
		label_roomNo.setForeground(new Color(0, 0, 0));
		label_roomNo.setFont(new Font("Tahoma", Font.BOLD, 18));
		label_roomNo.setBounds(100, 75, 200, 30);
		panel.add(label_roomNo);
		
		//Section 2
		JLabel label_4 = new JLabel("Smoke Level:");
		label_4.setForeground(new Color(0, 0, 0));
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_4.setBounds(200, 25, 150, 30);
		panel.add(label_4);
		
		JLabel label_sLevel = new JLabel("" + this.s_level);
		label_sLevel.setForeground(new Color(0, 0, 0));
		label_sLevel.setFont(new Font("Tahoma", Font.BOLD, 18));
		label_sLevel.setBounds(320, 25, 150, 30);
		panel.add(label_sLevel);
		
		JLabel label_5 = new JLabel("CO2 Level:");
		label_5.setForeground(new Color(0, 0, 0));
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_5.setBounds(200, 50, 150, 30);
		panel.add(label_5);
		
		JLabel label_cLevel = new JLabel("" + this.c_level);
		label_cLevel.setForeground(new Color(0, 0, 0));
		label_cLevel.setFont(new Font("Tahoma", Font.BOLD, 18));
		label_cLevel.setBounds(320, 50, 150, 30);
		panel.add(label_cLevel);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setVisible(this.isLogged);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentFrame.dispose();
				EditSensor editSensor = new EditSensor(id, label_sensorId.getText(), label_floorNo.getText(), label_roomNo.getText(), label_cLevel.getText(), label_sLevel.getText());
				editSensor.setVisible(true);
			}
		});
		btnEdit.setBounds(230, 82, 100, 20);
		btnEdit.setBorderPainted(false);
		panel.add(btnEdit);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSensorId() {
		return sensorId;
	}
	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}
	public int getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(int floorNo) {
		this.floorNo = floorNo;
	}
	public int getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}
	public int getC_level() {
		return c_level;
	}
	public void setC_level(int c_level) {
		this.c_level = c_level;
	}
	public int getS_level() {
		return s_level;
	}
	public void setS_level(int s_level) {
		this.s_level = s_level;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
}
