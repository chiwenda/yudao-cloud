package cn.iocoder.yudao.module.fis.controller.admin.problem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 问题信息更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProblemInfoUpdateReqVO extends ProblemInfoBaseVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "13187")
    @NotNull(message = "主键不能为空")
    private Long id;

}
