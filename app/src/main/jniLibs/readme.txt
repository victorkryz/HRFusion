----------------------------------------------------------------------
Copy POCO libraries (poco-1.7.5) built for abi types 
'armeabi', 'x86_64', etc. respectively here.
Libraries should be built on NDK toolchain with 
cpp flags: '-std=c++11 -frtti -fexceptions', 
linked with 'stl' type: 'gnustl_shared'
(see: https://www.appinf.com/docs/poco/99300-AndroidPlatformNotes.html)
-----------------------------------------------------------------------

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
    |-- libPocoDataSQLited.so
    |-- libPocoDatad.so
    |-- libPocoFoundation.so
    |-- libPocoFoundationd.so
    |-- libPocoJSON.so
    |-- libPocoJSONd.so
    |-- libPocoUtil.so
    |-- libPocoUtild.so
    |-- libPocoXML.so
    |-- libPocoXMLd.so
`-- armeabi-v7a
    |-- ....

