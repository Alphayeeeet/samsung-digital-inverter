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
 * The {@link SamsungDigitalInverterJsonDevices} class defines the Alarm Structure Samsung Digital Inverter
 *
 * @author Jan Grønlien - Initial contribution
 */

public class SamsungDigitalInverterJsonDiagnosis {
    private String diagnosisStart;

    /**
     * @return the diagnosisStart
     */
    public String getDiagnosisStart() {
        return diagnosisStart;
    }

    /**
     * @param diagnosisStart the diagnosisStart to set
     */
    public void setDiagnosisStart(String diagnosisStart) {
        this.diagnosisStart = diagnosisStart;
    }

    public SamsungDigitalInverterJsonDiagnosis() {
    }

}
