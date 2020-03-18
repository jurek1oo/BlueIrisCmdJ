# blueiriscmdj under GNU gpl-2.0 licence --- https://opensource.org/licenses/gpl-2.0.php

This is Java port of blueiriscmd Python software written by Magnus Appelquist (https://github.com/magapp/blueiriscmd). 
I have used Python code as my template, and have upgraded code to run on Python 3.8.
Python version is in doc/python directory. Blue Iris has to have web server enabled and configured, to be accessible on local lan.
On BlueIris you need to create user with password access to the web server from the local lan.

Python code has been ported to Java (hence blueiriscmdj), to run on my Raspberry pi 3, to control my Blue Iris (BI) system.
I am not sure if I fully understand the BI operation, hence the "doc/notes_issues_list.txt".
I control camera activity (recording) setting different profiles (AtHome and OnTheRoad).
The class HelperSetProfile is used for integration with another Java code running on raspberry PI.

You need to change login and other system parameters to make the unit tests work, in Constants4Tests.calss.
I am using InteliJ Idea community edition, as ide. To develop code on Raspberry I use Netbeans 8.2  on win10, with scripted upload of jars to PI for execution. A fully automated process. The only issue is that killing process on win10 Netbeans, dos not stop pi process. So you end up with multiple java processes running on raspberry. You need to kill them manually with pkill java, on pi. 

For more, check the project wiki. Have fun and let me know if you have issues.

This code is avaiable under GNU GPL 2 licence.  

Good luck, 

Jurek Kurianski jurek@inventionsource.com.au, Phuket Thailand, March 2020.

