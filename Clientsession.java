
package session;

public class Clientsession {
     private static Clientsession instance;
    private int clientID;
    private int vehicleID;
    private int serviceID;
    private String signature; 

    private Clientsession() {}

    public static Clientsession getInstance() {
        if (instance == null) {
            instance = new Clientsession();
        }
        return instance;
    }

    public void setClientID(int clientID, String clientEmail) {
        this.clientID = clientID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public int getVehicleID() {
        return vehicleID;
    }
    
    public int getserviceID(){
        return serviceID;
    }
    
    public void setserviceID(int serviceID){
        this.serviceID = serviceID;
    }
    
     public String getSignature() { 
        return signature;
    }

    public void setSignature(String signature) { 
        this.signature = signature;
    }
}
