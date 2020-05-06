package desktopClient;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONObject;

import desktopClient.ViewSensor;
import sendhttp.RMI_Service;

public class RMI_Test extends JFrame {
	
	private static JPanel contentPane;
	private static String responseBody;
	private static RMI_Test contentFrame; 
	private static boolean isLogged = false; 
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
		public void run() {
			try {
				contentFrame = new RMI_Test(isLogged);
				contentFrame.setLocationRelativeTo(null);
				contentFrame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		Timer t = new Timer(0, null);

		t.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					RMI_Service service = null;
					try {
						service = (RMI_Service) Naming.lookup("rmi://localhost:5099/FireSensorService");
							try {
								responseBody = service.getSensorDetails();
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}
						populateSensorComponents(responseBody);
					} catch (NotBoundException ex) {
						System.err.println(ex.getMessage());
					} catch (MalformedURLException ex) {
						System.err.println(ex.getMessage());
					} catch (RemoteException ex) {
						System.err.println(ex.getMessage());
					}
					System.out.println("15 seconds!");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		t.setRepeats(true);
		t.setDelay(15000); 
		t.start();
		
		}});
	}
	
	public RMI_Test(boolean isLogged) {
		
		this.isLogged = isLogged;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 390, 800);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menuList = new JMenu("Options");
		menuBar.add(menuList);

		JMenuItem menuItem1 = new JMenuItem("Add Sensor");
		menuItem1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentFrame.dispose();
				AddSensor addSensorForm = new AddSensor();
				addSensorForm.setVisible(true);
			}
		});
		menuList.add(menuItem1);
		menuItem1.setVisible(isLogged);

		JMenuItem menuItem2 = new JMenuItem("Login");
		menuItem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentFrame.dispose();
				Login login = new Login();
				login.setVisible(true);
			}
		});
		menuList.add(menuItem2);
		menuItem2.setVisible(!isLogged);
		
		JSeparator separator = new JSeparator();
		menuList.add(separator);

		JMenuItem menuItem3 = new JMenuItem("Exit");
		menuItem3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(EXIT_ON_CLOSE);
			}
		});
		menuList.add(menuItem3); 

		//Grid Layout
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		GridLayout gridPane = new GridLayout();
		gridPane.setColumns(1);
		gridPane.setRows(0);
		contentPane.setLayout(gridPane);

		//Scroll Bar
		JScrollPane scrollPane = new JScrollPane(contentPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		setContentPane(scrollPane);

	}
	
	public static void populateSensorComponents(String responseBody) {
		contentPane.removeAll(); 
		JSONArray sensors = new JSONArray(responseBody);
		
		for (int i = 0; i < sensors.length(); i++) {
			JSONObject sensor = sensors.getJSONObject(i);
			String id = sensor.getString("_id"); 
			String sensorId = sensor.getString("sensorID"); 
			int floorNo = sensor.getInt("floorNo");
			int roomNo = sensor.getInt("roomNo"); 
			int c_level = sensor.getInt("c_level"); 
			int s_level = sensor.getInt("s_level"); 
			boolean status = sensor.getBoolean("status");
			
			ViewSensor viewSensor = new ViewSensor(id, sensorId, floorNo, roomNo, c_level, s_level, status, isLogged, contentFrame);
			viewSensor.setVisible(true);
			contentPane.add(viewSensor);
		}
		contentPane.validate();
		contentPane.repaint();
	}
}