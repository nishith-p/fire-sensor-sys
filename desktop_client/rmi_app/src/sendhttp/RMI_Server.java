package sendhttp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.Timer;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class RMI_Server extends UnicastRemoteObject implements RMI_Service {
	
	public static void main(String[] args) throws RemoteException, AlreadyBoundException, IOException {

		Registry registry = LocateRegistry.createRegistry(5099);
		registry.bind("FireSensorService", new RMI_Server());

		System.out.println("Server started.");
		
		//Timer to refresh the window
		Timer t = new Timer(0, null);
		t.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					checkLevelsRepeat(); 
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		t.setRepeats(true);
		t.setDelay(15000);
		t.start();		
	}
	
	protected RMI_Server() throws RemoteException {
		super();
	}

	//Get Sensor
	@Override 
	public String getSensorDetails() throws RemoteException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4000/api/sensors/")).build(); 
		
		return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).thenApply((responseBody) -> parse(responseBody)).join(); 
	}
	
	public static String parse(String responseBody) {
		return responseBody;
	}
	
	//Add Sensor
	@Override
	public boolean addSensor(String sensorID, int floorNo, int roomNo, int c_level, int s_level) throws RemoteException {

		boolean res = false;

		JSONObject json = new JSONObject();
		json.put("sensorID", sensorID);
		json.put("floorNo", floorNo);
		json.put("roomNo", roomNo);
		json.put("c_level", c_level);
		json.put("s_level", s_level); 

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		try {	
			HttpPost request = new HttpPost("http://localhost:4000/api/sensors/");
			StringEntity params = new StringEntity(json.toString());

			//Headers and Authentication Token 
			request.addHeader("content-type", "application/json");
			request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImhlbGxvQGdtYWlsLmNvbSIsImlhdCI6MTU4ODQ1NDE2N30.yG30vjE6jkIXr_WMQ3PIPfloT7xtya-pJVLqkfZza2s");
			request.setEntity(params);
			
			org.apache.http.HttpResponse response = httpClient.execute(request);
			System.out.println(response.getStatusLine().toString().equalsIgnoreCase("HTTP/1.1 201 Created"));

			res = response.getStatusLine().toString().equalsIgnoreCase("HTTP/1.1 201 Created");	
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	//Edit Sensor
	@Override
	public boolean editSensor(String id, String sensorID, int floorNo, int roomNo, int c_level, int s_level) throws RemoteException {

		boolean res = false;

		JSONObject json = new JSONObject();
		json.put("sensorID", sensorID);
		json.put("floorNo", floorNo);
		json.put("roomNo", roomNo);
		json.put("c_level", c_level);
		json.put("s_level", s_level);

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		try {
			HttpPut request = new HttpPut("http://localhost:4000/api/sensors/" + id);
			StringEntity params = new StringEntity(json.toString());
			
			//Headers and Authentication Token 
			request.addHeader("content-type", "application/json");
			request.addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImhlbGxvQGdtYWlsLmNvbSIsImlhdCI6MTU4ODQ1NDE2N30.yG30vjE6jkIXr_WMQ3PIPfloT7xtya-pJVLqkfZza2s");
			request.setEntity(params);
			
			org.apache.http.HttpResponse response = httpClient.execute(request);
			System.out.println(response.getStatusLine().toString().equalsIgnoreCase("HTTP/1.1 200 OK"));

			res = response.getStatusLine().toString().equalsIgnoreCase("HTTP/1.1 200 OK");
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	//Delete Sensor
	@Override
	public boolean deleteSensor(String id) throws RemoteException {

		boolean res = false;

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		try {
			HttpDelete request = new HttpDelete("http://localhost:4000/api/sensors/" + id);

			//Headers and Authentication Token 
			request.addHeader("content-type", "application/json");
			request.addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImhlbGxvQGdtYWlsLmNvbSIsImlhdCI6MTU4ODQ1NDE2N30.yG30vjE6jkIXr_WMQ3PIPfloT7xtya-pJVLqkfZza2s");
			
			org.apache.http.HttpResponse response = httpClient.execute(request);
			
			res = response.getStatusLine().toString().equalsIgnoreCase("HTTP/1.1 204 No Content");
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	//Login
	@Override
	public String loginDetails(String email, String password) throws RemoteException {
		JSONObject json = new JSONObject();
		json.put("email", email);
		json.put("password", password);

		String res = null;

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		try {
			HttpPost request = new HttpPost("http://localhost:4000/users/login/");
			StringEntity params = new StringEntity(json.toString());
			
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			org.apache.http.HttpResponse response = httpClient.execute(request);

			if (response.getStatusLine().toString().equalsIgnoreCase("HTTP/1.1 200 OK")) {
				res = "success";
			} else {
				res = "failed";
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return res;
	}
	
	//Check Smoke and CO2 Levels
	public static void checkLevelsRepeat() {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4000/api/sensors/")).build(); 
		
		client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).thenApply((responseBody) -> checkLevels(responseBody)).join(); 
	}
	
	//Call sendMail()
	private static String checkLevels(String responseBody) {
		JSONArray sensors = new JSONArray(responseBody);
	
		for (int i = 0; i < sensors.length(); i++) {
			JSONObject obj = sensors.getJSONObject(i);
			
			int co2Level = obj.getInt("c_level");
			int smokeLevel = obj.getInt("s_level");

			if (co2Level > 5 || smokeLevel > 5) {
				//sendMail("nishith.pinnawala@gmail.com");
				System.out.println("FIRE ALERT: Email Sent."); 
				sendMail();
			} 
		}
		return null;
	} 
	
	//Send Email
	public static void sendMail() {
		//Dummy Email Service - Fake SMTP server 
		try {
			 
			InternetAddress[] distributionList = InternetAddress.parse("admin@firealert.com",false);
			String from = "emergency@firealert.com";
			String subject = "FIRE ALERT";
 
			Properties props = new Properties();
			props.put("mail.smtp.host", "localhost");
			props.put("mail.smtp.port", "25");
			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(false);
			
			Message msg = new MimeMessage(session);
			String message = "Fire Alert!";
			msg.setContent(message, "text/html; charset=utf-8");
			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO, distributionList);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			Transport.send(msg);
 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		//Uncomment to use Google Mail (Part 1)
		
		/*
		Properties props = new Properties();
		props.put("mail.smtp.auth" , "true");
		props.put("mail.smtp.starttls.enable" , "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.host" , "smtp.gmail.com");
		props.put("mail.smtp.port" , "587");
		
		String eMail = "firesysds@gmail.com";
		String password = "desktopapp123";
		 
		Session session = Session.getInstance(props, 
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(eMail , password);
			}
		});	
		 
		Message message = prepareMessage(session , eMail , rsp);
		 
		try {
			Transport.send(message);
			System.out.println("Email sent.");
		} catch (MessagingException e) {
			e.printStackTrace();
		}*/
	}

	//Uncomment to use Google Email (Part 2) 
	
	/*Create Email Content
	private static Message prepareMessage(Session session, String eMail , String rsp) {
		
		Message message = new MimeMessage(session);
		
		try {
			message.setFrom(new InternetAddress(eMail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(rsp));
			message.setSubject("Fire Alert!");
			message.setText("There's a fire.");
			return message;
		} catch (AddressException e) {		
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}*/
}
