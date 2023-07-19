package cn.iocoder.yudao.module.fis.controller.admin.problem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;

@Schema(description = "管理后台 - 问题信息部门 Request VO")
@Data
@ToString(callSuper = true)
public class ProblemPostReqVO {

    @Schema(description = "部门id")
    @NotNull(message = "部门id不能为空")
    private Collection<Long> ids;
}
