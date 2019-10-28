package config;

import connection.SerialModbusConnection;
import equipment.plsm.Plsm;
import jssc.SerialPortList;
import net.wimpi.modbus.facade.ModbusSerialMaster;
import net.wimpi.modbus.procimg.Register;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * This class initializes the configuration file.
 * First, all ports are initialized to <code>String[] ports</code>
 * If there is no data in the configuration file, then the program terminates
 * File slaves.properties contains all devices
 **/

//singleton
public class ProgrammConfiguration {
    private static ProgrammConfiguration configuration = new ProgrammConfiguration();
    //plsmMap key - slaveId
    private final Map<Integer, Plsm> plsmMap;
    private final Properties properties;
    private final String[] ports;
    private SerialModbusConnection modbusConnection;
    private String port;

    private ProgrammConfiguration() {
        this.plsmMap = new HashMap<>();
        this.properties = new Properties();
        this.ports = SerialPortList.getPortNames();
    }

    public static ProgrammConfiguration getInstance() {
        return configuration;
    }

    public void configure() {
        //make slaves.properties
        try (OutputStream outputStream = new FileOutputStream("slaves.properties")) {
            for (String port : ports) {
                this.port = port;
                modbusConnection = new SerialModbusConnection(port);
                ModbusSerialMaster master = modbusConnection.getMaster();
                master.connect();
                for (int i = 0; i < 248; i++) {
                    Register[] register = master.readMultipleRegisters(i, 0, 32);
                    // register 10 - slaveId, register 31 - device type
                    properties.setProperty(String.valueOf(register[10].getValue()), String.valueOf(register[31].getValue()));
                    //Thread.sleep(20);
                }
                master.disconnect();
            }
            // write properties to file
            properties.store(outputStream, null);

        } catch (FileNotFoundException e) {
            System.out.println("File slaves.properties not found in resources");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.printf("Cannot open connection to %s", port);
        }
    }

    public boolean createPlsmMap() {
        try (InputStream inputStream = new FileInputStream("slaves.properties")) {
            properties.load(inputStream);
            if (properties.isEmpty()) {
                return false;
            } else {
                for (String port : ports) {
                    Set<String> keys = properties.stringPropertyNames();
                    keys.forEach(k -> {
                        try {
                            plsmMap.put(Integer.valueOf(k), new Plsm(port, Integer.parseInt(k)));
                        } catch (IOException e) {
                            System.out.println("Cannot open port");
                            e.printStackTrace();
                        }
                    });
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File slaves.properties not found in resources");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Map<Integer, Plsm> getPlsmMap() {
        return plsmMap;
    }
}
