
Design:
=======

High Level Design:
-----
Ths simulator is combined by three sequanced steps:
* create site map from file
* execute command until quit condition is met
* print cost

and a major object:
* site map:
<br> the site map will have properties to record the result of command for each square/block.
<br> the cost calculation will based on the properties of each square/block.

Objects:
------
* Block: is a square in site map, has properties as block type and fields to record behavior/status
    * Site Map (not in a single class): is a 2 dimension array/list of blocks
    <br>the axis of site map: 
    zero point is at the corner of top-left/north-west , 
    x is the horizontal direction from left/west to right/east, 
    y is the vertical direction from top/north to bottom/south
    <br>initial point will be (-1,0) and facing +x, 
    and the (x,y) will be as same as index in site map
* Position: inlcuding the (x,y) and direction, and a boolean indicate where quit from this position
* Command: including what type of command, and steps for advance
* Cost: including cost type, quantity, total cost

Functions:
-----
three groups of major functions:
* Site Map Service: 
    + create site map from file
    + print site map
* Command Service: 
    + execute a single command on a given site map, will change properties of the block
    + print command history
* Cost Calculation Service: 
    + calculate cost by a site map with execution results / command history
    + print cost

Exceptions:
----------

* Handled exceptions:
    + File not found IOException
    + File name not entered
    + Command is case insensitive
    + Error command will not throw exceptions
* Not handled exceptions:
    + Other exceptions when read file. e.g. InvalidPathException
    + Site map is not a rectangular
    + Turn left/right at initial position
    + Error command with acceptable start is also acceptable. 
    <br>E.g. “leave” will be treated as “l”, “advance 4 2” will be treated as “a 4”.


Source Code:
===========
* [my git hub](https://github.com/sean3333/aconex.scs), including java source code, test source code, test data, executable jar file in out/artifacts/aconex_jar
* JDK version 1.8
* Build with maven
* Third party lib used for test: JUnit, Mockito

How to Execute:
==========
* Build with maven, will create jar in \target folder.
* Run jar file in \out\artifacts\aconex_jar\aconex.jar or \target\aconex-1.0-SNAPSHOT.jar, can run with/without file name.
    <br>e.g.
    + Run from project root folder as below, will start with a mocked site map
    ```
    java -jar .\out\artifacts\aconex_jar\aconex.jar .\src\test\resources\siteMapMock.txt
    ```
    + Run from project root folder as below, will start and be asked to enter file name later
    ```
    java -jar .\out\artifacts\aconex_jar\aconex.jar
    ```
* After site map is displayed, command can be entered as (l)eft, (r)ight, (a)dvance n, (q)uit, 
<br>e.g. l, r, a 1, q
* When q command is entered or reaching a preserved tree or beyond boundary, the simulation will end after displayed cost.


