package cn.iocoder.yudao.module.fis.controller.admin.problem;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.http.HttpUtil;
import com.catl.fis.framework.common.pojo.CommonResult;
import com.catl.fis.module.pc.controller.admin.problem.vo.ProblemInfoRespVO;
import com.catl.fis.module.pc.controller.admin.problem.vo.ProblemReportReqVO;
import com.catl.fis.module.pc.controller.admin.problem.vo.ProblemReportRespVO;
import com.catl.fis.module.pc.convert.problem.ProblemInfoConvert;
import com.catl.fis.module.pc.dal.dataobject.problem.ProblemAnswerDO;
import com.catl.fis.module.pc.dal.dataobject.problem.ProblemInfoDO;
import com.catl.fis.module.pc.service.problem.ProblemService;
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
import java.util.Objects;

import static com.catl.fis.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.catl.fis.framework.common.pojo.CommonResult.success;
import static com.catl.fis.module.pc.enums.ErrorCodeConstants.FILE_DOWNLOAD_ERROR;

@Tag(name = "管理后台 - 问题")
@RestController
@RequestMapping("/pc/problem")
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


    @GetMapping("/download-attached")
    @PreAuthorize("@ss.hasPermission('pc:problem-info:download')")
    @Operation(summary = "下载附件")
    public void download(HttpServletResponse response, @RequestParam("path") String path) {
        ServletOutputStream outputStream = null;
        try {

            byte[] fileBytes = problemService.downloadFromOss(path);
            outputStream = response.getOutputStream();
            outputStream.write(fileBytes);
            outputStream.close();
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=\"" + FileNameUtil.getName(path) + "\"");
        } catch (Exception e) {
            log.error(e.getMessage());
            if (Objects.nonNull(outputStream)) {
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    log.error(e.getMessage());
                    throw exception(FILE_DOWNLOAD_ERROR);
                }
            }
            throw exception(FILE_DOWNLOAD_ERROR);
        }
    }
}
