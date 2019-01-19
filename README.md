# Samsung Digital Inverter

This binding uses the built in webserver in the Digital Inverter to control and monitor.

This binding need a private key, to authenticate with the webserver, and a token (bearer)

## Supported Things

There is one supported thing type, Samsung Digital Inverter

## Discovery

There is not discovery for this addon. Thing must be configured manually

## Binding Configuration

The binding has no configuration options, all configuration is done at Thing level.

## Thing Configuration

The thing has a few required configuration parameters:

| Parameter         | Description                                                                          |
|-------------------|--------------------------------------------------------------------------------------|
| Token             | Bearer for this specific Samsung Digitasl Inverter                                   |
| IP-Address        | The IP-adress for this thing                                                         |
| Port Number       | Unique ID of the measuring station.                                                  |
| Refresh Interval  | Refresh interval in seconds. Default value is 60 minutes.                            |
| Keystore          | Absolute path to keystore with Private Key for the Webserver in the Digital Inverter |
| Keystore Password | Password to the encrypted keystore                                                   |

## Channels

The information from the Samsung Digital Inverter:

| Channel ID           | Item Type          | Description                                                                     |
|----------------------|--------------------|---------------------------------------------------------------------------------|
| power                | Switch             | Power On/Off the Inverter                                                       |
| autoclean            | Switch             | Enable/Disable Autoclean                                                        |
| setpoint_temperature | Number:Temperature | Desired Temperature                                                             |
| temperature          | Number:Temperature | Measured Temperature                                                            |
| outdoor_temperature  | Number:Temperature | Outdoor Unit Temperature                                                    |
| winddirection        | String             | Set Wind Direction Automatic=All, Up and Down=Up_And_Low, Left and Right=Left_And_Right, Fixed=Fix |
| windspeed            | Number             | Set windspeed Auto=0, Low=1, Medium=2, High=3, Turbo=4                                        |
| operation_mode       | String             | Set operating mode Auto, Heat, Cool, Dry, Wind                                                     |
| comode               | String             | Comfort Settings: Normal=Comode_Off, Quiet=Comode_Quiet, Comfort=Comode_Comfort, Smart=Comode_Smart, 25Step=Comode_25Step                 |
| filtertime           | Number             | Operating hours since last Filter Cleaning                  |
| filteralarmtime      | Number             | Set Thershold for Filter Cleaning INterval                  |
| alarm                | String             | Device Alarm Status                             |


