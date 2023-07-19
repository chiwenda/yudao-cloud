package cn.iocoder.yudao.module.fis.controller.admin.problem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "pc管理后台 - 问题报表 Request VO")
@Data
@ToString(callSuper = true)
public class ProblemReportReqVO {

    @Schema(description = "报表查询类型:0-问题报表 1-回答报表")
    private Integer reportType;

    @Schema(description = "查询类型:0-问题数量 1-回答数量 2-获得评价数量 3-回答评价总得分 4-平均回答星级 5-回答比例")
    private Integer type;

    @Schema(description = "空间维度:0-回答人姓名 1-问题分类")
    private List<Integer> spaceDimension;

    @Schema(description = "时间维度:0-日期 1-周次 2-月份")
    private Integer timeDimension;

    @Schema(description = "时间范围")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] timeSpan;

    @Schema(description = "回答人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String answerName;

    @Schema(description = "问题分类", example = "2")
    private String problemType;


}
