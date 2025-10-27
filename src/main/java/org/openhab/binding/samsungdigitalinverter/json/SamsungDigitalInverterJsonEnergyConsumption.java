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

/**
 *
 * The {@link SamsungDigitalInverterJsonDevices} class defines the Alarm Structure Samsung Digital Inverter
 *
 * @author Jan Grønlien - Initial contribution
 */

public class SamsungDigitalInverterJsonEnergyConsumption {
    private String saveLocation;

    public SamsungDigitalInverterJsonEnergyConsumption() {
    }

    /**
     * @return the saveLocation
     */
    public String getSaveLocation() {
        return saveLocation;
    }

    /**
     * @param saveLocation the saveLocation to set
     */
    public void setSaveLocation(String saveLocation) {
        this.saveLocation = saveLocation;
    }
}
