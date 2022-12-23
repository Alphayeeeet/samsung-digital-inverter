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

package org.openhab.binding.samsungdigitalinverter.handler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.ZonedDateTime;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.samsungdigitalinverter.SamsungDigitalInverterConstants;
import org.openhab.binding.samsungdigitalinverter.internal.SamsungDigitalInverterConfiguration;
import org.openhab.binding.samsungdigitalinverter.json.SamsungDigitalInverterJsonResponse;
import org.openhab.core.library.types.DateTimeType;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.PointType;
import org.openhab.core.library.types.QuantityType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.thing.Channel;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;
import org.openhab.core.types.State;
import org.openhab.core.types.UnDefType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 *
 * The {@link SamsungDigitalInverterHandler} class is the core class for the Samsung Digital Inverter
 *
 * @author Jan Grønlien - Initial contribution
 */
public class SamsungDigitalInverterHandler extends BaseThingHandler {

    private Logger logger = LoggerFactory.getLogger(SamsungDigitalInverterHandler.class);

    private @Nullable SamsungDigitalInverterConfiguration config;

    private static final int DEFAULT_REFRESH_PERIOD = 300;

    private ScheduledFuture<?> refreshJob;

    private @Nullable SamsungDigitalInverterJsonResponse sdiResponse;

    private Gson gson;

    private @Nullable HostnameVerifier allHostsValid;

    private @Nullable KeyManager[] keyManagers;

    private @Nullable TrustManager[] trustManagers;

    private @Nullable SSLContext sslContext;

    private int errorCounter = 0;

    public SamsungDigitalInverterHandler(Thing thing) {
        super(thing);
        gson = new Gson();
    }

    @Override
    protected void updateStatus(@NonNull ThingStatus status) {
        logger.info("updateStatus: {}", status);
        super.updateStatus(status);
    }

    @Override
    protected void updateStatus(@NonNull ThingStatus status, @NonNull ThingStatusDetail statusDetail) {
        logger.info("updateStatus: {} {}", status, statusDetail);
        super.updateStatus(status, statusDetail);
    }

    @Override
    protected void updateStatus(@NonNull ThingStatus status, @NonNull ThingStatusDetail statusDetail,
            @Nullable String description) {
        logger.info("updateStatus: {} {} {}", status, statusDetail, statusDetail);
        super.updateStatus(status, statusDetail, description);
    }

    @Override
    public void initialize() {
        logger.debug("Initializing Samsung Digital Inverter handler.");

        config = getConfigAs(SamsungDigitalInverterConfiguration.class);
        logger.debug("{}", "config bearer = (omitted from logging)");
        logger.debug("config ip = {}", config.ip);
        logger.debug("config port = {}", config.port);
        logger.debug("config refresh = {}", config.refresh);
        logger.debug("config keystore = {}", config.keystore);
        logger.debug("config keystore secret = (omitted from logging)");

        String errorMsg = null;

        try {
            allHostsValid = createrHostnameVerifier();
            keyManagers = createKeyManagers(config.keystore, config.keystore_secret);
            trustManagers = createTrustAll();

            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(keyManagers, trustManagers, new java.security.SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        } catch (Exception e) {
            errorMsg = e.getMessage();
        }

        if (errorMsg == null) {
            updateStatus(ThingStatus.ONLINE);
            startAutomaticRefresh();
        } else {
            logger.error("samsungdigitalinverter.handler.initialize: {}", errorMsg);
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, errorMsg);
        }
    }

