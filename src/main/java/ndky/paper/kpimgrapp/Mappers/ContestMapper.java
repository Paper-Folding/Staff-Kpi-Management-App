package ndky.paper.kpimgrapp.Mappers;

import ndky.paper.kpimgrapp.Models.Contest;
import ndky.paper.kpimgrapp.Models.StaffInfo;
import ndky.paper.kpimgrapp.Request.ContestRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ContestMapper {
    List<Contest> selectList(int from, int length, String query, List<String> selectFieldsNames);

    Long selectListTotal(String query);

    /**
     * select one contest by contest id
     */
    Optional<Contest> selectOne(Long id, List<String> selectFieldsNames);

    Integer insertOne(ContestRequest contestRequest);

    Integer insertMultiple(List<ContestRequest> contestRequestList);

    Integer deleteOne(ContestRequest contestRequest);

    Integer updateContest(Long contestId, String contestNo, String dueUpdateKey, String dueUpdateValue);

    List<StaffInfo> selectStaffInfoListForSelect();
}
