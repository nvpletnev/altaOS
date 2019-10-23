import com.intelligt.modbus.jlibmodbus.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import jssc.SerialPortList;

public class Main {
    static public void main(String[] arg) {
        String[] ports = SerialPortList.getPortNames();

        SerialParameters serialParameters = new SerialParameters();
        serialParameters.setDevice(ports[0]);
        serialParameters.setBaudRate(com.intelligt.modbus.jlibmodbus.serial.SerialPort.BaudRate.BAUD_RATE_115200);
        serialParameters.setDataBits(8);
        serialParameters.setParity(com.intelligt.modbus.jlibmodbus.serial.SerialPort.Parity.NONE);
        serialParameters.setStopBits(1);

        try {
            ModbusMaster masterRTU = ModbusMasterFactory.createModbusMasterRTU(serialParameters);
            masterRTU.connect();
            int slaveId = 1;
            int offset = 0;
            int quantity = 16;

            while (true) {
                masterRTU.writeSingleRegister(slaveId, 0, 3);
                Thread.sleep(5000);
                masterRTU.writeSingleRegister(slaveId, 0, 2);
                Thread.sleep(5000);
            }
//            masterRTU.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
