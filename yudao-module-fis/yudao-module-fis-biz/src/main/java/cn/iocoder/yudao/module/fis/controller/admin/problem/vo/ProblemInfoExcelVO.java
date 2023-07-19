package cn.iocoder.yudao.module.fis.controller.admin.problem.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 问题信息 Excel VO
 *
 * @author chiwd
 */
@Data
public class ProblemInfoExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("问题详情编号")
    private String detailId;

    @ExcelProperty("提出人工号")
    private String pubWorkId;

    @ExcelProperty("提出人姓名")
    private String pubUserName;

    @ExcelProperty("问题名称")
    private String problemName;

    @ExcelProperty("问题描述")
    private String problemDescribe;

    @ExcelProperty("问题附件")
    private String problemAttached;

    @ExcelProperty("指定回答人工号")
    private String subWorkId;

    @ExcelProperty(value = "问题分类", converter = DictConvert.class)
    @DictFormat("pc_problem_type") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private String problemType;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
