package cn.iocoder.yudao.module.fis.service.problem;

import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemUserPostRespVO;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;

import java.util.Collection;
import java.util.List;

public interface SystemService {
    /**
     * 获取登录用户信息
     *
     * @return 登录用户信息
     */
    AdminUserRespDTO getLoginUser();

    List<ProblemUserPostRespVO> getUsersByPostId(Collection<Long> ids);


}
