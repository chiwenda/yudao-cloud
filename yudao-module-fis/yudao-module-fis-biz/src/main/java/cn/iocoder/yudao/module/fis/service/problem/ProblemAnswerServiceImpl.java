package cn.iocoder.yudao.module.fis.service.problem;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemAnswerCreateReqVO;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemAnswerExportReqVO;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemAnswerPageReqVO;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemAnswerUpdateReqVO;
import cn.iocoder.yudao.module.fis.convert.problem.ProblemAnswerConvert;
import cn.iocoder.yudao.module.fis.dal.dataobject.problem.ProblemAnswerDO;
import cn.iocoder.yudao.module.fis.dal.mysql.problem.ProblemAnswerMapper;
import cn.iocoder.yudao.module.fis.dal.mysql.problem.ProblemInfoMapper;
import cn.iocoder.yudao.module.infra.api.file.FileApi;
import cn.iocoder.yudao.module.infra.api.file.dto.FileCreateReqDTO;
import cn.iocoder.yudao.module.infra.api.file.dto.FileRespDTO;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fis.enums.ErrorCodeConstants.*;


/**
 * 问题回答 Service 实现类
 *
 * @author chiwd
 */
@DS("problem")
@Service
@Validated
public class ProblemAnswerServiceImpl implements ProblemAnswerService {

    @Resource
    private ProblemAnswerMapper problemAnswerMapper;
    @Resource
    private ProblemInfoMapper problemInfoMapper;


    @Resource
    private SystemService systemService;
    @Value("${yudao.fis.oss.store.path.problem.answer}")
    private String path;

    @Resource
    private FileApi fileApi;


    @Override
    public Long createProblemAnswer(MultipartFile file, ProblemAnswerCreateReqVO createReqVO) {
        //校验问题是否存在
        if (problemInfoMapper.selectById(createReqVO.getProblemId()) == null) {
            throw exception(PROBLEM_INFO_NOT_EXISTS);
        }
        //用户信息
        AdminUserRespDTO user = systemService.getLoginUser();
        //已回答过的问题更新
        ProblemAnswerDO answer = problemAnswerMapper.queryAnswerCount(createReqVO.getProblemId(), user.getUsername());
        if (null != answer) {
            answer.setAnswerAttached(createReqVO.getAnswerAttached());
            answer.setAnswerContent(createReqVO.getAnswerContent());
            problemAnswerMapper.updateById(answer);
            return answer.getId();
        }
        //有附件 上传
        if (null != file) {
            //上传文件到阿里云OSS
            try {
                byte[] bytes = file.getBytes();
                if (bytes.length == 0) {
                    throw exception(FILE_CONTENT_NULL);
                }
                String answerCode = LocalDateTimeUtil.format(LocalDateTimeUtil.now(), "yyyyMMddHHmmss");
                String fileName = String.format("%s-%s-%s", "answer", answerCode, file.getOriginalFilename());
                String path = String.format("%s/%s"
                        , String.format("%s/%s", this.path, LocalDateTimeUtil.format(LocalDateTime.now(), "yyyyMMdd"))
                        , fileName);
                FileRespDTO fileResp = fileApi.createResp(new FileCreateReqDTO()
                        .setName(fileName)
                        .setPath(path).setContent(bytes)).getCheckedData();
                createReqVO.setAnswerAttached(file.getOriginalFilename());
                createReqVO.setAnswerFileId(fileResp.getId());
            } catch (Exception e) {
                throw exception(FILE_UPLOAD_ERROR, e.getMessage());
            }

        }
        // 插入
        createReqVO.setAnswerId(user.getUsername());
        createReqVO.setAnswerName(user.getNickname());
        createReqVO.setAnswerDate(LocalDateTime.now());
        ProblemAnswerDO problemAnswer = ProblemAnswerConvert.INSTANCE.convert(createReqVO);
        problemAnswerMapper.insert(problemAnswer);
        // 返回
        return problemAnswer.getId();
    }

    @Override
    public void updateProblemAnswer(ProblemAnswerUpdateReqVO updateReqVO) {
        // 校验存在
        validateProblemAnswerExists(updateReqVO.getId());
        // 更新
        ProblemAnswerDO updateObj = ProblemAnswerConvert.INSTANCE.convert(updateReqVO);
        problemAnswerMapper.updateById(updateObj);
    }

    @Override
    public void deleteProblemAnswer(Long id) {
        // 校验存在
        validateProblemAnswerExists(id);
        // 删除
        problemAnswerMapper.deleteById(id);
    }

    private void validateProblemAnswerExists(Long id) {
        if (problemAnswerMapper.selectById(id) == null) {
            throw exception(PROBLEM_ANSWER_NOT_EXISTS);
        }
    }

    @Override
    public ProblemAnswerDO getProblemAnswer(Long id) {
        return problemAnswerMapper.selectById(id);
    }

    @Override
    public List<ProblemAnswerDO> getProblemAnswerList(Collection<Long> ids) {
        return problemAnswerMapper.selectBatchIds(ids);
    }

    @Override
    public List<ProblemAnswerDO> getProblemAnswerList(Long problemId) {
        return problemAnswerMapper.selectList(ProblemAnswerDO::getProblemId, problemId);
    }

    @Override
    public PageResult<ProblemAnswerDO> getProblemAnswerPage(ProblemAnswerPageReqVO pageReqVO) {
        //只查询我的回答
        //用户信息
        Long userId = systemService.getLoginUser().getId();
        pageReqVO.setCreator(userId.toString());
        return problemAnswerMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ProblemAnswerDO> getProblemAnswerList(ProblemAnswerExportReqVO exportReqVO) {
        return problemAnswerMapper.selectList(exportReqVO);
    }

    @Override
    public void deleteAnswersByProblemId(Long problemId) {
        problemAnswerMapper.deleteByProblemId(problemId);
    }

    @Override
    public Boolean getHasProblemAnswer(Long id) {
        AdminUserRespDTO user = systemService.getLoginUser();
        ProblemAnswerDO answer = problemAnswerMapper.queryAnswerCount(id, user.getUsername());
        return null != answer;
    }


}
