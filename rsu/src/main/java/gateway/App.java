package gateway;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import globals.Resources;
import remote.RemoteCAInterface;
import remote.RemoteVehicleNetworkInterface;
import gateway.RemoteRSUService;


public class App
{
	// publishes RSU service to RMI
	public static void publishRSU(RemoteRSUService rsu_service) {
		try {
            RemoteRSUService stub = (RemoteRSUService) UnicastRemoteObject.exportObject(rsu_service, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(Resources.RSU_NAME , stub);
            System.out.println(Resources.OK_MSG("RemoteRSUService bound"));
        } catch (Exception e) {
            System.err.println(Resources.ERROR_MSG("RemoteRSUService exception:"));
            e.printStackTrace();
        }

	}

    public static void main( String[] args ) {
        
    	RSU rsu = new RSU();
        RemoteVehicleNetworkInterface vehicle_service = null;
        RemoteCAInterface ca_service = null;

    	try {
    		// Locate the vehicular network service
            Registry registry = LocateRegistry.getRegistry(Resources.REGISTRY_PORT); 
            vehicle_service
            		= (RemoteVehicleNetworkInterface) registry.lookup(Resources.VANET_NAME);

            // Locate the certificate authority service
            registry = LocateRegistry.getRegistry(Resources.REGISTRY_PORT);
            ca_service
            		= (RemoteCAInterface) registry.lookup(Resources.CA_NAME);

        } catch (Exception e) {
            System.err.println("Remote lookup exception:");
            e.printStackTrace();
        }

    	// Constroi objeto remoto
    	RemoteRSUService rsu_service = new RemoteRSUService(rsu,ca_service,vehicle_service);

        // Publica-o no RMI registry
    	publishRSU(rsu_service);
    }
}
