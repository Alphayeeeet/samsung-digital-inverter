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
| beep                 | Switch             | Enable/Disable Beep on the unit                                                 |
| setpoint_temperature | Number:Temperature | Desired Temperature                                                             |
| temperature          | Number:Temperature | Measured Temperature                                                            |
| outdoor_temperature  | Number:Temperature | Outdoor Unit Temperature                                                    |
| winddirection        | String             | Set Wind Direction Automatic=All, Up and Down=Up_And_Low, Fixed=Fix |
| windspeed            | Number             | Set windspeed Auto=0, Low=1, Medium=2, High=3, Turbo=4                                        |
| operation_mode       | String             | Set operating mode Auto, Heat, Cool, Dry, Wind                                                     |
| comode               | String             | Comfort Settings: Normal=Comode_Off, Quiet=Comode_Quiet, Comfort=Comode_Comfort, Smart=Comode_Smart, 25Step=Comode_25Step                 |
| filtertime           | Number             | Operating hours since last Filter Cleaning                  |
| filteralarmtime      | Number             | Set Thershold for Filter Cleaning INterval                  |
| alarm                | String             | Device Alarm Status                             |


## Setup

To comunicate with the Samsung Digital Inverter, a token is required. And a keystore file with the Samsung certificate.

### Getting the token.

To get token for your unit save the text below as a Python script, name it like server.py.
The script will start a webserver on port 8889, this webserver must be up running unit you have . 
Next turn the power of on your unit (with the remote)
Turn power on, and within 30 seconds, run the script token.py in another terminal, from the same server as the script server.py was started.
The unit will make a connection back to the same address at port 8889, and the token will be displayed in that session.

The binding needs a keystore in "pks #12". cert.pem can be converted into "pks #12".
The password choosen must be configured in the binding.

```
openssl pkcs12 -export -out samsung.pkcs12 -in cert.pem
```



# cert.pem

See the Openhab forum, to find the cert.pem file needed.


# sdi_server.py

```
from http.server import HTTPServer, BaseHTTPRequestHandler
import ssl

class RequestHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        request_path = self.path

        print("\n----- Request Start ----->\n")
        print("Request path:", request_path)
        print("Request headers:", self.headers)
        print("<----- Request End -----\n")

        self.send_response(200)
        self.send_header("Set-Cookie", "foo=bar")
        self.end_headers()

    def do_POST(self):
        request_path = self.path

        print("\n----- Request Start ----->\n")
        print("Request path:", request_path)

        request_headers = self.headers
        content_length = request_headers.get('Content-Length')
        length = int(content_length) if content_length else 0
        #body = self.body

        print("Content Length:", length)
        print("Request headers:", request_headers)
        print("Request payload:", self.rfile.read(28))
        print("Body:", self)
        print("<----- Request End -----\n")

        self.send_response(200)
        self.end_headers()

    do_PUT = do_POST
    do_DELETE = do_GET

def main():
    port = 8889
    print('Listening on localhost:%s' % port)
    server = HTTPServer(('', port), RequestHandler)
    server.socket = ssl.wrap_socket(server.socket, certfile='/etc/openhab2/services/cert.pem', server_side=True)
    server.serve_forever()

if __name__ == "__main__":
    main()
```




# sdi_token.py

```
import requests
s = requests.Session()
headers = {'content-type': 'text/xml'}
resp = s.post("https://SamsungDI:8888/devicetoken/request", data={"DeviceToken":"xxxxxxxxxxx"}, headers=headers, stream=True, verify=False, cert='/etc/openhab2/services/cert.pem')
```



### Credits

I have to credit the Openhab Community
Information in the threads https://community.openhab.org/t/samsung-smart-air-conditioner-openhab-2-0/21581/4 and https://community.openhab.org/t/newgen-samsung-ac-protocol/33805/6 pointed me into the right direction has been of invaluable information and inspiration.
