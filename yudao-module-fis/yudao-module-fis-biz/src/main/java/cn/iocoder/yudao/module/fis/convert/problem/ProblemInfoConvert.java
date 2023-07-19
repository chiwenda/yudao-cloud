package cn.iocoder.yudao.module.fis.convert.problem;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.*;
import cn.iocoder.yudao.module.fis.dal.dataobject.problem.ProblemInfoDO;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 问题信息 Convert
 *
 * @author chiwd
 */
@Mapper
public interface ProblemInfoConvert {

    ProblemInfoConvert INSTANCE = Mappers.getMapper(ProblemInfoConvert.class);

    ProblemInfoDO convert(ProblemInfoCreateReqVO bean);

    ProblemInfoDO convert(ProblemInfoUpdateReqVO bean);

    ProblemInfoRespVO convert(ProblemInfoDO bean);

    List<ProblemInfoRespVO> convertList(List<ProblemInfoDO> list);

    List<ProblemUserPostRespVO> convertList0(List<AdminUserRespDTO> list);

    PageResult<ProblemInfoRespVO> convertPage(PageResult<ProblemInfoDO> page);

    List<ProblemInfoExcelVO> convertList02(List<ProblemInfoDO> list);

}
