package config;

import com.intelligt.modbus.jlibmodbus.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.data.DataHolder;
import com.intelligt.modbus.jlibmodbus.data.ModbusHoldingRegisters;
import com.intelligt.modbus.jlibmodbus.data.SlaveId;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.msg.request.ReadHoldingRegistersRequest;
import com.intelligt.modbus.jlibmodbus.msg.response.ReadHoldingRegistersResponse;
import com.intelligt.modbus.jlibmodbus.serial.SerialPortException;
import connection.ModBusConnection;
import exception.ComPortsNotFound;
import jssc.SerialPortList;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Stream;

public class ProgrammConfigurationTest {

    @Test
    public void getProperties() throws ModbusIOException, ComPortsNotFound, SerialPortException, IOException, ModbusNumberException, ModbusProtocolException {
        ProgrammConfiguration configuration = new ProgrammConfiguration();
        Properties properties = configuration.getProperties();
        Assert.assertEquals("1", properties.getProperty("SlaveId"));
    }

    @Test
    public void testConnection() throws SerialPortException, ModbusIOException, ModbusNumberException, ModbusProtocolException, InterruptedException {
        String[] ports = SerialPortList.getPortNames();
        ModBusConnection connection = new ModBusConnection(ports[0]);
        ModbusMaster master = connection.getMaster();
        master.connect();

        //make request
        ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest();
        request.setServerAddress(1);
        request.setStartAddress(0);
        request.setQuantity(16);

        //make response
        ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) request.getResponse();
        master.processRequest(request);

        //get registers
        ModbusHoldingRegisters registers = response.getHoldingRegisters();

        for (int i = 0; i < registers.getRegisters().length; i++) {
            System.out.printf("номер регистра - %d, значение регистра - %d\n", i, registers.get(i));
        }

        master.disconnect();
    }
}