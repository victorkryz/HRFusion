/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

#include "common.h"
#include "Tools.h"
#include "Processing.h"
#include "Reflection.h"
#include "Session.h"
#include <Poco/File.h>
#include <Poco/Data/RecordSet.h>
#include "Poco/Data/SQLite/Connector.h"
#include "Poco/Data/SQLite/SQLiteException.h"
#include "Poco/NumberParser.h"
#include <limits>

using namespace Poco::Data::Keywords;
using Poco::Data::Statement;
using Poco::Data::RecordSet;
using Poco::InvalidAccessException;
using Poco::Data::SQLite::InvalidSQLStatementException;


extern "C"
JNIEXPORT jobjectArray JNICALL
Java_victor_kryz_hrfusion_hrdb_sqlt_SessionImpl_getRegionsNt(JNIEnv* env, jobject instance)
{
    using Entities::HrItem;
    using Entities::HrItems;

    jobjectArray jobjRes = nullptr;

    try
    {
        const std::string dbFile = getDbFile(env, instance);
        Sqlt::SessionSp spSession(
                Sqlt::Utils::openSession(dbFile));

        scope_t<> scope([spSession](bool) noexcept {
            spSession->close();
        });

        typedef Poco::Tuple<int, std::string> Region;
        typedef std::vector<Region> Regions;
        Regions regions;

//        const std::string strStmt("SELECT REGION_ID, REGION_NAME FROM REGIONS ORDER BY REGION_NAME");
        const std::string strStmt("SELECT * FROM REGIONS");
        Statement select(*spSession);
        reset(select);
        select << strStmt;
//        select << strStmt,
//                    into(regions), now;
        select.execute();

        SelectionReader<HrItem<int>> sr(select, 2,
                                        [](HrItem<int>& item, RecordSet& rs) {
                                            item.id = extractValue<int>(rs[0]);
                                            item.name = extractValue<std::string>(rs[1]);
                                        });

        HrItems<int> items;
        sr.read(items);

//        for (const auto& item : regions)
//        {
//            auto& target = *(items.insert(items.end(), HrItem<int>()));
//            target.id = item.get<0>();
//            target.name = item.get<1>();
//        }

        scope.enclose();

        Reflector<HrItem<int>> reflector(env);
        jobjRes = reflector.reflectEntities(items);
    }
    catch (const InvalidSQLStatementException& e) {
        HANDLE_POCO_EXCEPTION(env, e);
    }
    catch (const InvalidAccessException& e) {
        HANDLE_POCO_EXCEPTION(env, e);
    }
    catch (const Poco::Exception& e) {
        HANDLE_POCO_EXCEPTION(env, e);
    }
    catch (const ReflectionException& e) {
        HANDLE_REFLECTION_EXCEPTION(env, e);
    }
    catch (const std::exception& e) {
        HANDLE_STD_EXCEPTION(env, e);
    }
    return jobjRes;
}

extern "C"
JNIEXPORT jobjectArray JNICALL
Java_victor_kryz_hrfusion_hrdb_sqlt_SessionImpl_getCountriesNt(JNIEnv *env, jobject instance,
                                                           jstring jstrRegionId)
{
    using Entities::HrItem;
    using Entities::HrItems;

    jobjectArray jobjRes = nullptr;

    try
    {
        Sqlt::SessionSp spSession(
                Sqlt::Utils::openSession(getDbFile(env, instance)));

        scope_t<> scope([spSession](bool) noexcept {
            spSession->close();
        });

        int regionId(fromString(
                fromJStr(env, jstrRegionId)));

        Statement select(*spSession);
        reset(select);
        const std::string strStmt("SELECT COUNTRY_ID, COUNTRY_NAME FROM COUNTRIES WHERE REGION_ID = ? ORDER BY COUNTRY_NAME");
        select << strStmt, use(regionId);
        select.execute();

        SelectionReader<HrItem<std::string>> sr(select, 2,
                                                [](HrItem<std::string>& item, RecordSet& rs) {
                                                    item.id = extractValue<std::string>(rs[0]);
                                                    item.name = extractValue<std::string>(rs[1]);
                                                });
        HrItems<std::string> items;
        sr.read(items);

        scope.enclose();

        Reflector<HrItem<std::string>> reflector(env);
        jobjRes = reflector.reflectEntities(items);
    }
    catch (const InvalidSQLStatementException& e) {
        HANDLE_POCO_EXCEPTION(env, e);
    }
    catch (const InvalidAccessException& e) {
        HANDLE_POCO_EXCEPTION(env, e);
    }
    catch (const Poco::Exception& e) {
        HANDLE_POCO_EXCEPTION(env, e);
    }
    catch (const ReflectionException& e) {
        HANDLE_REFLECTION_EXCEPTION(env, e);
    }
    catch (const std::exception& e) {
        HANDLE_STD_EXCEPTION(env, e);
    }
    return jobjRes;
}

