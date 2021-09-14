# bt_ACR3901US1_api_PersonalId
the functional of this program is to get data from card reader using bluetooth card reader and send it out to a website depend on adup command communition with card reader

this is a source code which can be compiled by android studio original code form ACS_BLE_EVK_Android-0.52 from their web site.

the original application will connect to a bluetooth card reader (ACR3901US1) and read apdu command from text file then
send and retrieve data from to a card reader, getting data to display.

the modification application will read .txt apdu command data, specific comment with "'" and following by url at the first line, 
to send out data to webpage instate off only display. 

the page retrieve data of get request can be do more about this data parameter name "data".

the command of apdu is specify to Thailand Person ID card which use standard apdu command to get fields of the card. 
still the passing data is still not include to photo fields yet (do it more later)
