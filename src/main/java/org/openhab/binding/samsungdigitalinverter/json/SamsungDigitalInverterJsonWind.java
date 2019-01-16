/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
