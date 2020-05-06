import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import javax.swing.Timer;

import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

public class fireSensor {

	public static void main(String[] args) throws RemoteException {
		
		//EDIT this to match the Sensor ID
		String id = "5eaf2d02bc03530bec689f07"; 

		//int c_level;
		//int s_level; 
		
		//Scanner input = new Scanner(System.in); 
		
		/*System.out.print("CO2 Level: "); 
		c_level = input.nextInt();
		
		System.out.print("Smoke Level: "); 
		s_level = input.nextInt();*/
		
	    try {
	        while (true) {
				int c_level = (int) ((Math.random()*((9-1)+1))+1); 
				int s_level = (int) ((Math.random()*((9-1)+1))+1); 
				editSensor(id, c_level, s_level);
				System.out.println("WORKING");
	            Thread.sleep(10 * 1000);
	        }
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
		
	}
	
	public static boolean editSensor(String id, double c_level, double s_level) throws RemoteException {

		boolean res = false;

		JSONObject json = new JSONObject();
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

}
