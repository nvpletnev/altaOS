package equipment;

import equipment.plsm.Plsm;
import net.wimpi.modbus.procimg.Register;

public abstract class Equipment {

    private Plsm plsm;
    private int slaveId;
    private int channel;
    private boolean isWork;
    private int error;
    private int currentConsumption;
    private int currentLimit;

    public Equipment(Plsm plsm, String channel) throws Exception {
        Register[] registers = plsm.getRegisters();
        this.plsm = plsm;
        this.slaveId = plsm.getSlaveId();
        this.channel = plsm.getRegistersMap().get(channel);
        this.error = registers[plsm.getRegistersMap().get("error")].getValue();
        switch (this.channel) {
            case 0:
                int ch0 = registers[plsm.getRegistersMap().get("channel0")].getValue();
                this.currentConsumption = registers[plsm.getRegistersMap().get("channelCurrentConsumption1")].getValue();
                this.currentLimit = registers[plsm.getRegistersMap().get("currentConsumptionLimitChannel1")].getValue();
                this.isWork = ch0 != 0 && ch0 != 3;
                break;
            case 1:
                this.currentConsumption = registers[plsm.getRegistersMap().get("channelCurrentConsumption2")].getValue();
                this.currentLimit = registers[plsm.getRegistersMap().get("currentConsumptionLimitChannel2")].getValue();
                this.isWork = registers[plsm.getRegistersMap().get("channel1")].getValue() > 0;
                break;
            case 2:
                this.currentConsumption = registers[plsm.getRegistersMap().get("channelCurrentConsumption3")].getValue();
                this.currentLimit = registers[plsm.getRegistersMap().get("currentConsumptionLimitChannel3")].getValue();
                this.isWork = registers[plsm.getRegistersMap().get("channel2")].getValue() > 0;
                break;
        }
    }

    public abstract boolean turnOn();

    public abstract boolean turnOff();

    public Plsm getPlsm() {
        return plsm;
    }

    public int getSlaveId() {
        return slaveId;
    }

    public int getChannel() {
        return channel;
    }

    public boolean isWork() throws Exception {
        this.plsm.updateRegisters();
        Register[] registers = this.plsm.getRegisters();
        Register register = registers[channel];
        this.isWork = register.getValue() == 0 || (register.getValue() == 3);
        return isWork;
    }

    public int getError() throws Exception {
        this.plsm.updateRegisters();
        Register[] registers = this.plsm.getRegisters();
        Register register = registers[plsm.getRegistersMap().get("error")];
        this.error = register.getValue();
        return error;
    }

    public int getCurrentConsumption() throws Exception {
        this.plsm.updateRegisters();
        Register[] registers = this.plsm.getRegisters();
        Register register = registers[channel + 3];
        currentConsumption = register.getValue();
        return currentConsumption;
    }

    public int getCurrentLimit() throws Exception {
        this.plsm.updateRegisters();
        Register[] registers = this.plsm.getRegisters();
        Register register = registers[channel + 7];
        currentLimit = register.getValue();
        return currentLimit;
    }
}