    /**
     * Start the job refreshing the Samsung Digital Inverter data
     */
    private void startAutomaticRefresh() {
        if (refreshJob == null || refreshJob.isCancelled()) {
            Runnable runnable = () -> {
                try {
                    logger.debug("Starting Refresh");
                    // Call Samsung Digital Inverter to get data
                    if (this.getThing().getStatus() == ThingStatus.ONLINE) {
                        sdiResponse = getSamsungDigitalInverterData();
                        if (sdiResponse != null) {
                            // Update all channels
                            for (Channel channel : getThing().getChannels()) {
                                updateChannel(channel.getUID().getId(), sdiResponse);
                            }
                        } else {
                            logger.warn("{}", "getSamsungDigitalInverterData() returned null ");
                        }
                    }
                } catch (Exception e) {
                    logger.error("Exception occurred during execution: {}", e.getMessage(), e);
                }
            };

            SamsungDigitalInverterConfiguration config = getConfigAs(SamsungDigitalInverterConfiguration.class);
            int delay = (config.refresh != null) ? config.refresh.intValue() : DEFAULT_REFRESH_PERIOD;
            refreshJob = scheduler.scheduleWithFixedDelay(runnable, 0, delay, TimeUnit.SECONDS);
        }
    }

    @Override
    public void dispose() {
        logger.debug("Disposing the Samsung Digital Inverter handler.");

        if (refreshJob != null && !refreshJob.isCancelled()) {
            refreshJob.cancel(true);
            refreshJob = null;
        }
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (command instanceof RefreshType) {
            updateChannel(channelUID.getId(), sdiResponse);
        } else {
            String channel = channelUID.getId();
            JsonCommand sdiCommand = null;
            switch (channel) {
                case SamsungDigitalInverterConstants.SETPOINT_TEMPERATURE:
                    sdiCommand = SamsungDigitalInverterCommands.createDesiredTemperatureCommand(command);
                    break;
                case SamsungDigitalInverterConstants.POWER:
                    sdiCommand = SamsungDigitalInverterCommands.createPowerCommand(command);
                    break;
                case SamsungDigitalInverterConstants.WIND_DIRECTION:
                    sdiCommand = SamsungDigitalInverterCommands.createWindDirectionCommand(command);
                    break;
                case SamsungDigitalInverterConstants.WIND_SPEED:
                    sdiCommand = SamsungDigitalInverterCommands.createDesiredWindSpeedCommand(command);
                    break;
                case SamsungDigitalInverterConstants.MAX_WIND_SPEED:
                    sdiCommand = SamsungDigitalInverterCommands.createMaxWindSpeedCommand(command);
                    break;
                case SamsungDigitalInverterConstants.OPERATING_MODE:
                    sdiCommand = SamsungDigitalInverterCommands.createSetOperatingModeCommand(command);
                    break;
                case SamsungDigitalInverterConstants.COMODE:
                    sdiCommand = SamsungDigitalInverterCommands.createSetComodeCommand(command);
                    break;
                case SamsungDigitalInverterConstants.BEEP:
                    sdiCommand = SamsungDigitalInverterCommands.createSetBeepCommand(command);
                    break;
                case SamsungDigitalInverterConstants.AUTOCLEAN:
                    sdiCommand = SamsungDigitalInverterCommands.createSetAutoCleanCommand(command);
                    break;
                case SamsungDigitalInverterConstants.RESET_FILTER_CLEAN_ALARM:
                    sdiCommand = SamsungDigitalInverterCommands.createResetFilterCleanAlarm(command);
                    break;
            }
            logger.debug("The Samsung Digital Inverter handle command {} -> {}", channelUID, command);
            if (sdiCommand != null) {
                try {
                    logger.debug("To execute command: {} {}", sdiCommand.getPath(), sdiCommand.getJson());
                    updateService(sdiCommand);
                } catch (IOException e) {
                    logger.error("handleCommand: {}", e.getMessage());
                }
            }
        }
    }

    /**
     * @param sdiCommand
     * @throws IOException
     */
    private void updateService(JsonCommand sdiCommand) throws IOException {
        URL url = createURL(sdiCommand.getPath());

        logger.debug("updateService URL = {}", url);

        HttpsURLConnection con = openConnection(url);
        con.setRequestMethod("PUT");
        con.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(sdiCommand.getJson());
        wr.flush();

        int httpResult = con.getResponseCode();
        logger.debug("updateService {} with {} returned {}", sdiCommand.getPath(), sdiCommand.getJson(), httpResult);
    }

