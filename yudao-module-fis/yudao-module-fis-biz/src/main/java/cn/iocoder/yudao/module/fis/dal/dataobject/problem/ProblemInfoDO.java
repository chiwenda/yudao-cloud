package cn.iocoder.yudao.module.fis.dal.dataobject.problem;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 问题信息 DO
 *
 * @author chiwd
 */
@TableName("problem_info")
@KeySequence("problem_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemInfoDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 问题详情编号
     */
    private String detailId;
    /**
     * 提出人工号
     */
    private String pubWorkId;
    /**
     * 提出人姓名
     */
    private String pubUserName;
    /**
     * 问题名称
     */
    private String problemName;
    /**
     * 问题描述
     */
    private String problemDescribe;
    /**
     * 问题附件ID
     */
    private Long problemFileId;
    /**
     * 问题附件
     */
    private String problemAttached;
    /**
     * 指定回答人工号
     */
    private String subWorkId;
    /**
     * 问题分类
     * <p>
     * 枚举 {@link TODO pc_problem_type 对应的类}
     */
    private String problemType;

}
