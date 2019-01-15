/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.samsungdigitalinverter;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.measure.Unit;
import javax.measure.quantity.Dimensionless;
import javax.measure.quantity.Temperature;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.library.unit.SIUnits;
import org.eclipse.smarthome.core.library.unit.SmartHomeUnits;
import org.eclipse.smarthome.core.thing.ThingTypeUID;

@NonNullByDefault
public class SamsungDigitalInverterConstants {

    public static final String DEVICES = "/devices";

    public static final String BINDING_ID = "samsungdigitalinverter";
    public static final String LOCAL = "local";

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_SDI = new ThingTypeUID(BINDING_ID, "sdi");

    // List of all Channel id's
    public static final String TEMPERATURE_CURRENT = "temperature";
    public static final String SETPOINT_TEMPERATURE = "setpoint_temperature";
    public static final String POWER = "power";
    public static final String WIND_DIRECTION = "winddirection";
    public static final String WIND_SPEED = "windspeed";
    public static final String OPERATING_MODE = "operation_mode";
    public static final String ALARM = "alarm";

    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Collections.singleton(THING_TYPE_SDI);
    public static final Set<String> SUPPORTED_CHANNEL_IDS = Stream
            .of(SETPOINT_TEMPERATURE, TEMPERATURE_CURRENT, POWER, WIND_DIRECTION, WIND_SPEED, OPERATING_MODE, ALARM)
            .collect(Collectors.toSet());

    // Units of measurement of the data delivered by the API
    public static final Unit<Temperature> SDI_TEMPERATURE_UNIT = SIUnits.CELSIUS;
    public static final Unit<Dimensionless> SDI_HUMIDITY_UNIT = SmartHomeUnits.PERCENT;

    // Power Constants
    public static final Integer POWER_ON = 1;
    public static final Integer POWER_OFF = 0;

}
