package cn.iocoder.yudao.module.fis.service.problem;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemAnswerCreateReqVO;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemAnswerExportReqVO;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemAnswerPageReqVO;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemAnswerUpdateReqVO;
import cn.iocoder.yudao.module.fis.dal.dataobject.problem.ProblemAnswerDO;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 问题回答 Service 接口
 *
 * @author chiwd
 */
public interface ProblemAnswerService {

    /**
     * 创建问题回答
     *
     * @param file        文件
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProblemAnswer(MultipartFile file, @Valid ProblemAnswerCreateReqVO createReqVO);

    /**
     * 更新问题回答
     *
     * @param updateReqVO 更新信息
     */
    void updateProblemAnswer(@Valid ProblemAnswerUpdateReqVO updateReqVO);

    /**
     * 删除问题回答
     *
     * @param id 编号
     */
    void deleteProblemAnswer(Long id);

    /**
     * 获得问题回答
     *
     * @param id 编号
     * @return 问题回答
     */
    ProblemAnswerDO getProblemAnswer(Long id);

    /**
     * 获得问题回答列表
     *
     * @param ids 编号
     * @return 问题回答列表
     */
    List<ProblemAnswerDO> getProblemAnswerList(Collection<Long> ids);


    /**
     * 获得问题回答列表
     *
     * @param problemId 问题id
     * @return 回答列表
     */
    List<ProblemAnswerDO> getProblemAnswerList(Long problemId);

    /**
     * 获得问题回答分页
     *
     * @param pageReqVO 分页查询
     * @return 问题回答分页
     */
    PageResult<ProblemAnswerDO> getProblemAnswerPage(ProblemAnswerPageReqVO pageReqVO);

    /**
     * 获得问题回答列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 问题回答列表
     */
    List<ProblemAnswerDO> getProblemAnswerList(ProblemAnswerExportReqVO exportReqVO);


    /**
     * 删除问题id匹配的所有回答
     *
     * @param problemId 问题ID
     */
    void deleteAnswersByProblemId(Long problemId);

    /**
     * 查询问题对应的回答是否存在
     *
     * @param id 问题id
     * @return 是否存在
     */
    Boolean getHasProblemAnswer(Long id);
}
