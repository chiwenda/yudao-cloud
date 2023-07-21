package cn.iocoder.yudao.module.fis.controller.admin.problem.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 问题回答分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProblemAnswerPageReqVO extends PageParam {

    @Schema(description = "问题编号", example = "19519")
    private Long problemId;
    @Schema(description = "回答人账号", example = "19519")
    private String answerId;
    @Schema(description = "回答人姓名", example = "张三")
    private String answerName;

    @Schema(description = "回答时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] answerDate;

    @Schema(description = "回答内容")
    private String answerContent;

    @Schema(description = "回答附件")
    private String answerAttached;

    @Schema(description = "回答评分")
    private Integer answerScore;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
