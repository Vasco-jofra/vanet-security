package vanet;

import entity.vanet.RemoteVehicleService;
import entity.vanet.Vehicle;
import globals.Resources;
import remote.RemoteVehicleInterface;
import remote.Vector2Df;

import java.util.TreeMap;
import java.util.Map;
import java.util.Set;

import java.math.*;

/*
	Simulate physical wireless network
*/
public class VehicleNetwork {
	private Map<String, RemoteVehicleInterface> vehicleList = new TreeMap<>();

	public VehicleNetwork() {
		// @TODO: Launch vehicle processes (maybe) (probably not?)
	}

	public Set<Map.Entry<String, RemoteVehicleInterface>> getVehicleEntrySet() {
		return vehicleList.entrySet();
	}

	public boolean hasVehicle(String name) {
		return vehicleList.containsKey(name);
	}

	public void addVehicle(String name, RemoteVehicleInterface vehicleToAdd) {
		System.out.println(Resources.OK_MSG("Adding vehicle \"" + name + "\" to the network."));
		vehicleList.put(name, vehicleToAdd);
	}

	public void removeVehicle(String name) {
		System.out.println(Resources.OK_MSG("Removing vehicle \"" + name + "\" from the network."));
		vehicleList.remove(name);
	}

	public static boolean inRange(Vector2Df pos1, Vector2Df pos2) {
		double distance = pos1.distance(pos2);
		return !(distance == 0 || distance > Resources.MAX_IN_RANGE);
	}
}
