package org.openhab.binding.samsungdigitalinverter.json;

import java.util.List;

public class SamsungDigitalInverterJsonMode {
    private List<String> supportedModes;
    private List<String> modes;
    private List<String> options;

    /**
     * @return the supportedModes
     */
    public List<String> getSupportedModes() {
        return supportedModes;
    }

    /**
     * @param supportedModes the supportedModes to set
     */
    public void setSupportedModes(List<String> supportedModes) {
        this.supportedModes = supportedModes;
    }

    /**
     * @return the modes
     */
    public List<String> getModes() {
        return modes;
    }

    /**
     * @param modes the modes to set
     */
    public void setModes(List<String> modes) {
        this.modes = modes;
    }

    /**
     * @return the options
     */
    public List<String> getOptions() {
        return options;
    }

    /**
     * @param options the options to set
     */
    public void setOptions(List<String> options) {
        this.options = options;
    }

    public SamsungDigitalInverterJsonMode() {
    }

}
