package cn.iocoder.yudao.module.fis.framework.datapermission.config;

import cn.iocoder.yudao.framework.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import cn.iocoder.yudao.module.fis.dal.dataobject.problem.ProblemAnswerDO;
import cn.iocoder.yudao.module.fis.dal.dataobject.problem.ProblemInfoDO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FIS模块的数据权限
 * @author ChiWD01
 */
@Configuration(proxyBeanMethods = false)
public class DataPermissionConfiguration {

    @Bean
    public DeptDataPermissionRuleCustomizer sysDeptDataPermissionRuleCustomizer() {
        return rule -> {
            // 配置数据权限为只能看见自己的
            rule.addUserColumn(ProblemAnswerDO.class, "creator");
            rule.addUserColumn(ProblemInfoDO.class, "creator");
        };
    }
}
