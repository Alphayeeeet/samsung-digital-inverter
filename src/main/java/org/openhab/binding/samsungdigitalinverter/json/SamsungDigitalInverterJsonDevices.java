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
 * The {@link SamsungDigitalInverterJsonDevices} class defines the Alarm Structure Samsung Digital Inverter
 *
 * @author Jan Grønlien - Initial contribution
 */

public class SamsungDigitalInverterJsonDevices {
    private String uuid;
    private String id;
    private String name;

    private List<SamsungDigitalInverterJsonTemperatures> Temperatures;
    private SamsungDigitalInverterJsonOperation Operation;

    private String type;

    private SamsungDigitalInverterJsonDiagnosis Diagnosis;
    private SamsungDigitalInverterJsonMode Mode;
    private SamsungDigitalInverterJsonWind Wind;

    private String description;

    private SamsungDigitalInverterJsonLink InformationLink;
    private SamsungDigitalInverterJsonEnergyConsumption EnergyConsumption;

    private List<String> resources;

    private String connected;
    private List<SamsungDigitalInverterJsonAlarms> Alarms;

    private SamsungDigitalInverterJsonLink ConfigurationLink;

    public SamsungDigitalInverterJsonDevices() {
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the temperatures
     */
    public List<SamsungDigitalInverterJsonTemperatures> getTemperatures() {
        return Temperatures;
    }

    /**
     * @param temperatures the temperatures to set
     */
    public void setTemperatures(List<SamsungDigitalInverterJsonTemperatures> temperatures) {
        Temperatures = temperatures;
    }

    /**
     * @return the operation
     */
    public SamsungDigitalInverterJsonOperation getOperation() {
        return Operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(SamsungDigitalInverterJsonOperation operation) {
        Operation = operation;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the diagnosis
     */
    public SamsungDigitalInverterJsonDiagnosis getDiagnosis() {
        return Diagnosis;
    }

    /**
     * @param diagnosis the diagnosis to set
     */
    public void setDiagnosis(SamsungDigitalInverterJsonDiagnosis diagnosis) {
        Diagnosis = diagnosis;
    }

    /**
     * @return the mode
     */
    public SamsungDigitalInverterJsonMode getMode() {
        return Mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(SamsungDigitalInverterJsonMode mode) {
        Mode = mode;
    }

    /**
     * @return the wind
     */
    public SamsungDigitalInverterJsonWind getWind() {
        return Wind;
    }

    /**
     * @param wind the wind to set
     */
    public void setWind(SamsungDigitalInverterJsonWind wind) {
        Wind = wind;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the informationLink
     */
    public SamsungDigitalInverterJsonLink getInformationLink() {
        return InformationLink;
    }

    /**
     * @param informationLink the informationLink to set
     */
    public void setInformationLink(SamsungDigitalInverterJsonLink informationLink) {
        InformationLink = informationLink;
    }

    /**
     * @return the energyConsumption
     */
    public SamsungDigitalInverterJsonEnergyConsumption getEnergyConsumption() {
        return EnergyConsumption;
    }

    /**
     * @param energyConsumption the energyConsumption to set
     */
    public void setEnergyConsumption(SamsungDigitalInverterJsonEnergyConsumption energyConsumption) {
        EnergyConsumption = energyConsumption;
    }

    /**
     * @return the resources
     */
    public List<String> getResources() {
        return resources;
    }

    /**
     * @param resources the resources to set
     */
    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    /**
     * @return the connected
     */
    public String getConnected() {
        return connected;
    }

    /**
     * @param connected the connected to set
     */
    public void setConnected(String connected) {
        this.connected = connected;
    }

    /**
     * @return the alarms
     */
    public List<SamsungDigitalInverterJsonAlarms> getAlarms() {
        return Alarms;
    }

    /**
     * @param alarms the alarms to set
     */
    public void setAlarms(List<SamsungDigitalInverterJsonAlarms> alarms) {
        Alarms = alarms;
    }

    /**
     * @return the configurationLink
     */
    public SamsungDigitalInverterJsonLink getConfigurationLink() {
        return ConfigurationLink;
    }

    /**
     * @param configurationLink the configurationLink to set
     */
    public void setConfigurationLink(SamsungDigitalInverterJsonLink configurationLink) {
        ConfigurationLink = configurationLink;
    }
}
