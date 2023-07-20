package cn.iocoder.yudao.module.infra.api.file.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "RPC 服务 - 文件分页 Response DTO")
@Data
public class FileRespDTO {
    @Schema(description = "文件编号", required = true, example = "1024")
    private Long id;

    @Schema(description = "文件名称", required = true)
    private String name;

    @Schema(description = "文件路径", required = true)
    private String path;

    @Schema(description = "文件 URL", required = true)
    private String url;

    @Schema(description = "文件类型", example = "jpg")
    private String type;

    @Schema(description = "文件大小", example = "2048", required = true)
    private Integer size;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;
}
