package cn.iocoder.yudao.module.infra.api.file;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.infra.api.file.dto.FileCreateReqDTO;
import cn.iocoder.yudao.module.infra.api.file.dto.FilePageReqDTO;
import cn.iocoder.yudao.module.infra.api.file.dto.FileRespDTO;
import cn.iocoder.yudao.module.infra.convert.file.FileConvert;
import cn.iocoder.yudao.module.infra.dal.dataobject.file.FileDO;
import cn.iocoder.yudao.module.infra.service.file.FileService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.module.system.enums.ApiConstants.VERSION;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@DubboService(version = VERSION) // 提供 Dubbo RPC 接口，给 Dubbo Consumer 调用
@Validated
public class FileApiImpl implements FileApi {

    @Resource
    private FileService fileService;

    @Override
    public CommonResult<String> createFile(FileCreateReqDTO createReqDTO) {
        return success(fileService.createFile(createReqDTO.getName(), createReqDTO.getPath(),
                createReqDTO.getContent()));
    }

    @Override
    public CommonResult<FileRespDTO> createResp(FileCreateReqDTO createReqDTO) {
        FileDO file = fileService.createFileAndGetId(createReqDTO.getName(), createReqDTO.getPath(),
                createReqDTO.getContent());
        return success(FileConvert.INSTANCE.convert0(file));
    }

    @Override
    public CommonResult<PageResult<FileRespDTO>> getFilePage(FilePageReqDTO pageVO) {
        PageResult<FileDO> pageResult = fileService.getFilePage(FileConvert.INSTANCE.convert(pageVO));
        return success(FileConvert.INSTANCE.convertPage0(pageResult));
    }

    @Override
    public CommonResult<Boolean> delete(Long id) {
        try {
            fileService.deleteFile(id);
            return success(true);
        } catch (Exception e) {
            e.printStackTrace();
            return error(500, e.getMessage());
        }
    }

    @Override
    public CommonResult<FileRespDTO> getFileById(Long id) {
        FileDO file = fileService.getFileById(id);
        return success(FileConvert.INSTANCE.convert0(file));
    }

}