extern "C"
JNIEXPORT jobjectArray JNICALL
Java_victor_kryz_hrfusion_hrdb_sqlt_SessionImpl_getLocationsNt(JNIEnv *env, jobject instance,
                                                           jstring jstrCountryId)
{
    using Entities::Location;
    using Entities::Locations;

    jobjectArray jobjRes = nullptr;

    try
    {
        Sqlt::SessionSp spSession(
                Sqlt::Utils::openSession(getDbFile(env, instance)));

        scope_t<> scope([spSession](bool) noexcept {
            spSession->close();
        });

        std::string strCountryId(fromJStr(env, jstrCountryId));

        Statement select(*spSession);
        reset(select);
        const std::string strStmt("SELECT LOCATION_ID, CITY, STATE_PROVINCE, STREET_ADDRESS, POSTAL_CODE  FROM LOCATIONS WHERE COUNTRY_ID = ? ORDER BY CITY");
        select << strStmt, use(strCountryId);
        select.execute();

        SelectionReader<Location> sr(select, 5,
                                     [strCountryId](Location& item, RecordSet& rs) {
                                         int fieldIndex(0);
                                         item.id = extractValue<int>(rs[fieldIndex]);
                                         item.city = extractValue<std::string>(rs[++fieldIndex]);
                                         item.stateProvince = extractValue<std::string>(rs[++fieldIndex]);
                                         item.streetAddress = extractValue<std::string>(rs[++fieldIndex]);
                                         item.postalCode = extractValue<std::string>(rs[++fieldIndex]);
                                         item.countryId = strCountryId;
                                     });
        Locations items;
        sr.read(items);

        scope.enclose();

        Reflector<Location> reflector(env);
        jobjRes = reflector.reflectEntities(items);
    }
    catch (const InvalidSQLStatementException& e) {
        HANDLE_POCO_EXCEPTION(env, e);
    }
    catch (const InvalidAccessException& e) {
        HANDLE_POCO_EXCEPTION(env, e);
    }
    catch (const Poco::Exception& e) {
        HANDLE_POCO_EXCEPTION(env, e);
    }
    catch (const ReflectionException& e) {
        HANDLE_REFLECTION_EXCEPTION(env, e);
    }
    catch (const std::exception& e) {
        HANDLE_STD_EXCEPTION(env, e);
    }
    return jobjRes;
}