    /**
     *
     * Update with response from Samsung Digital Inverter
     *
     * @param channelId channel to update
     * @param sdiResponse Class representing the json response from the Samsung Digital Inverter
     */
    private void updateChannel(String channelId, SamsungDigitalInverterJsonResponse sdiResponse) {
        if (isLinked(channelId)) {
            Object value;
            try {
                value = getValue(channelId, sdiResponse);
            } catch (Exception e) {
                logger.debug("Device doesn't provide {} measurement", channelId.toUpperCase());
                return;
            }

            State state = null;
            if (value == null) {
                state = UnDefType.UNDEF;
            } else if (value instanceof PointType) {
                state = (PointType) value;
            } else if (value instanceof ZonedDateTime) {
                state = new DateTimeType((ZonedDateTime) value);
            } else if (value instanceof QuantityType<?>) {
                state = (QuantityType<?>) value;
            } else if (value instanceof BigDecimal) {
                state = new DecimalType((BigDecimal) value);
            } else if (value instanceof Integer) {
                state = new DecimalType(BigDecimal.valueOf(((Integer) value).longValue()));
            } else if (value instanceof String) {
                state = new StringType(value.toString());
            } else if (value instanceof Float) {
                state = new DecimalType((Float) value);
            } else {
                logger.warn("Update channel {}: Unsupported value type {}", channelId,
                        value.getClass().getSimpleName());
            }
            logger.debug("Update channel {} with state {} ({})", channelId, (state == null) ? "null" : state.toString(),
                    (value == null) ? "null" : value.getClass().getSimpleName());

            // Update the channel
            if (state != null) {
                updateState(channelId, state);
            }
        }
    }

