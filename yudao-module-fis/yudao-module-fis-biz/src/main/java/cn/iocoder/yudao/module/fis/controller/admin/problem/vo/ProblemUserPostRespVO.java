package cn.iocoder.yudao.module.fis.controller.admin.problem.vo;


import cn.iocoder.yudao.module.system.api.dept.dto.PostRespDTO;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ProblemUserPostRespVO {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户账号
     */
    private String username;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 帐号状态
     * <p>
     * 枚举 {@link cn.iocoder.yudao.framework.common.enums.CommonStatusEnum}
     */
    private Integer status;

    /**
     * 部门ID
     */
    private Long deptId;
    /**
     * 岗位编号数组
     */
    private Set<Long> postIds;
    /**
     * 手机号码
     */
    private String mobile;


    private List<PostRespDTO> posts;
}
