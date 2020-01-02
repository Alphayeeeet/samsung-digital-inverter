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

package org.openhab.binding.samsungdigitalinverter.handler;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * The {@link PowerUsage} class handles the Power Usage
 *
 * @author Jan Grønlien - Initial contribution
 */

public class PowerUsage {

    LocalDateTime currentDate;
    BigDecimal currentPowerUsage;
    BigDecimal currentRunningTime;

    LocalDateTime previousDate;
    BigDecimal previousPowerUsage;
    BigDecimal previousRunningTime;

    BigDecimal ten = new BigDecimal("10");

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMddHH");

    /**
     * @return the date
     */
    public LocalDateTime getCurrentDate() {
        return currentDate;
    }

    /**
     * @param date the date to set
     * @throws ParseException
     */
    public void setCurrentDate(String date) throws ParseException {
        this.currentDate = LocalDateTime.parse(date, dateFormat);
    }

    /**
     * @return the powerUsage
     */
    public BigDecimal getCurrentPowerUsage() {
        return currentPowerUsage;
    }

    /**
     * @param powerUsage the powerUsage to set
     */
    public void setCurrentPowerUsage(BigDecimal powerUsage) {
        this.currentPowerUsage = powerUsage.divide(new BigDecimal(10)); // KWh
    }

    /**
     * @param powerUsage the powerUsage to set
     */
    public void setCurrentPowerUsage(String powerUsage) {
        this.setCurrentPowerUsage(new BigDecimal(powerUsage));
    }

    /**
     * @return the runningTime
     */
    public BigDecimal getCurrentRunningTime() {
        return currentRunningTime;
    }

    /**
     * @param runningTime the runningTime to set
     */
    public void setCurrentRunningTime(BigDecimal runningTime) {
        this.currentRunningTime = runningTime.divide(ten);
    }

    /**
     * @param runningTime the runningTime to set
     */
    public void setCurrentRunningTime(String runningTime) {
        setCurrentRunningTime(new BigDecimal(runningTime));
    }

    @Override
    public String toString() {
        return (currentDate.toString() + " - " + previousDate.toString() + " : " + currentPowerUsage.toString() + " - "
                + previousPowerUsage.toString() + " : " + currentRunningTime.toString() + " - "
                + previousRunningTime.toString());
    }

    /**
     * @return the previousDate
     */
    public LocalDateTime getPreviousDate() {
        return previousDate;
    }

    /**
     * @param date the previousDate to set
     */
    public void setPreviousDate(String date) {
        this.previousDate = LocalDateTime.parse(date, dateFormat);
        ;
    }

    /**
     * @return the previousPowerUsage
     */
    public BigDecimal getPreviousPowerUsage() {
        return previousPowerUsage;
    }

    /**
     * @param previousPowerUsage the previousPowerUsage to set
     */
    public void setPreviousPowerUsage(BigDecimal previousPowerUsage) {
        this.previousPowerUsage = previousPowerUsage.divide(ten); // KWh;
    }

    /**
     * @param previousPowerUsage the previousPowerUsage to set
     */
    public void setPreviousPowerUsage(String previousPowerUsage) {
        setPreviousPowerUsage(new BigDecimal(previousPowerUsage));
    }

    /**
     * @return the previousRunningTime
     */
    public BigDecimal getPreviousRunningTime() {
        return previousRunningTime;
    }

    /**
     * @param previousRunningTime the previousRunningTime to set
     */
    public void setPreviousRunningTime(BigDecimal runningTime) {
        this.previousRunningTime = runningTime.divide(ten);
    }

    /**
     * @param previousRunningTime the previousRunningTime to set
     */
    public void setPreviousRunningTime(String runningTime) {
        setPreviousRunningTime(new BigDecimal(runningTime));
    }

    public BigDecimal getDuration() {
        return (currentRunningTime.subtract(previousRunningTime));
    }

}
