package cn.iocoder.yudao.module.fis.service.problem;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.*;
import cn.iocoder.yudao.module.fis.convert.problem.ProblemInfoConvert;
import cn.iocoder.yudao.module.fis.dal.dataobject.problem.ProblemInfoDO;
import cn.iocoder.yudao.module.fis.dal.mysql.problem.ProblemInfoMapper;
import cn.iocoder.yudao.module.infra.api.file.FileApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fis.enums.ErrorCodeConstants.*;


/**
 * 问题信息 Service 实现类
 *
 * @author chiwd
 */
@DS("problem")
@Service
@Validated
@Slf4j
public class ProblemInfoServiceImpl implements ProblemInfoService {

    @Resource
    private ProblemInfoMapper problemInfoMapper;


    @Resource
    private ProblemAnswerService problemAnswerService;

    @Resource
    private SystemService systemService;

    @Value("${fis.oss.store.path.problem.issue}")
    private String path;

    @Resource
    private FileApi fileApi;


    @DSTransactional
    @Override
    public Long createProblemInfo(MultipartFile file, ProblemInfoCreateReqVO createReqVO) {

        //有附件 上传
        if (null != file) {
            //上传文件到阿里云OSS
            try {
                byte[] bytes = file.getBytes();
                if (bytes.length == 0) {
                    throw exception(FILE_CONTENT_NULL);
                }
                createReqVO.setProblemAttached(fileApi.createFile(String.format("%s/%s"
                        , String.format("%s/%s", path, LocalDateTimeUtil.format(LocalDateTime.now(), "yyyyMMdd"))
                        , file.getOriginalFilename()), bytes));
            } catch (Exception e) {
                throw exception(FILE_UPLOAD_ERROR, e.getMessage());
            }

        }
        //用户信息
        AdminUserRespDTO user = systemService.getLoginUser();
        createReqVO.setDetailId(LocalDateTimeUtil.format(LocalDateTimeUtil.now(), "yyyyMMddHHmmss"));
        createReqVO.setPubWorkId(user.getUsername());
        createReqVO.setPubUserName(user.getNickname());
        // 插入
        ProblemInfoDO problemInfo = ProblemInfoConvert.INSTANCE.convert(createReqVO);
        problemInfoMapper.insert(problemInfo);
        // 返回
        return problemInfo.getId();
    }

    @Override
    public void updateProblemInfo(ProblemInfoUpdateReqVO updateReqVO) {
        // 校验存在
        validateProblemInfoExists(updateReqVO.getId());
        // 更新
        ProblemInfoDO updateObj = ProblemInfoConvert.INSTANCE.convert(updateReqVO);
        problemInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteProblemInfo(Long id) {
        // 校验存在
        validateProblemInfoExists(id);
        //删除问题的所有回答
        problemAnswerService.deleteAnswersByProblemId(id);
        // 删除
        problemInfoMapper.deleteById(id);
    }

    private void validateProblemInfoExists(Long id) {
        if (problemInfoMapper.selectById(id) == null) {
            throw exception(PROBLEM_INFO_NOT_EXISTS);
        }
    }

    @Override
    public ProblemInfoDO getProblemInfo(Long id) {
        return problemInfoMapper.selectById(id);
    }

    @Override
    public List<ProblemInfoDO> getProblemInfoList(Collection<Long> ids) {
        return problemInfoMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ProblemInfoDO> getProblemInfoPage(ProblemInfoPageReqVO pageReqVO) {
        AdminUserRespDTO user = systemService.getLoginUser();
        pageReqVO.setSubWorkId(user.getUsername());
        return problemInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<ProblemInfoDO> getProblemInfoPageForIssue(ProblemInfoPageReqVO pageReqVO) {
        AdminUserRespDTO user = systemService.getLoginUser();
        pageReqVO.setPubWorkId(user.getUsername());
        return problemInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ProblemInfoDO> getProblemInfoList(ProblemInfoExportReqVO exportReqVO) {
        return problemInfoMapper.selectList(exportReqVO);
    }

    @Override
    public List<ProblemUserPostRespVO> getUsersByPostId(Collection<Long> ids) {
        return systemService.getUsersByPostId(ids);
    }

    @Override
    public List<ProblemInfoRespVO> addIsMeAnswer(List<ProblemInfoRespVO> data) {
        return data.stream().map(e -> {
            e.setIsMeAnswer(problemAnswerService.getHasProblemAnswer(e.getId()));
            return e;
        }).collect(Collectors.toList());

    }

}
