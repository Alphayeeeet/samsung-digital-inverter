/**
 * Copyright (c) 2010-2023 Contributors to the openHAB project
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
 * The {@link SamsungDigitalInverterJsonAlarms} class defines the Alarm Structure Samsung Digital Inverter
 *
 * @author Jan Grønlien - Initial contribution
 */

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
