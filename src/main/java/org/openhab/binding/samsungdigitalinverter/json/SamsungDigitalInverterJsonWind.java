/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
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
 * The {@link SamsungDigitalInverterJsonWind} class defines the Wind Structure Samsung Digital Inverter
 *
 * @author Jan Grønlien - Initial contribution
 */

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
