/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

import org.apache.commons.lang.StringUtils;
import org.eclipse.smarthome.core.library.types.DateTimeType;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.library.types.PointType;
import org.eclipse.smarthome.core.library.types.QuantityType;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.eclipse.smarthome.core.types.State;
import org.eclipse.smarthome.core.types.UnDefType;
import org.openhab.binding.samsungdigitalinverter.SamsungDigitalInverterConstants;
import org.openhab.binding.samsungdigitalinverter.internal.SamsungDigitalInverterConfiguration;
import org.openhab.binding.samsungdigitalinverter.json.SamsungDigitalInverterJsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * The {@link SamsungDigitalInverterHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Kuba Wolanin - Initial contribution
 * @author Łukasz Dywicki - Initial contribution
 */
public class SamsungDigitalInverterHandler extends BaseThingHandler {

    private Logger logger = LoggerFactory.getLogger(SamsungDigitalInverterHandler.class);

    private SamsungDigitalInverterConfiguration config;

    private static final int DEFAULT_REFRESH_PERIOD = 60;

    private ScheduledFuture<?> refreshJob;

    private SamsungDigitalInverterJsonResponse sdiResponse;

    private Gson gson;

    private HostnameVerifier allHostsValid;

    private KeyManager[] keyManagers;

    private TrustManager[] trustManagers;

    private SSLContext sslContext;

    public SamsungDigitalInverterHandler(Thing thing) {
        super(thing);
        gson = new Gson();

    }

    @Override
    public void initialize() {
        logger.debug("Initializing Samsung Digital Inverter handler.");

        config = getConfigAs(SamsungDigitalInverterConfiguration.class);
        logger.debug("config bearer = (omitted from logging)");
        logger.debug("config ip = {}", config.ip);
        logger.debug("config port = {}", config.port);
        logger.debug("config refresh = {}", config.refresh);
        logger.debug("config keystore = {}", config.keystore);
        logger.debug("config keystore secret = (omitted from logging)");

        String errorMsg = null;

        try {
            config.keystore_password = "secret";
            allHostsValid = createrHostnameVerifier();
            keyManagers = createKeyManagers(config.keystore, config.keystore_password);
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
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, errorMsg);
        }
    }

    /**
     * Start the job refreshing the Air Quality data
     */
    private void startAutomaticRefresh() {
        if (refreshJob == null || refreshJob.isCancelled()) {
            Runnable runnable = () -> {
                try {
                    // Request new air quality data to the aqicn.org service
                    sdiResponse = getSamsungDigitalInverterData();

                    // Update all channels from the updated AQI data
                    for (Channel channel : getThing().getChannels()) {
                        updateChannel(channel.getUID().getId(), sdiResponse);
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
        logger.debug("Disposing the Samsung Digitsal Inverter handler.");

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

            }
            if (sdiCommand != null) {
                try {
                    logger.debug("To execute command: {} {}", sdiCommand.getPath(), sdiCommand.getJson());
                    updateService(sdiCommand);
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
            // Delegate command to correct service
            // for (SamsungDigitalInverterService service : services) {
            // for (String s : service.getSupportedChannelNames()) {
            // if (channel.equals(s)) {
            // service.handleCommand(channel, command);
            // return;
            // }
            // }
            // }
            logger.debug("The Samsung Digital Inverter handle command {} -> {}", channelUID, command);
        }
    }

    private void updateService(JsonCommand sdiCommand) throws IOException {
        URL url = createURL(sdiCommand.getPath());

        logger.debug("URL = {}", url);

        HttpsURLConnection con = openConnection(url);
        con.setRequestMethod("PUT");
        con.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(sdiCommand.getJson());
        wr.flush();

        int httpResult = con.getResponseCode();
        logger.debug("updateService {} with {} returned {}", sdiCommand.getPath(), sdiCommand.getJson(), httpResult);

    }

    private void updateChannel(String channelId, SamsungDigitalInverterJsonResponse aqiResponse) {
        if (isLinked(channelId)) {
            Object value;
            try {
                value = getValue(channelId, aqiResponse);
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
     * Request new air quality data to the aqicn.org service
     *
     * @param location  geo-coordinates from config
     * @param stationId station ID from config
     * @return the air quality data object mapping the JSON response or null in case of error
     */
    private SamsungDigitalInverterJsonResponse getSamsungDigitalInverterData() {
        SamsungDigitalInverterJsonResponse result = null;
        String errorMsg = null;

        try {
            URL url = createURL(SamsungDigitalInverterConstants.DEVICES);
            logger.debug("URL = {}", url);

            URLConnection con = openConnection(url);

            String response = convertStreamToString(con.getInputStream());
            logger.debug("Response: {}", response);

            // Map the JSON response to an object
            result = gson.fromJson(response, SamsungDigitalInverterJsonResponse.class);

            if (result.getDevices() != null) {
                updateStatus(ThingStatus.ONLINE, ThingStatusDetail.NONE, null);
            } else {
                errorMsg = "missing data sub-object";
                logger.warn("Invalid response: {}", errorMsg);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return (result);
    }

    public static Object getValue(String channelId, SamsungDigitalInverterJsonResponse data) throws Exception {
        String[] fields = StringUtils.split(channelId, "#");

        if (data != null) {
            switch (fields[0]) {
                case SamsungDigitalInverterConstants.TEMPERATURE_CURRENT:
                    return data.getDevices().get(0).getTemperatures().get(0).getCurrent();
                case SamsungDigitalInverterConstants.SETPOINT_TEMPERATURE:
                    return data.getDevices().get(0).getTemperatures().get(0).getDesired();
                case SamsungDigitalInverterConstants.POWER:
                    return "On".equals(data.getDevices().get(0).getOperation().getPower())
                            ? SamsungDigitalInverterConstants.POWER_ON
                            : SamsungDigitalInverterConstants.POWER_OFF;
                case SamsungDigitalInverterConstants.WIND_SPEED:
                    return data.getDevices().get(0).getWind().getSpeedLevel().toString();
                case SamsungDigitalInverterConstants.WIND_DIRECTION:
                    return data.getDevices().get(0).getWind().getDirection();
                case SamsungDigitalInverterConstants.ALARM:
                    return data.getDevices().get(0).getAlarms().get(0).getCode();
                case SamsungDigitalInverterConstants.OPERATING_MODE:
                    return data.getDevices().get(0).getMode().getModes().get(0);
                case SamsungDigitalInverterConstants.OPTIONS:
                    Object value = data.getDevices().get(0).getMode().getOptions();
                    return value;

            }
        } else {
            throw new NullPointerException();
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

    private KeyManager[] createKeyManagers(String keystoreFilePath, String keyStorePassword) throws Exception {
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
        return (new URL("https://" + config.ip + ":" + config.port + "/" + path));
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
