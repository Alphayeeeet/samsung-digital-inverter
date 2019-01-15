/**
 * Copyright (c) 2010-2019 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.samsungdigitalinverter.internal;

/**
 * The {@link SamsungDigitalInverterConfiguration} is the class used to match the
 * thing configuration.
 *
 * @author Jan Grønlien - Initial contribution
 */
public class SamsungDigitalInverterConfiguration {
    // Access token used as key to control Device
    public String bearer;

    // IP address of Device
    public String ip;

    // Port number for device, default 8888
    public Integer port;

    // Refresh interval in seconds
    public Integer refresh;

    // keystore
    public String keystore;

    // keystore password
    public String keystore_password;
}
