package equipment.plsm;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import connection.ModBusConnection;

import java.util.HashMap;
import java.util.Map;

public class PlsmFactory {
    //todo Найти все мксн в сети модбас и вернуть объект из hashMap по ключу (slaveID)
    // send broadcast message (get value from register 10)
    private static Map<Integer, Plsm> plsms = new HashMap<>();

    private PlsmFactory() {
    }

    public static void createDevices(ModBusConnection connection) {
        try {
            connection.getMaster().connect();

            connection.getMaster().disconnect();
        } catch (ModbusIOException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.getMaster().disconnect();
            } catch (ModbusIOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<Integer, Plsm> getPlsms() {
        return plsms;
    }
}
