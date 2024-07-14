# *HRFusion*
*HRFusion is a small Android app, that navigates on "HR" SQLite database*.

#### Motivations:

- Researching [*POCO C++ Libraries*](https://pocoproject.org/index.html) on Android platform.

#### About

In spite of Android SDK provides built-in API for operating with SQLite,
the aim of this app is an integration of POCO libs and using
POCO's API to access to SQLite database
(see [*"POCO C++ Libraries Android Platform Notes"*](https://docs.pocoproject.org/pro/99300-AndroidPlatformNotes.html)).


As a database, there's used a reflection of Oracle Database 'HR' sample schema ("hr.db" file provided in "assets" folder).  
This one is produced by the utility [*"HRToSQLite"*](https://github.com/victorkryz/HRtoSQLite).


#### Built with:

- Android Studio 4.0
    - SDK (Build Tools 35)
    - NDK r26d
- CMake (External Build) for C++ part
- *HRFusion.so* is built with: 
    - cpp flags - *'-std=c++17 -frtti -fexceptions'*
    - stl type - *'c++_shared'*

#### POCO libs integration:

This project uses POCO LIBS 1.13.3.
([Download | POCO C++ Libraries ](https://github.com/pocoproject/poco))

To make it buildable, do the next steps:

 - copy POCO LIBS headers under directory 'app/src/main/cpp/include':
 ```
  -- Poco
    |-- Data
    |   -- SQLite
    |-- Dynamic
    |    -- Util
    |-- ASCIIEncoding.h
    |-- AbstractCache.h
    |-- AbstractDelegate.h
    |-- ...
 ```

 - copy POCO libraries built for abi types 'armeabi', 'x86_64', etc.
   under directory 'app/src/main/jniLibs' respectively:

       ```
       .
       |-- armeabi
       |   |-- libPocoData.so
       |   |-- libPocoDataSQLite.so
       |   |-- libPocoDataSQLited.so
       |   |-- libPocoDatad.so
       |   |-- libPocoFoundation.so
       |   |-- libPocoFoundationd.so
       |   |-- libPocoJSON.so
       |   |-- libPocoJSONd.so
       |   |-- libPocoUtil.so
       |   |-- libPocoUtild.so
       |   |-- libPocoXML.so
       |   |-- libPocoXMLd.so
       `-- x86_64
           |-- libPocoData.so
           |-- libPocoDataSQLite.so
           |-- ...

       ```
    
#### Testing:

 - JUnit tests are provided for all jni-calls against to HRFusion.so;
 - ReadHREntitiesFixture - the single fixture aggregates all test unit calls, to launch them in 'batch mode';

 
#### Screenshots
-----------------

<img src="screenshots/Screenshot.01.png" width="300" high="500">  <img src="screenshots/Screenshot.02.png" width="300" high="500">
<img src="screenshots/Screenshot.05.png" width="300" high="500">  <img src="screenshots/Screenshot.08.png" width="300" high="500">
<img src="screenshots/Screenshot.06.png" width="300" high="500">  <img src="screenshots/Screenshot.07.png" width="300" high="500">





