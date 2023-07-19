package cn.iocoder.yudao.module.fis.service.problem;

import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemReportReqVO;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemReportRespVO;

import java.util.List;

public interface ProblemService {

    /**
     * 从ftp下载文件
     *
     * @param path 路径
     * @return 内容
     */
    byte[] downloadFromOss(String path);


    /**
     * 问题报表
     *
     * @param reqVO 参数
     * @return 报表数据
     */
    List<ProblemReportRespVO> getProblemReport(ProblemReportReqVO reqVO);

}
