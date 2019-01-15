package org.openhab.binding.samsungdigitalinverter.json;

public class SamsungDigitalInverterJsonAlarms {
    private String triggeredTime;
    private String id;
    private String code;
    private String alarmType;

    /**
     * @return the triggeredTime
     */
    public String getTriggeredTime() {
        return triggeredTime;
    }

    /**
     * @param triggeredTime the triggeredTime to set
     */
    public void setTriggeredTime(String triggeredTime) {
        this.triggeredTime = triggeredTime;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the alarmType
     */
    public String getAlarmType() {
        return alarmType;
    }

    /**
     * @param alarmType the alarmType to set
     */
    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public SamsungDigitalInverterJsonAlarms() {
    }

}
