package org.openhab.binding.samsungdigitalinverter.json;

public class SamsungDigitalInverterJsonTemperatures {
    private String id;
    private Integer maximum;
    private Integer current;
    private Integer minimum;
    private String unit;

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
     * @return the maximum
     */
    public Integer getMaximum() {
        return maximum;
    }

    /**
     * @param maximum the maximum to set
     */
    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    /**
     * @return the current
     */
    public Integer getCurrent() {
        return current;
    }

    /**
     * @param current the current to set
     */
    public void setCurrent(Integer current) {
        this.current = current;
    }

    /**
     * @return the minimum
     */
    public Integer getMinimum() {
        return minimum;
    }

    /**
     * @param minimum the minimum to set
     */
    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }

    /**
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return the desired
     */
    public Integer getDesired() {
        return desired;
    }

    /**
     * @param desired the desired to set
     */
    public void setDesired(Integer desired) {
        this.desired = desired;
    }

    private Integer desired;
}