extern "C"
JNIEXPORT jobjectArray JNICALL
Java_victor_kryz_hrfusion_hrdb_sqlt_SessionImpl_getDepartmentsNt(JNIEnv *env, jobject instance,
                                                             jstring jstrLocationId)
{
    using Entities::Department;
    using Entities::Departments;

    jobjectArray jobjRes = nullptr;

    try
    {
        Sqlt::SessionSp spSession(
                Sqlt::Utils::openSession(getDbFile(env, instance)));

        scope_t<> scope([spSession](bool) noexcept {
            spSession->close();
        });

        int locationId(fromString(fromJStr(env, jstrLocationId)));

        Statement select(*spSession);
        reset(select);
        const std::string strStmt("SELECT DEPARTMENT_ID, DEPARTMENT_NAME , MANAGER_ID FROM DEPARTMENTS WHERE LOCATION_ID = ? ORDER BY DEPARTMENT_NAME");
        select << strStmt, use(locationId);
        select.execute();

        SelectionReader<Department> sr(select, 3,
                                     [](Department& item, RecordSet& rs) {
                                         int fieldIndex(0);
                                         item.id = extractValue<int>(rs[fieldIndex]);
                                         item.name = extractValue<std::string>(rs[++fieldIndex]);
                                         item.mngrId = extractValue<int>(rs[++fieldIndex]);
                                     });
        Departments items;
        sr.read(items);

        scope.enclose();

        Reflector<Department> reflector(env);
        jobjRes = reflector.reflectEntities(items);
    }
    catch (const InvalidSQLStatementException& e) {
        HANDLE_POCO_EXCEPTION(env, e);
    }
    catch (const InvalidAccessException& e) {
        HANDLE_POCO_EXCEPTION(env, e);
    }
    catch (const Poco::Exception& e) {
        HANDLE_POCO_EXCEPTION(env, e);
    }
    catch (const ReflectionException& e) {
        HANDLE_REFLECTION_EXCEPTION(env, e);
    }
    catch (const std::exception& e) {
        HANDLE_STD_EXCEPTION(env, e);
    }
    return jobjRes;
}


extern "C"
JNIEXPORT jobjectArray JNICALL
Java_victor_kryz_hrfusion_hrdb_sqlt_SessionImpl_getEmployeesNt(JNIEnv *env, jobject instance,
                                                           jstring jstrDepartmentId)
{
    using Entities::Employee;
    using Entities::Employees;

    jobjectArray jobjRes = nullptr;

    try
    {
        Sqlt::SessionSp spSession(
                Sqlt::Utils::openSession(getDbFile(env, instance)));

        scope_t<> scope([spSession](bool) noexcept {
            spSession->close();
        });

        int departmentId(fromString(fromJStr(env, jstrDepartmentId)));

        Statement select(*spSession);
        reset(select);
        const std::string strStmt("SELECT DISTINCT A.EMPLOYEE_ID, A.FIRST_NAME, A.LAST_NAME, A.EMAIL, A.PHONE_NUMBER, A.HIRE_DATE," \
                                        " A.JOB_ID, j.JOB_TITLE, A.SALARY, A.COMMISSION_PCT,"\
                                        " CASE WHEN B.MANAGER_ID IS NOT NULL THEN 1 ELSE 0 END AS IS_MNGR"\
                                        " FROM EMPLOYEES A LEFT JOIN EMPLOYEES B ON A.EMPLOYEE_ID = B.MANAGER_ID"\
                                        " LEFT JOIN JOBS j ON (a.JOB_ID = j.JOB_ID)"
                                        " WHERE A.DEPARTMENT_ID = ? ORDER BY IS_MNGR DESC, a.FIRST_NAME");
        select << strStmt, use(departmentId);
        select.execute();

        SelectionReader<Employee> sr(select, 10,
                                       [](Employee& item, RecordSet& rs) {
                                           int fieldIndex(0);
                                           item.id = extractValue<int>(rs[fieldIndex]);
                                           item.name = extractValue<std::string>(rs[++fieldIndex]);
                                           item.lastName = extractValue<std::string>(rs[++fieldIndex]);
                                           item.email = extractValue<std::string>(rs[++fieldIndex]);
                                           item.phoneNumber = extractValue<std::string>(rs[++fieldIndex]);
                                           item.hireDate = extractValue<std::string>(rs[++fieldIndex]);
                                           item.jobId = extractValue<std::string>(rs[++fieldIndex]);
                                           item.jobTitle = extractValue<std::string>(rs[++fieldIndex]);
                                           item.salary = extractValue<float>(rs[++fieldIndex]);
                                           item.comissionPct = extractValue<double>(rs[++fieldIndex]);
                                           item.isMngr = extractValue<bool>(rs[++fieldIndex]);
                                       });
        Employees items;
        sr.read(items);

        scope.enclose();

        Reflector<Employee> reflector(env);
        jobjRes = reflector.reflectEntities(items);
    }
    catch (const InvalidSQLStatementException& e) {
        HANDLE_POCO_EXCEPTION(env, e);
    }
    catch (const InvalidAccessException& e) {
        HANDLE_POCO_EXCEPTION(env, e);
    }
    catch (const Poco::Exception& e) {
        HANDLE_POCO_EXCEPTION(env, e);
    }
    catch (const ReflectionException& e) {
        HANDLE_REFLECTION_EXCEPTION(env, e);
    }
    catch (const std::exception& e) {
        HANDLE_STD_EXCEPTION(env, e);
    }
    return jobjRes;
}

