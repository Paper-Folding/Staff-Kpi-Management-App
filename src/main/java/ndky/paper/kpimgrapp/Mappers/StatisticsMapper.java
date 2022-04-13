package ndky.paper.kpimgrapp.Mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StatisticsMapper {
    @Select("select type, count(id) as amount from contest group by type;")
    List<Map<String, Integer>> contestType();

    @Select("select level, count(id) as amount from contest group by level;")
    List<Map<String, Integer>> contestLevel();

    @Select("select gender,count(id) from staff_info where disabled='0' group by gender;")
    List<Map<String, Integer>> staffAge();

    @Select("select ranges,count(*) as amount from (\n" +
            "select case\n" +
            "when (YEAR(NOW()) - YEAR(birth) + 1) > 0 and (YEAR(NOW()) - YEAR(birth) + 1) <= 10 then '1-10'\n" +
            "when (YEAR(NOW()) - YEAR(birth) + 1) > 10 and (YEAR(NOW()) - YEAR(birth) + 1) <= 20 then '11-20'\n" +
            "when (YEAR(NOW()) - YEAR(birth) + 1) > 20 and (YEAR(NOW()) - YEAR(birth) + 1) <= 30 then '21-30'\n" +
            "when (YEAR(NOW()) - YEAR(birth) + 1) > 30 and (YEAR(NOW()) - YEAR(birth) + 1) <= 40 then '31-40'\n" +
            "when (YEAR(NOW()) - YEAR(birth) + 1) > 40 and (YEAR(NOW()) - YEAR(birth) + 1) <= 50 then '41-50'\n" +
            "when (YEAR(NOW()) - YEAR(birth) + 1) > 50 then '>50'\n" +
            "end\n" +
            "as ranges\n" +
            "from staff_info where disabled='0') as temp GROUP BY ranges;")
    List<Map<String, Integer>> staffGender();
}
