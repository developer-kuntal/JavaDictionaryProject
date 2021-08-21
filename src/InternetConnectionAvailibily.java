import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class InternetConnectionAvailibily {
    
    public boolean status;

    public InternetConnectionAvailibily() {
        this.status = false;
    }

    public boolean hasInternetConnection() {
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }  
        while (interfaces.hasMoreElements()) {  
            NetworkInterface nic = interfaces.nextElement();  

            // InterfaceName["Realtek RTL8723BE Wireless LAN 802.11n PCI-E NIC"].isUp() == true
            // The PC have the internet connection ... 
            String interfaceName = "Realtek RTL8723BE Wireless LAN 802.11n PCI-E NIC";

            if( interfaceName.equals(nic.getDisplayName().toString())  ) {
                // System.out.print("Interface Name : [" + nic.getDisplayName() + "]");  
                try {
                    // System.out.println(", Is connected : [" + nic.isUp() + "]");
                    this.status = nic.isUp();
                    break;
                } catch (SocketException e) {
                    // e.printStackTrace();
                    this.status = false;
                    break;
                }  
            }
            
        }
        return this.status;
    }
}
