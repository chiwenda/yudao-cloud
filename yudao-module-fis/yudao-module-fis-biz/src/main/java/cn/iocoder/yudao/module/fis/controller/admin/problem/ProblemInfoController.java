package cn.iocoder.yudao.module.fis.controller.admin.problem;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.*;
import cn.iocoder.yudao.module.fis.convert.problem.ProblemInfoConvert;
import cn.iocoder.yudao.module.fis.dal.dataobject.problem.ProblemAnswerDO;
import cn.iocoder.yudao.module.fis.dal.dataobject.problem.ProblemInfoDO;
import cn.iocoder.yudao.module.fis.service.problem.ProblemAnswerService;
import cn.iocoder.yudao.module.fis.service.problem.ProblemInfoService;
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

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;


@Tag(name = "管理后台 - 问题信息")
@RestController
@RequestMapping("/pc/problem-info")
@Validated
public class ProblemInfoController {

    @Resource
    private ProblemInfoService problemInfoService;

    @Resource
    private ProblemAnswerService problemAnswerService;

    @PostMapping("/create")
    @Operation(summary = "创建问题信息")
    @PreAuthorize("@ss.hasPermission('pc:problem-info:create')")
    public CommonResult<Long> createProblemInfo(@RequestPart(value = "attached", required = false) MultipartFile file,
                                                @Valid @RequestPart("content") ProblemInfoCreateReqVO createReqVO) {
        return success(problemInfoService.createProblemInfo(file, createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新问题信息")
    @PreAuthorize("@ss.hasPermission('pc:problem-info:update')")
    public CommonResult<Boolean> updateProblemInfo(@Valid @RequestBody ProblemInfoUpdateReqVO updateReqVO) {
        problemInfoService.updateProblemInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除问题信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('pc:problem-info:delete')")
    public CommonResult<Boolean> deleteProblemInfo(@RequestParam("id") Long id) {
        problemInfoService.deleteProblemInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得问题信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pc:problem-info:query')")
    public CommonResult<ProblemInfoRespVO> getProblemInfo(@RequestParam("id") Long id) {
        ProblemInfoDO problemInfo = problemInfoService.getProblemInfo(id);
        //查询问题所有回答
        List<ProblemAnswerDO> problemAnswerList = problemAnswerService.getProblemAnswerList(problemInfo.getId());
        ProblemInfoRespVO problemInfoRespVO = ProblemInfoConvert.INSTANCE.convert(problemInfo);
        problemInfoRespVO.setAnswers(problemAnswerList);
        return success(problemInfoRespVO);
    }

    @PostMapping("/getUsersByPostIds")
    @Operation(summary = "获岗位匹配的用户")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pc:problem-info:query')")
    public CommonResult<List<ProblemUserPostRespVO>> getUsersByPostId(@RequestBody ProblemPostReqVO reqVO) {
        return success(problemInfoService.getUsersByPostId(reqVO.getIds()));
    }

    @GetMapping("/list")
    @Operation(summary = "获得问题信息列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('pc:problem-info:query')")
    public CommonResult<List<ProblemInfoRespVO>> getProblemInfoList(@RequestParam("ids") Collection<Long> ids) {
        List<ProblemInfoDO> list = problemInfoService.getProblemInfoList(ids);
        return success(ProblemInfoConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得问题信息分页(只显示指派给我的问题)")
    @PreAuthorize("@ss.hasPermission('pc:problem-info:query')")
    public CommonResult<PageResult<ProblemInfoRespVO>> getProblemInfoPage(@Valid ProblemInfoPageReqVO pageVO) {
        PageResult<ProblemInfoDO> pageResult = problemInfoService.getProblemInfoPage(pageVO);
        PageResult<ProblemInfoRespVO> result = ProblemInfoConvert.INSTANCE.convertPage(pageResult);
        List<ProblemInfoRespVO> problems = problemInfoService.addIsMeAnswer(result.getList());
        result.setList(problems);
        return success(result);
    }

    @GetMapping("/issue-page")
    @Operation(summary = "获得问题信息分页(只显示我提问的问题)")
    @PreAuthorize("@ss.hasPermission('pc:problem-info:query')")
    public CommonResult<PageResult<ProblemInfoRespVO>> getProblemInfoPageForIssue(@Valid ProblemInfoPageReqVO pageVO) {
        PageResult<ProblemInfoDO> pageResult = problemInfoService.getProblemInfoPageForIssue(pageVO);
        return success(ProblemInfoConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出问题信息 Excel")
    @PreAuthorize("@ss.hasPermission('pc:problem-info:export')")
    @OperateLog(type = EXPORT)
    public void exportProblemInfoExcel(@Valid ProblemInfoExportReqVO exportReqVO,
                                       HttpServletResponse response) throws IOException {
        List<ProblemInfoDO> list = problemInfoService.getProblemInfoList(exportReqVO);
        // 导出 Excel
        List<ProblemInfoExcelVO> datas = ProblemInfoConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "问题信息.xls", "数据", ProblemInfoExcelVO.class, datas);
    }

}
