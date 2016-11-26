//
// Created by Victor on 11/2/2016.
//

#ifndef HRFUSION_ENTITIES_H
#define HRFUSION_ENTITIES_H

#include <string>
#include <vector>


namespace Entities {

    template<typename T>
    struct HrItem {
        T id = T();
        std::string name;
    };

    struct Location : public HrItem<int> {
        std::string city,
                stateProvince,
                streetAddress,
                postalCode,
                countryId;
    };


    struct Department : public HrItem<int> {
        int mngrId;
    };

    struct Employee : public HrItem<int> {
        std::string lastName, phoneNumber,
                    email, hireDate, jobId,
                    jobTitle;
        float comissionPct;
        double salary;
        bool isMngr = 0;
    };

    struct JobInfo : public HrItem<std::string> {
            double minSalary, maxSalary;
    };

    struct JobStage : public JobInfo {
        int departmentId;
        std::string departmentName,
                    startDate, endDate;
    };

    template<typename T>
    using HrItems = std::vector<HrItem<T>>;
    using Locations = std::vector<Location>;
    using Departments = std::vector<Department>;
    using Employees = std::vector<Employee>;
    using JobStages = std::vector<JobStage>;

}; // namespace Entities

#endif //HRFUSION_ENTITIES_H
