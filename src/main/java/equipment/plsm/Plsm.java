package equipment.plsm;

import connection.SerialModbusConnection;
import net.wimpi.modbus.facade.ModbusSerialMaster;
import net.wimpi.modbus.procimg.Register;
import net.wimpi.modbus.procimg.SimpleRegister;

import java.util.HashMap;
import java.util.Map;

//Модуль коммутации силовой нагрузки
public class Plsm {
    private final static int COUNT_REGISTERS = 16;

    private final int slaveId;
    private final String port;
    private final SerialModbusConnection connection;
    private final Map<String, Integer> registersMap = new HashMap<>() {{
        put("channel1", 0);
        put("channel2", 1);
        put("channel3", 2);
        put("channelCurrentConsumption1", 3);
        put("channelCurrentConsumption2", 4);
        put("channelCurrentConsumption3", 5);
        put("deviceTemperature", 6);
        put("currentConsumptionLimitChannel1", 7);
        put("currentConsumptionLimitChannel2", 8);
        put("currentConsumptionLimitChannel3", 9);
        put("modbusAddress", 10);
        put("baudrate", 11);
        put("stopBit", 12);
        put("inputCurrent", 13);
        put("totalPowerConsumption", 14);
        put("error", 15);
    }};
    private Register[] registers;

    public Plsm(String port, int slaveId) throws Exception {
        this.port = port;
        this.slaveId = slaveId;
        this.connection = new SerialModbusConnection(port);
        this.registers = readMultipleValues(slaveId, 0, COUNT_REGISTERS);
    }

    public Register[] getRegisters() {
        return registers;
    }

    public void updateRegisters() throws Exception {
        registers = readMultipleValues(slaveId, 0, COUNT_REGISTERS);
    }

    public SerialModbusConnection getConnection() {
        return connection;
    }

    public String getPort() {
        return port;
    }

    public Map<String, Integer> getRegistersMap() {
        return registersMap;
    }

    public int getSlaveId() {
        return slaveId;
    }

    public void writeValue(int reg, int value) throws Exception {
        ModbusSerialMaster master = connection.getMaster();
        master.connect();
        Register register = new SimpleRegister();
        register.setValue(value);
        master.writeSingleRegister(slaveId, reg, register);
        master.disconnect();
    }

    private Register[] readMultipleValues(int slaveId, int offset, int count) throws Exception {
        ModbusSerialMaster master = connection.getMaster();
        master.connect();
        this.registers = master.readMultipleRegisters(slaveId, offset, count);
        master.disconnect();
        return this.registers;
    }
}
