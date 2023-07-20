package cn.iocoder.yudao.module.fis.service.problem;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.FilePageReqVO;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.FileRespVO;
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

    /**
     * 查询文件分页
     *
     * @param pageVO 参数
     * @return 文件列表
     */
    PageResult<FileRespVO> getFilePage(FilePageReqVO pageVO);

    /**
     * 删除上传的文件
     *
     * @param id 文件ID
     * @return 是否删除成功
     */
    Boolean deleteFile(Long id);
}
