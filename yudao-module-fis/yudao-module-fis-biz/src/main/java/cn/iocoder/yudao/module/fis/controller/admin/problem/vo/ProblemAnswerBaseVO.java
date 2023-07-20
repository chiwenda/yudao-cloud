package cn.iocoder.yudao.module.fis.controller.admin.problem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;


/**
 * 问题回答 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProblemAnswerBaseVO {

    @Schema(description = "问题编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "19519")
    @NotNull(message = "问题编号不能为空")
    private Long problemId;

    /**
     * 回答人工号
     */
    @Schema(description = "回答人工号", requiredMode = Schema.RequiredMode.REQUIRED, example = "19519")
    private String answerId;

    @Schema(description = "回答人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String answerName;

    @Schema(description = "回答时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime answerDate;

    @Schema(description = "回答内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "回答内容不能为空")
    private String answerContent;

    @Schema(description = "回答附件id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long answerFileId;

    @Schema(description = "回答附件", requiredMode = Schema.RequiredMode.REQUIRED)
    private String answerAttached;

    @Schema(description = "回答评分", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer answerScore;

}
