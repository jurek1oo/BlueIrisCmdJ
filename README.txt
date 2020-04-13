# BlueIrisCmdJ under GNU gpl-2.0 licence --- https://opensource.org/licenses/gpl-2.0.php

This is a Java command line application which controls and monitors Blue Iris security camera system. 
As a Java app, it will run on Linux, Windos or Mac.

This project started as a Java port of blueiriscmd Python application written by Magnus Appelquist
(https://github.com/magapp/blueiriscmd). For a reference, Python 3.8 version is in doc/python directory.

The current version 1.2.20 of BlueIrisCmdJ has been extended, and covers all BlueIris API commands 
as defined in House Logix document - https://www.houselogix.com/docs/blue-iris/BlueIris/json.htm.

A new '--cmd-post' a generic command has bee added. This command returns JsonElement object.
This command has functionality of all other commands, when used with proper syntax.

All Java Classes have coresponding unit tests. For the tests to work, 
you need a BI system with at least one camera.

For more information look at https://github.com/jurek1oo/BlueIrisCmdJ/wiki.  

Demo project showing use this software: https://github.com/jurek1oo/BiCmdJDemo.

Have fun and let me know if you have any issues.

Good luck, 

Jurek Kurianski jurek@inventionsource.com.au, Phuket Thailand, March 2020.

This code is avaiable under GNU GPL 2 licence.
