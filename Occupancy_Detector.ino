#include "Arduino.h"
#include "ESP8266.h"
#include "LiquidCrystal_PCF8574.h"
#include "LED.h"
#include "Button.h"


// Pin Definitions
#define WIFI_PIN_TX  11
#define WIFI_PIN_RX 10
#define LEDG_PIN_VIN  5
#define LEDR_PIN_VIN  6
#define TOGGLESWITCH_PIN_2  2



// Global variables and defines
// ====================================================================
// vvvvvvvvvvvvvvvvvvvv ENTER YOUR WI-FI SETTINGS  vvvvvvvvvvvvvvvvvvvv
//
const char *SSID     = "WIFI-SSID"; // Enter your Wi-Fi name 
const char *PASSWORD = "PASSWORD" ; // Enter your Wi-Fi password
//
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
// ====================================================================
char* const host = "www.google.com";
int hostPort = 80;
// There are several different versions of the LCD I2C adapter, each might have a different address.
// Try the given addresses by Un/commenting the following rows until LCD works follow the serial monitor prints. 
// To find your LCD address go to: http://playground.arduino.cc/Main/I2cScanner and run example.
#define LCD_ADDRESS 0x3F
//#define LCD_ADDRESS 0x27
// Define LCD characteristics
#define LCD_ROWS 4
#define LCD_COLUMNS 20
#define SCROLL_DELAY 150
#define BACKLIGHT 255
// object initialization
ESP8266 wifi(WIFI_PIN_RX,WIFI_PIN_TX);
LiquidCrystal_PCF8574 lcd20x4;
LED ledG(LEDG_PIN_VIN);
LED ledR(LEDR_PIN_VIN);
Button ToggleSwitch(TOGGLESWITCH_PIN_2);


// define vars for testing menu
const int timeout = 10000;       //define timeout of 10 sec
char menuOption = 0;
long time0;

// Setup the essentials for your circuit to work. It runs first every time your circuit is powered with electricity.
void setup() 
{
    // Setup Serial which is useful for debugging
    // Use the Serial Monitor to view printed messages
    Serial.begin(9600);
    while (!Serial) ; // wait for serial port to connect. Needed for native USB
    Serial.println("start");
    
    wifi.init(SSID, PASSWORD);
    // initialize the lcd
    lcd20x4.begin(LCD_COLUMNS, LCD_ROWS, LCD_ADDRESS, BACKLIGHT); 
    ToggleSwitch.init();
    menuOption = menu();
    
}

// Main logic of your circuit. It defines the interaction between the components you selected. After setup, it runs over and over again, in an eternal loop.
void loop() 
{
    
    
    if(menuOption == '1') {
    // ESP8266-01 - Wifi Module - Test Code
    //Send request for www.google.com at port 80
    wifi.httpGet(host, hostPort);
    // get response buffer. Note that it is set to 250 bytes due to the Arduino low memory
    char* wifiBuf = wifi.getBuffer();
    //Comment out to print the buffer to Serial Monitor
    //for(int i=0; i< MAX_BUFFER_SIZE ; i++)
    //  Serial.print(wifiBuf[i]);
    //search buffer for the date and time and print it to the serial monitor. This is GMT time!
    char *wifiDateIdx = strstr (wifiBuf, "Date");
    for (int i = 0; wifiDateIdx[i] != '\n' ; i++)
    Serial.print(wifiDateIdx[i]);

    }
    else if(menuOption == '2') {
    // LCD Display 20x4 I2C - Test Code
    // The LCD Screen will display the text of your choice.
    lcd20x4.clear();                          // Clear LCD screen.
    lcd20x4.selectLine(2);                    // Set cursor at the begining of line 2
    lcd20x4.print("    Circuito.io  ");                   // Print print String to LCD on first line
    lcd20x4.selectLine(3);                    // Set cursor at the begining of line 3
    lcd20x4.print("      Rocks!  ");                   // Print print String to LCD on second line
    delay(1000);

    }
    else if(menuOption == '3') {
    // LED - Basic Green 5mm - Test Code
    // The LED will turn on and fade till it is off
    for(int i=255 ; i> 0 ; i -= 5)
    {
    ledG.dim(i);                      // 1. Dim Led 
    delay(15);                               // 2. waits 5 milliseconds (0.5 sec). Change the value in the brackets (500) for a longer or shorter delay in milliseconds.
    }                                          
    ledG.off();                        // 3. turns off
    }
    else if(menuOption == '4') {
    // LED - Basic Red 5mm - Test Code
    // The LED will turn on and fade till it is off
    for(int i=255 ; i> 0 ; i -= 5)
    {
    ledR.dim(i);                      // 1. Dim Led 
    delay(15);                               // 2. waits 5 milliseconds (0.5 sec). Change the value in the brackets (500) for a longer or shorter delay in milliseconds.
    }                                          
    ledR.off();                        // 3. turns off
    }
    else if(menuOption == '5') {
    // ToggleSwitch - Test Code
    //read Toggle Switch state. 
    //if Switch is open function will return LOW (0). 
    //if it is closed function will return HIGH (1).
    bool ToggleSwitchVal = ToggleSwitch.read();
    Serial.print(F("Val: ")); Serial.println(ToggleSwitchVal);
    }
    
    if (millis() - time0 > timeout)
    {
        menuOption = menu();
    }
    
}



// Menu function for selecting the components to be tested
// Follow serial monitor for instrcutions
char menu()
{

    Serial.println(F("\nWhich component would you like to test?"));
    Serial.println(F("(1) ESP8266-01 - Wifi Module"));
    Serial.println(F("(2) LCD Display 20x4 I2C"));
    Serial.println(F("(3) LED - Basic Green 5mm"));
    Serial.println(F("(4) LED - Basic Red 5mm"));
    Serial.println(F("(5) ToggleSwitch"));
    Serial.println(F("(menu) send anything else or press on board reset button\n"));
    while (!Serial.available());

    // Read data from serial monitor if received
    while (Serial.available()) 
    {
        char c = Serial.read();
        if (isAlphaNumeric(c)) 
        {   
            
            if(c == '1') 
          Serial.println(F("Now Testing ESP8266-01 - Wifi Module"));
        else if(c == '2') 
          Serial.println(F("Now Testing LCD Display 20x4 I2C"));
        else if(c == '3') 
          Serial.println(F("Now Testing LED - Basic Green 5mm"));
        else if(c == '4') 
          Serial.println(F("Now Testing LED - Basic Red 5mm"));
        else if(c == '5') 
          Serial.println(F("Now Testing ToggleSwitch"));
            else
            {
                Serial.println(F("illegal input!"));
                return 0;
            }
            time0 = millis();
            return c;
        }
    }
}
