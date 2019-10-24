package interfaces;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;

public interface ModbusReadable {

    int readSingleRegister(int slaveID, int register) throws ModbusNumberException, ModbusProtocolException, ModbusIOException;

    int[] readMultiplyRegisters(int slaveID, int... registers) throws ModbusNumberException, ModbusProtocolException, ModbusIOException;
}
