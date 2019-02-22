/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openhab.binding.samsungdigitalinverter.handler;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.smarthome.core.types.Command;

/**
 *
 * The {@link SamsungDigitalInverterCommands} class defines the json commands for communication with Samsung Digital
 * Inverter
 *
 * @author Jan Grønlien - Initial contribution
 */

public class SamsungDigitalInverterCommands {

    public static JsonCommand createPowerCommand(@NonNull Command command) {
        JsonCommand json = new JsonCommand();
        String state = "";
        switch (command.toString()) {
            case "OFF":
                state = "Off";
                break;
            case "ON":
                state = "On";
                break;
        }
        json.setPath("/devices/0");
        json.setJson(String.format("{\"Operation\": {\"power\": \"%s\"}}", state));
        return json;
    }

    public static JsonCommand createDesiredTemperatureCommand(@NonNull Command command) {
        JsonCommand json = new JsonCommand();
        json.setPath("/devices/0/temperatures/0");
        json.setJson(String.format("{\"desired\": %s}", command.toString().split(" ")[0]));
        return json;
    }

    public static JsonCommand createDesiredWindSpeedCommand(@NonNull Command command) {
        JsonCommand json = new JsonCommand();
        json.setPath("/devices/0/wind");
        json.setJson(String.format("{\"speedLevel\": %s}", command.toString()));
        return json;
    }

    public static JsonCommand createMaxWindSpeedCommand(@NonNull Command command) {
        JsonCommand json = new JsonCommand();
        json.setPath("/devices/0/wind");
        json.setJson(String.format("{\"maxSpeedLevel\": %s}", command.toString()));
        return json;
    }

    public static JsonCommand createWindDirectionCommand(@NonNull Command command) {
        JsonCommand json = new JsonCommand();
        json.setPath("/devices/0/wind");
        json.setJson(String.format("{\"direction\": \"%s\"}", command.toString()));
        return json;
    }

    public static JsonCommand createSetOperatingModeCommand(@NonNull Command command) {
        JsonCommand json = new JsonCommand();
        json.setPath("/devices/0/mode");
        json.setJson(String.format("{\"modes\":[\"%s\"]}", command.toString()));
        return json;
    }

    public static JsonCommand createSetComodeCommand(@NonNull Command command) {
        JsonCommand json = new JsonCommand();
        json.setPath("/devices/0/mode");
        json.setJson(String.format("{\"options\":[\"%s\"]}", command.toString()));
        return json;
    }

    public static JsonCommand createSetAutoCleanCommand(@NonNull Command command) {
        JsonCommand json = new JsonCommand();
        json.setPath("/devices/0/mode");
        if ("ON".equals(command.toString())) {
            json.setJson("{\"options\":[\"Autoclean_On\"]}");
        } else {
            json.setJson("{\"options\":[\"Autoclean_Off\"]}");
        }
        return json;
    }

    public static JsonCommand createSetBeepCommand(@NonNull Command command) {
        JsonCommand json = new JsonCommand();
        json.setPath("/devices/0/mode");
        if ("ON".equals(command.toString())) {
            json.setJson("{\"options\":[\"Volume_100\"]}");
        } else {
            json.setJson("{\"options\":[\"Volume_Mute\"]}");
        }
        return json;
    }

    public static JsonCommand createResetFilterCleanAlarm(@NonNull Command command) {
        JsonCommand json = new JsonCommand();
        json.setPath("/devices/0/mode");
        json.setJson("{\"options\":[\"FilterCleanAlarm_1\"]}");
        return json;
    }
}
