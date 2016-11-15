package gateway;

import globals.Resources;
import java.rmi.registry.LocateRegistry;

public class RSUApp
{
    public static void main( String[] args ) {

         System.out.println("\n");

        // Create registry if it doesn't exist
        try {
            LocateRegistry.createRegistry(Resources.REGISTRY_PORT);
        } catch(Exception e) {
            // registry is already created
        }

        // Constroi RSU
        RSU rsu = new RSU();

        try {
            // Constroi objeto remoto
            RemoteRSUService rsu_service = rsu.getRemoteRSUService(rsu);
            // publica servico
            rsu_service.publish();

            try {
                System.out.println("Press enter to kill the road side unit...");
                System.in.read();
            } catch (java.io.IOException e) {
                System.out.println(Resources.ERROR_MSG("Unable to read from input. Exiting."));
            } finally {
                // remove servico do registry
                rsu_service.unpublish();
            }

        } catch (Exception e) {
            System.err.println(Resources.ERROR_MSG("CA remote interface is not present in the RMI registry."));
        }

        System.out.println("\n");
        System.exit(0);

    }
}
