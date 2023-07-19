package cn.iocoder.yudao.module.fis.convert.problem;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemAnswerCreateReqVO;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemAnswerExcelVO;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemAnswerRespVO;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemAnswerUpdateReqVO;
import cn.iocoder.yudao.module.fis.dal.dataobject.problem.ProblemAnswerDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 问题回答 Convert
 *
 * @author chiwd
 */
@Mapper
public interface ProblemAnswerConvert {

    ProblemAnswerConvert INSTANCE = Mappers.getMapper(ProblemAnswerConvert.class);

    ProblemAnswerDO convert(ProblemAnswerCreateReqVO bean);

    ProblemAnswerDO convert(ProblemAnswerUpdateReqVO bean);

    ProblemAnswerRespVO convert(ProblemAnswerDO bean);

    List<ProblemAnswerRespVO> convertList(List<ProblemAnswerDO> list);

    PageResult<ProblemAnswerRespVO> convertPage(PageResult<ProblemAnswerDO> page);

    List<ProblemAnswerExcelVO> convertList02(List<ProblemAnswerDO> list);

}
