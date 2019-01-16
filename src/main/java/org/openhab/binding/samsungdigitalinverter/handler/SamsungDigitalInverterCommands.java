package org.openhab.binding.samsungdigitalinverter.handler;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.smarthome.core.types.Command;

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
}
