package org.openhab.binding.samsungdigitalinverter.json;

public class SamsungDigitalInverterJsonWind {
    private Integer speedLevel;
    private String direction;
    private Integer maxSpeedLevel;

    /**
     * @return the speedLevel
     */
    public Integer getSpeedLevel() {
        return speedLevel;
    }

    /**
     * @param speedLevel the speedLevel to set
     */
    public void setSpeedLevel(Integer speedLevel) {
        this.speedLevel = speedLevel;
    }

    /**
     * @return the direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * @return the maxSpeedLevel
     */
    public Integer getMaxSpeedLevel() {
        return maxSpeedLevel;
    }

    /**
     * @param maxSpeedLevel the maxSpeedLevel to set
     */
    public void setMaxSpeedLevel(Integer maxSpeedLevel) {
        this.maxSpeedLevel = maxSpeedLevel;
    }

    public SamsungDigitalInverterJsonWind() {
    }

}
