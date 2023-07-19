package cn.iocoder.yudao.module.fis.dal.mysql.problem;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemInfoExportReqVO;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemInfoPageReqVO;
import cn.iocoder.yudao.module.fis.dal.dataobject.problem.ProblemInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 问题信息 Mapper
 *
 * @author chiwd
 */
@Mapper
public interface ProblemInfoMapper extends BaseMapperX<ProblemInfoDO> {

    default PageResult<ProblemInfoDO> selectPage(ProblemInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProblemInfoDO>()
                .eqIfPresent(ProblemInfoDO::getDetailId, reqVO.getDetailId())
                .eqIfPresent(ProblemInfoDO::getPubWorkId, reqVO.getPubWorkId())
                .likeIfPresent(ProblemInfoDO::getPubUserName, reqVO.getPubUserName())
                .likeIfPresent(ProblemInfoDO::getProblemName, reqVO.getProblemName())
                .eqIfPresent(ProblemInfoDO::getProblemDescribe, reqVO.getProblemDescribe())
                .eqIfPresent(ProblemInfoDO::getProblemAttached, reqVO.getProblemAttached())
                .likeIfPresent(ProblemInfoDO::getSubWorkId, reqVO.getSubWorkId())
                .eqIfPresent(ProblemInfoDO::getProblemType, reqVO.getProblemType())
                .betweenIfPresent(ProblemInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProblemInfoDO::getId));
    }

    default List<ProblemInfoDO> selectList(ProblemInfoExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ProblemInfoDO>()
                .eqIfPresent(ProblemInfoDO::getDetailId, reqVO.getDetailId())
                .eqIfPresent(ProblemInfoDO::getPubWorkId, reqVO.getPubWorkId())
                .likeIfPresent(ProblemInfoDO::getPubUserName, reqVO.getPubUserName())
                .likeIfPresent(ProblemInfoDO::getProblemName, reqVO.getProblemName())
                .eqIfPresent(ProblemInfoDO::getProblemDescribe, reqVO.getProblemDescribe())
                .eqIfPresent(ProblemInfoDO::getProblemAttached, reqVO.getProblemAttached())
                .eqIfPresent(ProblemInfoDO::getSubWorkId, reqVO.getSubWorkId())
                .eqIfPresent(ProblemInfoDO::getProblemType, reqVO.getProblemType())
                .betweenIfPresent(ProblemInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProblemInfoDO::getId));
    }

}