    /**
     *
     * Make Call to Samsung Digital Inverter
     * Data is returned as json
     *
     */
    private SamsungDigitalInverterJsonResponse getSamsungDigitalInverterData() {
        SamsungDigitalInverterJsonResponse result = null;
        String errorMsg = null;

        try {
            URL url = createURL(SamsungDigitalInverterConstants.DEVICES);
            logger.debug("getSamsungDigitalInverterData URL = {}", url);

            URLConnection con = openConnection(url);

            String response = convertStreamToString(con.getInputStream());
            logger.debug("getSamsungDigitalInverterData Response: {}", response);

            // Map the JSON response to an object
            result = gson.fromJson(response, SamsungDigitalInverterJsonResponse.class);

            if (result.getDevices() != null) {
                updateStatus(ThingStatus.ONLINE, ThingStatusDetail.NONE, null);
            } else {
                errorMsg = "missing data sub-object";
                logger.warn("getSamsungDigitalInverterData Invalid response: {}", errorMsg);
            }

            // Download usage.db
            // usageDB = getUsageDB();
            errorCounter = 0;
        } catch (Exception e) {
            logger.error("getSamsungDigitalInverterData {}", e.getMessage());
            if (++errorCounter > 9) {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, e.getMessage());
                errorCounter = 0;
            }

            // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, e.getMessage());
        }
        return (result);
    }

    /**
     *
     * Get value from json response, convert values to wanted type.
     *
     * @param channelId
     * @param data
     * @return Object with data from response
     * @throws Exception
     */
    public static Object getValue(String channelId, SamsungDigitalInverterJsonResponse data) throws Exception {
        String[] fields = channelId.split("#");
        Object value;

        if (data != null) {
            switch (fields[0]) {
                case SamsungDigitalInverterConstants.TEMPERATURE_CURRENT:
                    return data.getDevices().get(0).getTemperatures().get(0).getCurrent();
                case SamsungDigitalInverterConstants.SETPOINT_TEMPERATURE:
                    return data.getDevices().get(0).getTemperatures().get(0).getDesired();
                case SamsungDigitalInverterConstants.OUTDOOR_TEMPERATURE:
                    return new BigDecimal(data.getDevices().get(0).getMode().getOptions().get(4).substring(12));
                case SamsungDigitalInverterConstants.POWER:
                    return "On".equals(data.getDevices().get(0).getOperation().getPower())
                            ? SamsungDigitalInverterConstants.ON
                            : SamsungDigitalInverterConstants.OFF;
                case SamsungDigitalInverterConstants.WIND_SPEED:
                    return data.getDevices().get(0).getWind().getSpeedLevel().toString();
                case SamsungDigitalInverterConstants.MAX_WIND_SPEED:
                    return data.getDevices().get(0).getWind().getMaxSpeedLevel().toString();
                case SamsungDigitalInverterConstants.WIND_DIRECTION:
                    return data.getDevices().get(0).getWind().getDirection();
                case SamsungDigitalInverterConstants.ALARM:
                    value = data.getDevices().get(0).getAlarms().get(0).getCode();
                    if ("FilterAlarm_OFF".equals(value)) {
                        value = "";
                    } else if ("FilterAlarm".equals(value)) {
                        value = "Please Clean Filter";
                    }
                    return value;
                case SamsungDigitalInverterConstants.OPERATING_MODE:
                    return data.getDevices().get(0).getMode().getModes().get(0);
                case SamsungDigitalInverterConstants.OPTIONS:
                    value = data.getDevices().get(0).getMode().getOptions();
                    return value;
                case SamsungDigitalInverterConstants.COMODE:
                    value = data.getDevices().get(0).getMode().getOptions().get(0);
                    return value;
                case SamsungDigitalInverterConstants.FILTER_ALARMTIME:
                    value = data.getDevices().get(0).getMode().getOptions().get(11).substring(16);
                    value = new BigDecimal((String) value);
                    return value;
                case SamsungDigitalInverterConstants.FILTERTIME:
                    value = data.getDevices().get(0).getMode().getOptions().get(8).substring(11);
                    value = Float.valueOf((Float.valueOf((String) value) / 10));
                    return value;
                case SamsungDigitalInverterConstants.AUTOCLEAN:
                    return "Autoclean_On".equals(data.getDevices().get(0).getMode().getOptions().get(2))
                            ? SamsungDigitalInverterConstants.ON
                            : SamsungDigitalInverterConstants.OFF;
                case SamsungDigitalInverterConstants.BEEP:
                    return "Volume_100".equals(data.getDevices().get(0).getMode().getOptions().get(13))
                            ? SamsungDigitalInverterConstants.ON
                            : SamsungDigitalInverterConstants.OFF;
                case SamsungDigitalInverterConstants.RESET_FILTER_CLEAN_ALARM:
                    return "FilterCleanAlarm_0".equals(data.getDevices().get(0).getMode().getOptions().get(3))
                            ? SamsungDigitalInverterConstants.OFF
                            : SamsungDigitalInverterConstants.ON;
            }
        } else {
            throw new ChannelException("data can't be null for channelID=" + channelId);
        }

        return null;
    }

    private TrustManager[] createTrustAll() {
        TrustManager[] ret = new TrustManager[] { new X509TrustManager() {
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        } };

        return (ret);
    }

    private KeyManager[] createKeyManagers(String keystoreFilePath, String keyStorePassword) throws KeyStoreException,
            NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException {

        InputStream keyStoreInput = new FileInputStream(keystoreFilePath);
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(keyStoreInput, keyStorePassword.toCharArray());
        // keystore.
        KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmfactory.init(keyStore, keyStorePassword.toCharArray());
        return kmfactory.getKeyManagers();
    }

    private HostnameVerifier createrHostnameVerifier() {
        return (new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }

    private URL createURL(String path) throws MalformedURLException {
        return (new URL("https://" + config.ip + ":" + config.port + path));
    }

    private HttpsURLConnection openConnection(URL url) throws IOException {
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.addRequestProperty("Content-Type", "application/json");
        con.addRequestProperty("Authorization", "Bearer " + config.bearer);
        return (con);
    }

    private String convertStreamToString(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String line = null;
        boolean isFirst = true;

        while ((line = br.readLine()) != null) {
            if (isFirst) {
                sb.append(line);
            } else {
                sb.append("\n").append(line);
            }
            isFirst = false;
        }

        return sb.toString();
    }
}
