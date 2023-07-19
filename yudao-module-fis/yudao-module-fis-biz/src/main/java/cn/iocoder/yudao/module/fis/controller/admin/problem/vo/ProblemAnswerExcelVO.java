package cn.iocoder.yudao.module.fis.controller.admin.problem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 问题回答 Excel VO
 *
 * @author chiwd
 */
@Data
public class ProblemAnswerExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("问题编号")
    private Long problemId;

    @ExcelProperty("回答人姓名")
    private String answerName;

    @ExcelProperty("回答时间")
    private LocalDateTime answerDate;

    @ExcelProperty("回答内容")
    private String answerContent;

    @ExcelProperty("回答附件")
    private String answerAttached;

    @ExcelProperty("回答评分")
    private Integer answerScore;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
