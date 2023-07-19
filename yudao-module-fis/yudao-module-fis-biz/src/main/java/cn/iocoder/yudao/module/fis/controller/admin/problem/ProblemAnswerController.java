package cn.iocoder.yudao.module.fis.controller.admin.problem;


import com.catl.fis.framework.common.pojo.CommonResult;
import com.catl.fis.framework.common.pojo.PageResult;
import com.catl.fis.framework.excel.core.util.ExcelUtils;
import com.catl.fis.framework.operatelog.core.annotations.OperateLog;
import com.catl.fis.module.pc.controller.admin.problem.vo.*;
import com.catl.fis.module.pc.convert.problem.ProblemAnswerConvert;
import com.catl.fis.module.pc.dal.dataobject.problem.ProblemAnswerDO;
import com.catl.fis.module.pc.service.problem.ProblemAnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static com.catl.fis.framework.common.pojo.CommonResult.success;
import static com.catl.fis.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;


@Tag(name = "管理后台 - 问题回答")
@RestController
@RequestMapping("/pc/problem-answer")
@Validated
public class ProblemAnswerController {

    @Resource
    private ProblemAnswerService problemAnswerService;

    @PostMapping("/create")
    @Operation(summary = "创建问题回答")
    @PreAuthorize("@ss.hasPermission('pc:problem-answer:create')")
    public CommonResult<Long> createProblemAnswer(@RequestPart(value = "attached", required = false) MultipartFile file,
                                                  @Valid @RequestPart("content") ProblemAnswerCreateReqVO createReqVO) {
        return success(problemAnswerService.createProblemAnswer(file, createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新问题回答")
    @PreAuthorize("@ss.hasPermission('pc:problem-answer:update')")
    public CommonResult<Boolean> updateProblemAnswer(@Valid @RequestBody ProblemAnswerUpdateReqVO updateReqVO) {
        problemAnswerService.updateProblemAnswer(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除问题回答")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('pc:problem-answer:delete')")
    public CommonResult<Boolean> deleteProblemAnswer(@RequestParam("id") Long id) {
        problemAnswerService.deleteProblemAnswer(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得问题回答")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pc:problem-answer:query')")
    public CommonResult<ProblemAnswerRespVO> getProblemAnswer(@RequestParam("id") Long id) {
        ProblemAnswerDO problemAnswer = problemAnswerService.getProblemAnswer(id);
        return success(ProblemAnswerConvert.INSTANCE.convert(problemAnswer));
    }

    @GetMapping("/list")
    @Operation(summary = "获得问题回答列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('pc:problem-answer:query')")
    public CommonResult<List<ProblemAnswerRespVO>> getProblemAnswerList(@RequestParam("ids") Collection<Long> ids) {
        List<ProblemAnswerDO> list = problemAnswerService.getProblemAnswerList(ids);
        return success(ProblemAnswerConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得问题回答分页")
    @PreAuthorize("@ss.hasPermission('pc:problem-answer:query')")
    public CommonResult<PageResult<ProblemAnswerRespVO>> getProblemAnswerPage(@Valid ProblemAnswerPageReqVO pageVO) {
        PageResult<ProblemAnswerDO> pageResult = problemAnswerService.getProblemAnswerPage(pageVO);
        return success(ProblemAnswerConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出问题回答 Excel")
    @PreAuthorize("@ss.hasPermission('pc:problem-answer:export')")
    @OperateLog(type = EXPORT)
    public void exportProblemAnswerExcel(@Valid ProblemAnswerExportReqVO exportReqVO,
                                         HttpServletResponse response) throws IOException {
        List<ProblemAnswerDO> list = problemAnswerService.getProblemAnswerList(exportReqVO);
        // 导出 Excel
        List<ProblemAnswerExcelVO> datas = ProblemAnswerConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "问题回答.xls", "数据", ProblemAnswerExcelVO.class, datas);
    }

}
