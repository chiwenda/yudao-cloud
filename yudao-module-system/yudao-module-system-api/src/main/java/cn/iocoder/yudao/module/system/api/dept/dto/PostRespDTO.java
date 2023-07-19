package cn.iocoder.yudao.module.system.api.dept.dto;

import lombok.Data;

/**
 * 岗位信息
 *
 * @author ChiWD01
 */
@Data
public class PostRespDTO {


    /**
     * 岗位序号
     */
    private Long id;
    /**
     * 岗位名称
     */
    private String name;
    /**
     * 岗位编码
     */
    private String code;
    /**
     * 岗位排序
     */
    private Integer sort;
    /**
     * 状态
     * <p>
     * 枚举 {@link com.catl.fis.framework.common.enums.CommonStatusEnum}
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
}
