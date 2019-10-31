package connection;

import net.wimpi.modbus.facade.ModbusSerialMaster;
import net.wimpi.modbus.util.SerialParameters;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SerialModbusConnection {
    private final SerialParameters serialParameters;
    private final ModbusSerialMaster master;
    private final String port;
    private final Properties properties;

    public SerialModbusConnection(final String port) throws IOException {
        this.port = port;
        this.properties = new Properties();
        FileInputStream inputStream = new FileInputStream("serial.properties");
        properties.load(inputStream);
        inputStream.close();
        serialParameters = new SerialParameters(properties, null);
        serialParameters.setPortName(port);
        master = new ModbusSerialMaster(serialParameters);
    }

    public String getPort() {
        return port;
    }

    public ModbusSerialMaster getMaster() {
        return master;
    }

    private Properties getProperties() throws IOException {
        return properties;
    }
}
