package cn.iocoder.yudao.module.fis.controller.admin.problem.vo;

import cn.iocoder.yudao.module.fis.dal.dataobject.problem.ProblemAnswerDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 问题信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProblemInfoRespVO extends ProblemInfoBaseVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "13187")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;


    @Schema(description = "问题的所有回答", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ProblemAnswerDO> answers;

    @Schema(description = "当前用户是否回答过", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isMeAnswer;

}
