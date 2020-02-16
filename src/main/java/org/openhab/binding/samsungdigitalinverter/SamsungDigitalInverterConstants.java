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

/**
 *
 * The {@link SamsungDigitalInverterConstants} class defines common constants
 *
 * @author Jan Grønlien - Initial contribution
 */

@NonNullByDefault
public class SamsungDigitalInverterConstants {

    public static final String DEVICES = "/devices";
    public static final String USAGE_DB = "/files/usage.db";

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
    public static final String MAX_WIND_SPEED = "max_windspeed";
    public static final String OPERATING_MODE = "operation_mode";
    public static final String ALARM = "alarm";
    public static final String COMODE = "comode";
    public static final String FILTERTIME = "filtertime";
    public static final String FILTER_ALARMTIME = "filteralarmtime";
    public static final String AUTOCLEAN = "autoclean";
    public static final String OPTIONS = "options";
    public static final String OUTDOOR_TEMPERATURE = "outdoor_temperature";
    public static final String BEEP = "beep";
    public static final String RESET_FILTER_CLEAN_ALARM = "reset_filter_clean_alarm";
    public static final String RUNNING_TIME = "running_time";
    public static final String POWER_USAGE = "power_usage";

    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Collections.singleton(THING_TYPE_SDI);
    public static final Set<String> SUPPORTED_CHANNEL_IDS = Stream.of(SETPOINT_TEMPERATURE, TEMPERATURE_CURRENT, POWER,
            WIND_DIRECTION, WIND_SPEED, OPERATING_MODE, ALARM, MAX_WIND_SPEED, COMODE, FILTERTIME, FILTER_ALARMTIME,
            AUTOCLEAN, OUTDOOR_TEMPERATURE, BEEP, RESET_FILTER_CLEAN_ALARM, RUNNING_TIME, POWER_USAGE)
            .collect(Collectors.toSet());

    // Units of measurement of the data delivered by the API
    public static final Unit<Temperature> SDI_TEMPERATURE_UNIT = SIUnits.CELSIUS;
    public static final Unit<Dimensionless> SDI_HUMIDITY_UNIT = SmartHomeUnits.PERCENT;

    // Power Constants
    public static final Integer ON = 1;
    public static final Integer OFF = 0;

}
