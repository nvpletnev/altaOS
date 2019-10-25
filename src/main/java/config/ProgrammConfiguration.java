package config;

import com.intelligt.modbus.jlibmodbus.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.data.ModbusHoldingRegisters;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.msg.request.ReadHoldingRegistersRequest;
import com.intelligt.modbus.jlibmodbus.msg.response.ReadHoldingRegistersResponse;
import com.intelligt.modbus.jlibmodbus.serial.SerialPortException;
import connection.ModBusConnection;
import exception.ComPortsNotFound;
import jssc.SerialPortList;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * This class initializes the configuration file.
 * First, all ports are initialized to <code>String[] ports</code>
 * If there is no data in the configuration file, then the program terminates
 * File slaves.properties contains all devices
 **/

public class ProgrammConfiguration {

    private final Properties properties = new Properties();
    private OutputStream outputStream = new FileOutputStream("slaves.properties");
    private ModBusConnection connection;
    private String[] ports = SerialPortList.getPortNames();
    private ModbusHoldingRegisters registers;

    public ProgrammConfiguration() throws IOException, ComPortsNotFound, SerialPortException, ModbusIOException, ModbusNumberException, ModbusProtocolException {

        if (ports.length == 0) throw new ComPortsNotFound("Com-port not found!");

        //todo Опросить все устройства в сети modbus на каждом порту

        for (String port : ports) {
            //open connection
            connection = new ModBusConnection(port);
            ModbusMaster master = connection.getMaster();
            master.connect();

            registers = getRegisters(port, 1);
            properties.setProperty("SlaveId", String.valueOf(registers.get(10)));

            //close connection
            connection.getMaster().disconnect();
        }
        properties.store(outputStream, null);
        outputStream.close();
    }

    public Properties getProperties() {
        return properties;
    }

    private ModbusHoldingRegisters getRegisters(String port, int slaveID) throws SerialPortException, ModbusIOException, ModbusNumberException, ModbusProtocolException {

        //make request to 0-15 registers
        ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest();
        request.setServerAddress(slaveID);
        request.setStartAddress(0);
        request.setQuantity(16);

        //make response
        connection.getMaster().processRequest(request);
        ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) request.getResponse();

        //get registers
        ModbusHoldingRegisters registers = response.getHoldingRegisters();

        return registers;
    }
}
