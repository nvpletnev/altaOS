package connection;

import com.intelligt.modbus.jlibmodbus.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import com.intelligt.modbus.jlibmodbus.serial.SerialPort;
import com.intelligt.modbus.jlibmodbus.serial.SerialPortException;

public class ModBusConnection {

    private final ModbusMaster master;

    public ModBusConnection(final String portName) throws SerialPortException {

        SerialParameters serialParameters = new SerialParameters();
        serialParameters.setDevice(portName);
        serialParameters.setBaudRate(SerialPort.BaudRate.BAUD_RATE_115200);
        serialParameters.setParity(SerialPort.Parity.NONE);
        serialParameters.setDataBits(8);
        serialParameters.setStopBits(1);
        this.master = ModbusMasterFactory.createModbusMasterRTU(serialParameters);
        master.setResponseTimeout(1000);
    }

    public ModbusMaster getMaster() {
        return master;
    }
}
