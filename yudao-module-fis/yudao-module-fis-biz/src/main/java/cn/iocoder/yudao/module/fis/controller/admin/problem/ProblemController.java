package cn.iocoder.yudao.module.fis.controller.admin.problem;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.RandomUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.FilePageReqVO;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.FileRespVO;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemReportReqVO;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemReportRespVO;
import cn.iocoder.yudao.module.fis.service.problem.ProblemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.module.fis.enums.ErrorCodeConstants.FILE_DOWNLOAD_ERROR;
import static cn.iocoder.yudao.module.fis.enums.ErrorCodeConstants.FILE_NOT_EXISTS;

@Tag(name = "管理后台 - 问题")
@RestController
@RequestMapping("/fis/problem")
@Validated
@Slf4j
public class ProblemController {


    @Resource
    private ProblemService problemService;


    @GetMapping("/report/get")
    @Operation(summary = "获得问题报表数据信息")
    @PreAuthorize("@ss.hasPermission('pc:problem-info:query')")
    public CommonResult<List<ProblemReportRespVO>> getReportInfo(@Valid ProblemReportReqVO reqVO) {
        return success(problemService.getProblemReport(reqVO));
    }

    @GetMapping("/file/page")
    @Operation(summary = "获得文件分页")
    @PreAuthorize("@ss.hasPermission('fis:problem-file:query')")
    public CommonResult<PageResult<FileRespVO>> getFilePage(@Valid FilePageReqVO pageVO) {
        return success(problemService.getFilePage(pageVO));
    }

    @DeleteMapping("/file/delete")
    @Operation(summary = "删除文件")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fis:problem-file:delete')")
    public CommonResult<Boolean> deleteFile(@RequestParam("id") Long id) throws Exception {
        return success(problemService.deleteFile(id));
    }


    @GetMapping("/download-attached")
    @PreAuthorize("@ss.hasPermission('fis:problem-info:download')")
    @Operation(summary = "下载附件")
    public void download(HttpServletResponse response, @RequestParam("id") Long id) {
        ServletOutputStream outputStream;
        try {
            byte[] fileBytes = problemService.downloadFromOss(id);
            outputStream = response.getOutputStream();
            outputStream.write(fileBytes);
            outputStream.close();
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=\"" + RandomUtil.randomString(5) + "\"");
        } catch (IOException e) {
            throw exception(FILE_DOWNLOAD_ERROR);
        }

    }
}
