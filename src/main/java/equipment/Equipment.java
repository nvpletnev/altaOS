package equipment;

import equipment.plsm.Plsm;

public abstract class Equipment {

    private Plsm plsm;
    private int slaveId;
    private int channel;
    private boolean isWork;
    private int error;
    private int currentConsumption;
    private int setpointCurrentConsumption;

    public Equipment(Plsm plsm, String channel) throws Exception {
        this.plsm = plsm;
        this.channel = plsm.getRegisters().get(channel);
        this.slaveId = plsm.getSlaveId();
        this.error = plsm.readValueFromRegister(plsm.getRegisters().get("error"));
        switch (this.channel) {
            case 0:
                this.currentConsumption = plsm.readValueFromRegister(plsm.getRegisters().get("channelCurrentConsumption1"));
                this.setpointCurrentConsumption = plsm.readValueFromRegister(plsm.getRegisters().get("currentConsumptionLimitChannel1"));
                if (plsm.readValueFromRegister(plsm.getRegisters().get("channel0")) == 0 ||
                        plsm.readValueFromRegister(plsm.getRegisters().get("channel0")) == 3) {
                    this.isWork = false;
                } else this.isWork = true;
                break;
            case 1:
                this.currentConsumption = plsm.readValueFromRegister(plsm.getRegisters().get("channelCurrentConsumption2"));
                this.setpointCurrentConsumption = plsm.readValueFromRegister(plsm.getRegisters().get("currentConsumptionLimitChannel2"));
                if (plsm.readValueFromRegister(plsm.getRegisters().get("channel1")) > 0) {
                    this.isWork = true;
                } else this.isWork = false;
                break;
            case 2:
                this.currentConsumption = plsm.readValueFromRegister(plsm.getRegisters().get("channelCurrentConsumption3"));
                this.setpointCurrentConsumption = plsm.readValueFromRegister(plsm.getRegisters().get("currentConsumptionLimitChannel3"));
                if (plsm.readValueFromRegister(plsm.getRegisters().get("channel2")) > 0) {
                    this.isWork = true;
                } else this.isWork = false;
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

    public boolean isWork() {
        return isWork;
    }

    public int getError() {
        return error;
    }

    public int getCurrentConsumption() {
        return currentConsumption;
    }

    public int getSetpointCurrentConsumption() {
        return setpointCurrentConsumption;
    }
}
