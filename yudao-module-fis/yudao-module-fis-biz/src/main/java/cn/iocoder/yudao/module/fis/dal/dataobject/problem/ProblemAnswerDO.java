package cn.iocoder.yudao.module.fis.dal.dataobject.problem;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 问题回答 DO
 *
 * @author chiwd
 */
@TableName("problem_answer")
@KeySequence("problem_answer_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemAnswerDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 问题编号
     */
    private Long problemId;
    /**
     * 回答人工号
     */
    private String answerId;
    /**
     * 回答人姓名
     */
    private String answerName;
    /**
     * 回答时间
     */
    private LocalDateTime answerDate;
    /**
     * 回答内容
     */
    private String answerContent;
    /**
     * 回答文件ID
     */
    private Long answerFileId;
    /**
     * 回答附件
     */
    private String answerAttached;
    /**
     * 回答评分
     */
    private Integer answerScore;

}
