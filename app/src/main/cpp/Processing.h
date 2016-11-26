/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */


#ifndef HRFUSION_PROCESSING_H
#define HRFUSION_PROCESSING_H

#include "common.h"
#include <Poco/Data/RecordSet.h>

template <typename T,
        typename F = std::function<void(T&, Poco::Data::RecordSet&)>>
class SelectionReader
{
    using Statement = Poco::Data::Statement;
    using RecordSet = Poco::Data::RecordSet;

public:
    SelectionReader(Statement& stmt, int nExpectedColumns, F f) :
            stmt_(stmt), f_(std::move(f)),
            nExpectedColumns_(nExpectedColumns) {
    }

    void read(std::vector<T>& vals)
    {
        RecordSet rs(stmt_);

        const std::size_t ncols(rs.columnCount());
        if ( nExpectedColumns_ >  ncols )
            throw std::runtime_error("SelectionReader::read(): required values number exceeds selected column's number");

        std::vector<T> _vals;
        auto more = rs.moveFirst();
        while (more)
        {
            auto& rowItem = *(_vals.insert(_vals.end(), T()));
            f_(rowItem, rs);

            more = rs.moveNext();
        }

        _vals.swap(vals);
    }

private:
    F f_;
    Statement& stmt_;
    int nExpectedColumns_;
};


template <typename T>
T extractValue(const Poco::Dynamic::Var& var)
{
    if ( !var.isEmpty())
        return var.convert<T>();
    else
        return T();
}


#define HANDLE_POCO_EXCEPTION(_env, _e) \
        {   const std::string strMsg(Poco::format("%s (raised by function: %s)",   _e.message(), std::string(__func__)));\
            ALOGE("%s", strMsg.c_str()); \
            simulateException(_env, strMsg, "victor/kryz/hrfusion/jni/PocoException"); }\

#define HANDLE_STD_EXCEPTION(_env, _e) \
        {   const std::string strMsg(Poco::format("%s (raised by function: %s)",  _e.what(), std::string(__func__)));\
            ALOGE("%s", strMsg.c_str()); \
            simulateException(_env,  strMsg, "java/lang/RuntimeException"); }\

#define HANDLE_REFLECTION_EXCEPTION(_env, _e) \
        {   const std::string strMsg(Poco::format("%s (raised by function: %s)",  _e.what(), std::string(__func__)));\
            ALOGE("%s", strMsg.c_str()); \
            simulateException(_env, strMsg, "victor/kryz/hrfusion/jni/ReflectionException"); }\




#endif //HRFUSION_PROCESSING_H
