package cn.iocoder.yudao.module.fis.service.problem;


import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemUserPostRespVO;
import cn.iocoder.yudao.module.fis.convert.problem.ProblemInfoConvert;
import cn.iocoder.yudao.module.system.api.dept.PostApi;
import cn.iocoder.yudao.module.system.api.dept.dto.PostReqDTO;
import cn.iocoder.yudao.module.system.api.dept.dto.PostRespDTO;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 调用系统模块接口
 */
@DS("master")
@Service
public class SystemServiceImpl implements SystemService {
    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private PostApi postApi;

    @Override
    public AdminUserRespDTO getLoginUser() {
        //获取登录用户信息
        return adminUserApi.getUser(SecurityFrameworkUtils.getLoginUserId()).getCheckedData();
    }

    @Override
    public List<ProblemUserPostRespVO> getUsersByPostId(Collection<Long> ids) {
        List<AdminUserRespDTO> users = adminUserApi.getUsersByPostIds(ids).getCheckedData();
        List<ProblemUserPostRespVO> userPostRespVOList = ProblemInfoConvert.INSTANCE.convertList0(users);
        return userPostRespVOList.stream().map(e -> {
            Set<Long> postIds = e.getPostIds();
            List<PostRespDTO> posts = new ArrayList<>();
            PostReqDTO reqDTO = new PostReqDTO();
            postIds.forEach(id -> {
                reqDTO.setPostId(id);
                PostRespDTO data = postApi.getPost(reqDTO).getCheckedData();
                posts.add(data);
            });
            e.setPosts(posts);
            return e;
        }).collect(Collectors.toList());
    }
}
