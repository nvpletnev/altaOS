package interfaces;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;

public interface ModbusWritable {

    boolean writeSingleRegister(int slaveID, int register, int value);

    boolean writeMultiplyRegisters(int slaveID, int[] register, int[] value);
}
