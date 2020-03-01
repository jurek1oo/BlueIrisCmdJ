Hi,

2020-03-01 Phuket Thailand - Jurek.

I have took Magnus Appelquist (https://github.com/magapp/blueiriscmd)
Python code as my template. I had upgraded it to run on Python 3.8.

I have ported Python code to Java (blueiriscmdj), to run on my Raspberry pi 3, to control my Blue Iris (BI) system.
I am not sure if I understand fully the BI operation, hence the "notes_issues_list.txt"

I control camera activity setting different profiles (AtHome and OnTheRoad).

You need to change login and other system parameters to make the unit test works, in Constants4Tests.calss.

To get executable jar, co to project directory with pom and execute:

mvn package

This will compile, run tests and build snapshot jar.
Or you can use existing jar in target directory.

Good luck, Jurek Kurianski jurek@inventionsource.com.au, Phuket Thailand, March 2020.