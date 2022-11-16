/**
 * Copyright (c) 2010-2022 Contributors to the openHAB project
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

package org.openhab.binding.samsungdigitalinverter.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 *
 * The {@link SamsungDigitalInverterConfiguration} class is the configuration class for the Samsung Digital Inverter
 *
 * @author Jan Grønlien - Initial contribution
 */
@NonNullByDefault
public class SamsungDigitalInverterConfiguration {
    // Access token used as key to control Device
    public String bearer = "";

    // IP address of Device
    public String ip = "";

    // Port number for device, default 8888
    public Integer port = 8888;

    // Refresh interval in seconds
    public Integer refresh = 900;

    // keystore
    public String keystore = "";

    // keystore password
    public String keystore_secret = "";
}
