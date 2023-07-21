package cn.iocoder.yudao.module.fis.service.problem;


import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.*;
import cn.iocoder.yudao.module.fis.dal.dataobject.problem.ProblemInfoDO;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 问题信息 Service 接口
 *
 * @author chiwd
 */
public interface ProblemInfoService {

    /**
     * 创建问题信息
     *
     * @param file        文件
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProblemInfo(MultipartFile file, @Valid ProblemInfoCreateReqVO createReqVO);

    /**
     * 更新问题信息
     *
     * @param updateReqVO 更新信息
     */
    void updateProblemInfo(@Valid ProblemInfoUpdateReqVO updateReqVO);

    /**
     * 删除问题信息
     *
     * @param id 编号
     */
    void deleteProblemInfo(Long id);

    /**
     * 获得问题信息
     *
     * @param id 编号
     * @return 问题信息
     */
    ProblemInfoDO getProblemInfo(Long id);

    /**
     * 获得问题信息列表
     *
     * @param ids 编号
     * @return 问题信息列表
     */
    List<ProblemInfoDO> getProblemInfoList(Collection<Long> ids);

    /**
     * 获得问题信息分页(指派给我的)
     *
     * @param pageReqVO 分页查询
     * @return 问题信息分页
     */
    PageResult<ProblemInfoDO> getProblemInfoPage(ProblemInfoPageReqVO pageReqVO);

    /**
     * 获得问题信息分页(我提问的)
     *
     * @param pageReqVO 分页查询
     * @return 问题信息分页
     */
    PageResult<ProblemInfoDO> getProblemInfoPageForIssue(ProblemInfoPageReqVO pageReqVO);

    /**
     * 获得问题信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 问题信息列表
     */
    List<ProblemInfoDO> getProblemInfoList(ProblemInfoExportReqVO exportReqVO);


    /**
     * 岗位ID获取匹配的用户
     *
     * @param ids 岗位id
     * @return 用户
     */
    List<ProblemUserPostRespVO> getUsersByPostId(Collection<Long> ids);

    /**
     * 添加是否回答
     *
     * @param data 数据
     * @return 问题
     */
    List<ProblemInfoRespVO> addIsMeAnswer(List<ProblemInfoRespVO> data);

    void validateProblemInfoExists(Long id);
}
