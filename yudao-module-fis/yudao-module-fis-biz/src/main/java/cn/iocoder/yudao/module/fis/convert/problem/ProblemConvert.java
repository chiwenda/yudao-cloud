package cn.iocoder.yudao.module.fis.convert.problem;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.FilePageReqVO;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.FileRespVO;
import cn.iocoder.yudao.module.infra.api.file.dto.FilePageReqDTO;
import cn.iocoder.yudao.module.infra.api.file.dto.FileRespDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 问题 Convert
 *
 * @author ChiWD01
 */
@Mapper
public interface ProblemConvert {

    ProblemConvert INSTANCE = Mappers.getMapper(ProblemConvert.class);

    FilePageReqDTO convert(FilePageReqVO bean);

    PageResult<FileRespVO> convertPage(PageResult<FileRespDTO> page);
}
