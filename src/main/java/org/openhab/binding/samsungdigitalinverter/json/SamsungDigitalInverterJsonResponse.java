/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openhab.binding.samsungdigitalinverter.json;

import java.math.BigDecimal;
import java.util.List;

import org.openhab.binding.samsungdigitalinverter.handler.PowerUsage;

/**
 *
 * The {@link SamsungDigitalInverterJsonResponse} class defines the Response Structure Samsung Digital Inverter
 * The class is the base class for the json response
 *
 * @author Jan Grønlien - Initial contribution
 */

public class SamsungDigitalInverterJsonResponse {

    private List<SamsungDigitalInverterJsonDevices> Devices;

    private PowerUsage powerUsage;

    private BigDecimal powerUsageDifference;

    /**
     * @return the previousPowerUsage
     */
    public BigDecimal getPowerUsageDifference() {
        return powerUsageDifference;
    }

    /**
     * @param previousPowerUsage the previousPowerUsage to set
     */
    public void setPowerUsageDifference(BigDecimal powerUsageDifference) {
        this.powerUsageDifference = powerUsageDifference;
    }

    public SamsungDigitalInverterJsonResponse() {
    }

    public List<SamsungDigitalInverterJsonDevices> getDevices() {
        return Devices;
    }

    public PowerUsage getPowerUsage() {
        return powerUsage;
    }

    public void setPowerUsage(PowerUsage powerUsage) {
        this.powerUsage = powerUsage;
    }

}
