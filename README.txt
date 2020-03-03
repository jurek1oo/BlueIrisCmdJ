# blueiriscmdj under GNU gpl-2.0 licence --- https://opensource.org/licenses/gpl-2.0.php

This is Java port of blueiriscmd Python software written by Magnus Appelquist (https://github.com/magapp/blueiriscmd). 
I have used Python code as my template. 
I have upgraded code to run on Python 3.8. It is in doc/python directory.  
I have ported Python code to Java (hence blueiriscmdj), to run on my Raspberry pi 3, to control my Blue Iris (BI) system. 
I am not sure if I fully understand the BI operation, hence the "doc/notes_issues_list.txt".
I control camera activity setting different profiles (AtHome and OnTheRoad).  
You need to change login and other system parameters to make the unit test works, in Constants4Tests.calss. 
I am using InteliJ Idea community edition.

To make executable jar, cd to project directory with pom and execute in terminal window:  

mvn package -Dmaven.test.skip  

This will compile and build snapshot jar. 
Or you can use the existing jar.

This code is avaiable under GNU GPL 2 licence.  

Good luck, 

Jurek Kurianski jurek@inventionsource.com.au, Phuket Thailand, March 2020.
