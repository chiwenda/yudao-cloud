package cn.iocoder.yudao.module.infra.convert.file;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.infra.api.file.dto.FilePageReqDTO;
import cn.iocoder.yudao.module.infra.api.file.dto.FileRespDTO;
import cn.iocoder.yudao.module.infra.controller.admin.file.vo.file.FilePageReqVO;
import cn.iocoder.yudao.module.infra.controller.admin.file.vo.file.FileRespVO;
import cn.iocoder.yudao.module.infra.dal.dataobject.file.FileDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileConvert {

    FileConvert INSTANCE = Mappers.getMapper(FileConvert.class);

    FileRespVO convert(FileDO bean);

    FilePageReqVO convert(FilePageReqDTO bean);

    FileRespDTO convert0(FileDO bean);

    PageResult<FileRespVO> convertPage(PageResult<FileDO> page);
    PageResult<FileRespDTO> convertPage0(PageResult<FileDO> page);

}
