cmake_minimum_required(VERSION 3.10.2)

project(HRFusion LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

set(CPP_DIR "${CMAKE_CURRENT_SOURCE_DIR}/src/main/cpp")

file(GLOB HRFusion_src
    "${CPP_DIR}/*.cpp"
    "${CPP_DIR}/*.cxx"
    "${CPP_DIR}/*.cc"
)

set(POCO_INC_DIR "${POCO_INSTALL_PATH}/include")
set(POCO_LIB_DIR "${POCO_INSTALL_PATH}/lib")

# find_package(Poco REQUIRED COMPONENTS
#     Foundation
#     Data
#     Util
#     DataSQLite
# )


set(POCO_LIBS ${POCO_LIB_DIR}/libPocoFoundation
    ${POCO_LIB_DIR}/libPocoUtil
    ${POCO_LIB_DIR}/libPocoData
    ${POCO_LIB_DIR}/libPocoDataSQLite)

set(POCO_LINKED_LIBS "")
foreach(x ${POCO_LIBS})
    list(APPEND POCO_LINKED_LIBS debug ${x}d.so optimized ${x}.so)
endforeach()


add_library(HRFusion SHARED
    ${HRFusion_src}
)

target_include_directories(HRFusion
    PRIVATE ${POCO_INC_DIR})

find_library(log-lib log)

target_link_libraries(HRFusion
    PRIVATE
    ${POCO_LINKED_LIBS}
    ${log-lib}
)
