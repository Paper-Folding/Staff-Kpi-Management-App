package ndky.paper.kpimgrapp.Mappers;

import ndky.paper.kpimgrapp.Models.Contest;
import ndky.paper.kpimgrapp.Request.ContestRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContestMapper {
    List<Contest> selectList(int from, int length, String query, List<String> selectFieldsNames);

    Long selectListTotal(String query);

    /**
     * select one contest by contest id
     */
    Contest selectOne(Long id, List<String> selectFieldsNames);

    Integer insertOne(ContestRequest contestRequest);

    Integer insertMultiple(List<ContestRequest> contestRequestList);

    Integer deleteOne(ContestRequest contestRequest);

    Integer updateContest(Long contestId, String dueUpdateKey, String dueUpdateValue);
}
