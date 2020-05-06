package sendhttp;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI_Service extends Remote {
	public String getSensorDetails() throws RemoteException;
	public boolean addSensor(String sensorID, int floorNo, int roomNo, int c_level, int s_level) throws RemoteException;
	public boolean editSensor(String id, String sensorID, int floorNo, int roomNo, int c_level, int s_level) throws RemoteException;
	public boolean deleteSensor(String id) throws RemoteException;
	public String loginDetails(String email, String password) throws RemoteException;
}
