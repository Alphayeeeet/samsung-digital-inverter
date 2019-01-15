/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.samsungdigitalinverter.json;

import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link SamsungDigitalInverterJsonIaqi} is responsible for storing
 * "iaqi" node from the waqi.org JSON response
 * It contains information on air pollution particles
 * as well as some basic weather metrics.
 *
 * @author Kuba Wolanin - Initial contribution
 * @author Łukasz Dywicki - Initial contribution
 */
public class SamsungDigitalInverterJsonIaqi {

    private SamsungDigitalInverterValue<BigDecimal> pm25;
    private SamsungDigitalInverterValue<BigDecimal> pm10;
    private SamsungDigitalInverterValue<BigDecimal> o3;
    private SamsungDigitalInverterValue<BigDecimal> no2;
    private SamsungDigitalInverterValue<BigDecimal> co;
    private SamsungDigitalInverterValue<BigDecimal> t;

    @SerializedName("p")
    private SamsungDigitalInverterValue<BigDecimal> pressure;
    private SamsungDigitalInverterValue<BigDecimal> h;

    @SerializedName("w")
    private SamsungDigitalInverterValue<BigDecimal> wind;

    public SamsungDigitalInverterJsonIaqi() {
    }

    public BigDecimal getPm25() {
        return pm25.getValue();
    }

    public BigDecimal getPm10() {
        return pm10.getValue();
    }

    public BigDecimal getO3() {
        return o3.getValue();
    }

    public BigDecimal getNo2() {
        return no2.getValue();
    }

    public BigDecimal getCo() {
        return co.getValue();
    }

    public BigDecimal getT() {
        return t.getValue();
    }

    public BigDecimal getP() {
        return pressure.getValue();
    }

    public BigDecimal getH() {
        return h.getValue();
    }

    public BigDecimal getW() {
        return wind.getValue();
    }

}
