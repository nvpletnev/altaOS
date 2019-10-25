package equipment.plsm;

import java.util.HashMap;
import java.util.Map;

//Модуль коммутации силовой нагрузки
public class Plsm {

    private final int slaveId;

    private final Map<String, Integer> registers = new HashMap<>() {{
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

    public Plsm(int slaveId) {
        this.slaveId = slaveId;
    }

    public Map<String, Integer> getRegisters() {
        return registers;
    }

    public int getSlaveId() {
        return slaveId;
    }

//    @Override
//    public int readSingleRegister(int slaveID, int register) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
//        int[] valueFromRegisters = connection.getMaster().readHoldingRegisters(slaveID, register, 1);
//        return valueFromRegisters[0];
//    }
//
//    @Override
//    public int[] readMultiplyRegisters(int slaveID, int... registers) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
//        int[] valueFromRegisters = new int[registers.length];
//        for (int i = 0; i < registers.length; i++) {
//            valueFromRegisters[i] = readSingleRegister(slaveID, i);
//        }
//        return valueFromRegisters;
//    }
//
//
//    @Override
//    public boolean writeSingleRegister(int slaveID, int register, int value) {
//        try {
//            connection.getMaster().writeSingleRegister(slaveID, register, value);
//            return true;
//        } catch (ModbusProtocolException | ModbusIOException | ModbusNumberException e) {
//            return false;
//        }
//    }
//
//    @Override
//    public boolean writeMultiplyRegisters(int slaveID, int[] register, int[] value) {
//        if (register.length != value.length) return false;
//        for (int i = 0; i < register.length; i++) {
//            writeSingleRegister(slaveID, register[i], value[i]);
//        }
//        return true;
//    }
}
