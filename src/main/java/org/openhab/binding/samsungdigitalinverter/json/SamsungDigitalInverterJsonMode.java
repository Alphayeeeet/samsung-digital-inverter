/*
 * Copyright (c) 2010-2025 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.openhab.binding.samsungdigitalinverter.json;

import java.util.List;

/**
 *
 * The {@link SamsungDigitalInverterJsonMode} class defines the Link Structure Samsung Digital Inverter
 *
 * @author Jan Grønlien - Initial contribution
 */

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
