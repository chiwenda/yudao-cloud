package cn.iocoder.yudao.module.fis.dal.mysql.problem;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemAnswerExportReqVO;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemAnswerPageReqVO;
import cn.iocoder.yudao.module.fis.dal.dataobject.problem.ProblemAnswerDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 问题回答 Mapper
 *
 * @author chiwd
 */
@Mapper
public interface ProblemAnswerMapper extends BaseMapperX<ProblemAnswerDO> {

    default PageResult<ProblemAnswerDO> selectPage(ProblemAnswerPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProblemAnswerDO>()
                .eqIfPresent(ProblemAnswerDO::getProblemId, reqVO.getProblemId())
                .eqIfPresent(ProblemAnswerDO::getAnswerId, reqVO.getAnswerId())
                .likeIfPresent(ProblemAnswerDO::getAnswerName, reqVO.getAnswerName())
                .betweenIfPresent(ProblemAnswerDO::getAnswerDate, reqVO.getAnswerDate())
                .eqIfPresent(ProblemAnswerDO::getAnswerContent, reqVO.getAnswerContent())
                .eqIfPresent(ProblemAnswerDO::getAnswerAttached, reqVO.getAnswerAttached())
                .eqIfPresent(ProblemAnswerDO::getAnswerScore, reqVO.getAnswerScore())
                .betweenIfPresent(ProblemAnswerDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProblemAnswerDO::getId));
    }

    default List<ProblemAnswerDO> selectList(ProblemAnswerExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ProblemAnswerDO>()
                .eqIfPresent(ProblemAnswerDO::getProblemId, reqVO.getProblemId())
                .eqIfPresent(ProblemAnswerDO::getAnswerId, reqVO.getAnswerId())
                .likeIfPresent(ProblemAnswerDO::getAnswerName, reqVO.getAnswerName())
                .betweenIfPresent(ProblemAnswerDO::getAnswerDate, reqVO.getAnswerDate())
                .eqIfPresent(ProblemAnswerDO::getAnswerContent, reqVO.getAnswerContent())
                .eqIfPresent(ProblemAnswerDO::getAnswerAttached, reqVO.getAnswerAttached())
                .eqIfPresent(ProblemAnswerDO::getAnswerScore, reqVO.getAnswerScore())
                .betweenIfPresent(ProblemAnswerDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProblemAnswerDO::getId));
    }

    default void deleteByProblemId(Long problemId) {
        delete(new LambdaQueryWrapperX<ProblemAnswerDO>().eqIfPresent(ProblemAnswerDO::getProblemId, problemId));
    }


    default ProblemAnswerDO queryAnswerCount(Long problemId, String answerId) {
        return selectOne(new LambdaQueryWrapperX<ProblemAnswerDO>()
                .eqIfPresent(ProblemAnswerDO::getProblemId, problemId)
                .eqIfPresent(ProblemAnswerDO::getAnswerId, answerId));
    }
}