extern "C"
JNIEXPORT jobjectArray JNICALL
Java_victor_kryz_hrfusion_hrdb_sqlt_SessionImpl_getJobHistoryNt(JNIEnv *env, jobject instance,
                                                            jstring jstrEmloyeeId)
{
    using Entities::JobStage;
    using Entities::JobStages;

    jobjectArray jobjRes = nullptr;

    try
    {
        Sqlt::SessionSp spSession(
                Sqlt::Utils::openSession(getDbFile(env, instance)));

        scope_t<> scope([spSession](bool) noexcept {
            spSession->close();
        });

        std::string strEmloyeeId(fromJStr(env, jstrEmloyeeId));

        Statement select(*spSession);
        reset(select);

        const std::string strStmt("SELECT JOB_ID, J.JOB_TITLE, J.MAX_SALARY, J.MAX_SALARY, DEPARTMENT_ID, D.DEPARTMENT_NAME, JH.START_DATE, JH.END_DATE"\
                                          " FROM JOB_HISTORY JH LEFT JOIN JOBS J USING (JOB_ID)"\
                                          " LEFT JOIN DEPARTMENTS D USING (DEPARTMENT_ID) WHERE JH.EMPLOYEE_ID = ? ORDER BY START_DATE DESC;");
        select << strStmt, use(strEmloyeeId);
        select.execute();

        SelectionReader<JobStage> sr(select, 6,
                                       [](JobStage& item, RecordSet& rs) {
                                           int fieldIndex(0);
                                           item.id = extractValue<std::string>(rs[fieldIndex]);
                                           item.name = extractValue<std::string>(rs[++fieldIndex]);
                                           item.minSalary = extractValue<double>(rs[++fieldIndex]);
                                           item.maxSalary = extractValue<double>(rs[++fieldIndex]);
                                           item.departmentId = extractValue<int>(rs[++fieldIndex]);
                                           item.departmentName = extractValue<std::string>(rs[++fieldIndex]);
                                           item.startDate = extractValue<std::string>(rs[++fieldIndex]);
                                           item.endDate = extractValue<std::string>(rs[++fieldIndex]);
                                       });
        JobStages items;
        sr.read(items);

        scope.enclose();

        Reflector<JobStage> reflector(env);
        jobjRes = reflector.reflectEntities(items);
    }
    catch (const InvalidSQLStatementException& e) {
        HANDLE_POCO_EXCEPTION(env, e);
    }
    catch (const InvalidAccessException& e) {
        HANDLE_POCO_EXCEPTION(env, e);
    }
    catch (const Poco::Exception& e) {
        HANDLE_POCO_EXCEPTION(env, e);
    }
    catch (const ReflectionException& e) {
        HANDLE_REFLECTION_EXCEPTION(env, e);
    }
    catch (const std::exception& e) {
        HANDLE_STD_EXCEPTION(env, e);
    }
    return jobjRes;
}


extern "C"
JNIEXPORT void JNICALL
Java_victor_kryz_hrfusion_hrdb_sqlt_SessionImpl_checkDbFile(JNIEnv *env, jclass type,
                                                        jstring _strDbFile)
{
    const std::string strDbFile(fromJStr(env, _strDbFile));

    try {

        if ( !Poco::File(strDbFile).exists() )
        {
            const std::string strMsg(Poco::format("%s: file \"%s\" not found!", std::string(__func__), strDbFile));
            simulateException(env, "java/lang/NotFoundException");
        }

        Sqlt::SessionSp spSession(Sqlt::Utils::openSession(strDbFile));
        spSession->close();
    }
    catch (const Poco::FileAccessDeniedException &e) {
        HANDLE_POCO_EXCEPTION(env, e);
    }
    catch (const Poco::Exception& e) {
        HANDLE_POCO_EXCEPTION(env, e);
    }
}