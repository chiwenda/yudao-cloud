package cn.iocoder.yudao.module.fis.dal.mysql.problem;

import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemReportRespVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 问题通用
 */
@Mapper
public interface ProblemMapper {
    /**
     * 问题报表数据
     *
     * @param username 工号
     * @param timeSpan 时间范围
     * @return 报表数据
     */
    List<ProblemReportRespVO> selectProblemReportInfo(@Param("username") String username,
                                                      @Param("timeSpan") LocalDateTime[] timeSpan);

    /**
     * 回答报表数据
     *
     * @param username 工号
     * @param timeSpan 时间范围
     * @return 报表数据
     */
    List<ProblemReportRespVO> selectAnswerReportInfo(@Param("username") String username,
                                                     @Param("timeSpan") LocalDateTime[] timeSpan);
}
