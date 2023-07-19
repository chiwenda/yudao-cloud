package cn.iocoder.yudao.module.fis.controller.admin.problem.vo;

import cn.iocoder.yudao.framework.common.core.KeyValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "pc管理后台 - 问题报表 Response VO")
@Data
@ToString(callSuper = true)
public class ProblemReportRespVO {
    @Schema(description = "问题ID")
    private Integer id;
    @Schema(description = "回答人工号")
    private String answerId;
    @Schema(description = "回答人姓名")
    private String answerName;

    @Schema(description = "问题分类")
    private String problemType;

    @Schema(description = "问题数量")
    private Integer problemSize;

    @Schema(description = "回答数量")
    private Integer answerSize;

    @Schema(description = "获得评价数量")
    private Integer commentSize;

    @Schema(description = "回答评价总得分")
    private Integer answerCountSize;

    @Schema(description = "问题编号")
    private String detailId;

    @Schema(description = "日期")
    private LocalDateTime createTime;

    @Schema(description = "周次")
    private String week;


    @Schema(description = "月份")
    private String month;


    @Schema(description = "数据")
    private List<KeyValue<String, Long>> data;

    @Schema(description = "扩展表头")
    private List<String> headers;
}
