package cn.iocoder.yudao.module.infra.api.file.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "RPC 服务 - 文件分页 Request DTO")
@Data
public class FilePageReqDTO {
    @Schema(description = "创建人ID", example = "1")
    private String creator;

    @Schema(description = "文件名称")
    private String name;

    @Schema(description = "创建时间", example = "[2022-07-01 00:00:00, 2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;
}
