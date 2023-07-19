package cn.iocoder.yudao.module.fis.controller.admin.problem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 问题信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProblemInfoBaseVO {

    @Schema(description = "问题详情编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "19737")
    private String detailId;

    @Schema(description = "提出人工号", requiredMode = Schema.RequiredMode.REQUIRED, example = "7222")
    private String pubWorkId;

    @Schema(description = "提出人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    private String pubUserName;

    @Schema(description = "问题名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotNull(message = "问题名称不能为空")
    private String problemName;

    @Schema(description = "问题描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "问题描述不能为空")
    private String problemDescribe;

    @Schema(description = "问题附件")
    private String problemAttached;

    @Schema(description = "指定回答人工号", requiredMode = Schema.RequiredMode.REQUIRED, example = "4804")
    @NotNull(message = "指定回答人工号不能为空")
    private String subWorkId;

    @Schema(description = "问题分类", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "问题分类不能为空")
    private String problemType;

}
