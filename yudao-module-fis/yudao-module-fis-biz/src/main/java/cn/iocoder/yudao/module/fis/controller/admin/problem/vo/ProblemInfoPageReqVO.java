package cn.iocoder.yudao.module.fis.controller.admin.problem.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 问题信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProblemInfoPageReqVO extends PageParam {

    @Schema(description = "问题详情编号", example = "19737")
    private String detailId;

    @Schema(description = "提出人工号", example = "7222")
    private String pubWorkId;

    @Schema(description = "提出人姓名", example = "李四")
    private String pubUserName;

    @Schema(description = "问题名称", example = "张三")
    private String problemName;

    @Schema(description = "问题描述")
    private String problemDescribe;

    @Schema(description = "问题附件")
    private String problemAttached;

    @Schema(description = "指定回答人工号", example = "4804")
    private String subWorkId;

    @Schema(description = "问题分类", example = "2")
    private String problemType;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
