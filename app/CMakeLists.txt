# Sets the minimum version of CMake required to build the native
# library. You should either keep the default value or only pass a
# value of 3.4.0 or lower.

cmake_minimum_required(VERSION 3.4.1)

#set(CMAKE_VERBOSE_MAKEFILE on)

set(CPP_DIR "${CMAKE_CURRENT_SOURCE_DIR}/src/main/cpp")
set(SOURCE_FILES_DIR "${CPP_DIR}")
set(POCO_INC_DIR "${CPP_DIR}/include")
set(POCO_LIB_DIR "${CMAKE_CURRENT_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}")

function(check_poco_headers)
    file (GLOB PocoInc_files "${POCO_INC_DIR}/Poco/*.h")

    list(LENGTH PocoInc_files PocoInc_files_sz)
    if ( ${PocoInc_files_sz} EQUAL  0)
        message(FATAL_ERROR "Project integrity issue: there're  no POCO headers! \n
                             (solution: copy POCO LIBS headers (*.h) under directory: ${POCO_INC_DIR}")
    endif()

endfunction(check_poco_headers)

check_poco_headers()

aux_source_directory("${SOURCE_FILES_DIR}" HRFusion_src)

add_library(HRFusion SHARED ${HRFusion_src})

find_library( log-lib
              log )

target_include_directories(HRFusion
                           PRIVATE ${POCO_INC_DIR})

set(POCO_LIBS   ${POCO_LIB_DIR}/libPocoFoundation
                ${POCO_LIB_DIR}/libPocoUtil
                ${POCO_LIB_DIR}/libPocoData
                ${POCO_LIB_DIR}/libPocoDataSQLite)

set (POCO_LINKED_LIBS "")
foreach(x ${POCO_LIBS})
       list (APPEND POCO_LINKED_LIBS debug ${x}d.so optimized ${x}.so)
endforeach()

target_link_libraries( HRFusion
                       ${POCO_LINKED_LIBS}
                       ${log-lib} )
