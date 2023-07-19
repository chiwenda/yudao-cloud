package cn.iocoder.yudao.module.fis.service.problem;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.iocoder.yudao.framework.common.core.KeyValue;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemReportReqVO;
import cn.iocoder.yudao.module.fis.controller.admin.problem.vo.ProblemReportRespVO;
import cn.iocoder.yudao.module.fis.dal.mysql.problem.ProblemMapper;
import cn.iocoder.yudao.module.fis.enums.PcConstants;
import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fis.enums.ErrorCodeConstants.SPACE_DIMENSION_NOT_NULL;


@DS("problem")
@Service
@Slf4j
public class ProblemServiceImpl implements ProblemService {


    @Resource
    private ProblemMapper problemMapper;

    @Resource
    private SystemService systemService;


    @Override
    public byte[] downloadFromOss(String path) {
        try {

            return HttpUtil.downloadBytes(path);
        } catch (Exception e) {
            log.error(e.getMessage());

        }
        return new byte[0];
    }

    @Override
    public List<ProblemReportRespVO> getProblemReport(ProblemReportReqVO reqVO) {
        String username = systemService.getLoginUser().getUsername();
        List<ProblemReportRespVO> data = new ArrayList<>();
        if (Objects.equals(PcConstants.PROBLEM_REPORT, reqVO.getReportType())) {
            data = problemMapper.selectProblemReportInfo(username, reqVO.getTimeSpan());
        }
        if (Objects.equals(PcConstants.ANSWER_REPORT, reqVO.getReportType())) {
            data = problemMapper.selectAnswerReportInfo(username, reqVO.getTimeSpan());
        }

        List<ProblemReportRespVO> result = new ArrayList<>();

        //都没选抛出异常
        if (reqVO.getSpaceDimension().size() == 0) {
            throw exception(SPACE_DIMENSION_NOT_NULL);
        }
        //空间维度选两个
        if (reqVO.getSpaceDimension().size() == 2) {
            //回答人姓名+问题类型为key分组构建
            Map<String, List<ProblemReportRespVO>> searchData = data.stream()
                    .collect(Collectors.groupingBy(g -> g.getAnswerId() + g.getAnswerName() + g.getProblemType()));

            //遍历查询的数据，如果key匹配，构建分组
            for (ProblemReportRespVO p : data) {
                //去重跳过
                if (result.stream().anyMatch(e -> Objects.equals(e.getAnswerId(), p.getAnswerId())
                        && Objects.equals(e.getAnswerName(), p.getAnswerName()) &&
                        Objects.equals(e.getProblemType(), p.getProblemType()))) {
                    continue;
                }
                //作为要按时间分组的列表
                List<ProblemReportRespVO> times = searchData.get(p.getAnswerId() + p.getAnswerName() + p.getProblemType());
                List<KeyValue<String, Long>> kvs = new ArrayList<>();
                //时间维度
                getTimeDimension(reqVO, result, p, times, kvs);

            }
        }
        //空间维度选择一个
        else {
            //选择回答人姓名
            if (reqVO.getSpaceDimension().get(0).equals(0)) {
                //回答人姓名构建查询分组
                Map<String, List<ProblemReportRespVO>> searchData = data.stream()
                        .collect(Collectors.groupingBy(g -> g.getAnswerId() + g.getAnswerName()));

                //遍历查询的数据，如果key匹配，构建日期分组
                for (ProblemReportRespVO p : data) {
                    //去重跳过
                    if (result.stream().anyMatch(e -> Objects.equals(e.getAnswerId(), p.getAnswerId())
                            && Objects.equals(e.getAnswerName(), p.getAnswerName()))) {
                        continue;
                    }
                    List<ProblemReportRespVO> times = searchData.get(p.getAnswerId() + p.getAnswerName());
                    List<KeyValue<String, Long>> kvs = new ArrayList<>();
                    //时间维度
                    getTimeDimension(reqVO, result, p, times, kvs);
                }
            }
            //选择分类
            else {
                //分类构建查询分组
                Map<String, List<ProblemReportRespVO>> searchData = data.stream()
                        .collect(Collectors.groupingBy(ProblemReportRespVO::getProblemType));
                //遍历查询的数据，如果key匹配，构建日期分组
                for (ProblemReportRespVO p : data) {
                    //去重跳过
                    if (result.stream().anyMatch(e -> Objects.equals(e.getProblemType(), p.getProblemType()))) {
                        continue;
                    }
                    //作为要按时间分组的列表
                    List<ProblemReportRespVO> times = searchData.get(p.getProblemType());
                    List<KeyValue<String, Long>> kvs = new ArrayList<>();
                    //时间维度
                    getTimeDimension(reqVO, result, p, times, kvs);
                }
            }
        }

        //抽取组成扩展表头
        List<KeyValue<String, Long>> kvs = new ArrayList<>();
        result.forEach(p -> kvs.addAll(p.getData()));
        List<String> headers = kvs.stream().map(KeyValue::getKey).distinct().collect(Collectors.toList());
        //头部排序
        if (headers.stream().anyMatch(h -> h.contains(StrUtil.SLASH))) {
            Map<LocalDate, String> mp = headers.stream().collect(Collectors.toMap(h -> {
                String pre = StrUtil.splitTrim(h, StrUtil.SLASH).get(0);
                String sub = StrUtil.splitTrim(h, StrUtil.SLASH).get(1);
                return LocalDate.of(2000, Integer.parseInt(pre), Integer.parseInt(sub));
            }, h -> h));
            TreeMap<LocalDate, String> tMp = new TreeMap<>(mp);
            headers = tMp.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        }

        List<String> finalHeaders = headers;
        result = result.stream().map(p -> {
            p.setHeaders(finalHeaders);
            //没有值的重新填充为0
            if (p.getData().size() == finalHeaders.size()) return p;
            Map<String, Long> kvMap = p.getData().stream().collect(Collectors.toMap(KeyValue::getKey, KeyValue::getValue));
            finalHeaders.forEach(h -> {
                if (!kvMap.containsKey(h)) {
                    if (null == p.getData()) {
                        List<KeyValue<String, Long>> lstKv = new ArrayList<>();
                        KeyValue<String, Long> emptyKv = new KeyValue<>();
                        emptyKv.setKey(h);
                        emptyKv.setValue(0L);
                        lstKv.add(emptyKv);
                        p.setData(lstKv);
                    } else {
                        KeyValue<String, Long> emptyKv = new KeyValue<>();
                        emptyKv.setKey(h);
                        emptyKv.setValue(0L);
                        p.getData().add(emptyKv);
                    }
                }
            });
            return p;
        }).collect(Collectors.toList());


        return result;
    }

    private static void getTimeDimension(ProblemReportReqVO reqVO, List<ProblemReportRespVO> result, ProblemReportRespVO p,
                                         List<ProblemReportRespVO> times, List<KeyValue<String, Long>> kvs) {
        //日期维度 遍历时间为key的map
        if (Objects.equals(reqVO.getTimeDimension(), 0)) {
            times.stream().sorted(Comparator.comparing(ProblemReportRespVO::getCreateTime))
                    .collect(Collectors.groupingBy(ProblemReportRespVO::getCreateTime))
                    //遍历时间为key的map
                    .forEach((key, values) -> {
                        KeyValue<String, Long> kv = new KeyValue<>();
                        kv.setKey(String.format("%s/%s", key.getMonthValue(), key.getDayOfMonth()));
                        //判断每个值,回答人工号+回答人姓名+问题类型相同抽出并相加
                        AtomicReference<Long> total = new AtomicReference<>(0L);
                        renderAnPlusTotal(reqVO, p, values, total);
                        kv.setValue(total.get());
                        kvs.add(kv);
                    });
            p.setData(kvs);

            result.add(p);
        }
        //周次维度则直接按key分组
        else if (Objects.equals(reqVO.getTimeDimension(), 1)) {
            times.stream().collect(Collectors.groupingBy(ProblemReportRespVO::getWeek))
                    //遍历时间为key的map
                    .forEach((key, values) -> {
                        KeyValue<String, Long> kv = new KeyValue<>();
                        kv.setKey(key);
                        //判断每个值,回答人工号+回答人姓名+问题类型相同抽出并相加
                        AtomicReference<Long> total = new AtomicReference<>(0L);
                        //只选了回答人姓名空间维度
                        renderAnPlusTotal(reqVO, p, values, total);
                        kv.setValue(total.get());
                        kvs.add(kv);
                    });
            p.setData(kvs);
            result.add(p);
        }
        //月份维度
        else {
            times.stream().collect(Collectors.groupingBy(ProblemReportRespVO::getMonth))
                    //遍历时间为key的map
                    .forEach((key, values) -> {
                        KeyValue<String, Long> kv = new KeyValue<>();
                        kv.setKey(key);
                        //判断每个值,回答人工号+回答人姓名+问题类型相同抽出并相加
                        AtomicReference<Long> total = new AtomicReference<>(0L);
                        //只选了回答人姓名空间维度
                        renderAnPlusTotal(reqVO, p, values, total);
                        kv.setValue(total.get());
                        kvs.add(kv);
                    });
            p.setData(kvs);
            result.add(p);
        }
    }

    private static void renderAnPlusTotal(ProblemReportReqVO reqVO, ProblemReportRespVO p, List<ProblemReportRespVO> values, AtomicReference<Long> total) {
        //只选了回答人姓名空间维度
        if (reqVO.getSpaceDimension().size() == 1 && reqVO.getSpaceDimension().contains(0)) {
            values.forEach(v -> {
                if (Objects.equals(p.getAnswerId(), v.getAnswerId()) &&
                        Objects.equals(p.getAnswerName(), v.getAnswerName())) {
                    setValueByShowType(reqVO, total, v);

                }
            });
        }
        //只选择问题分类
        else if (reqVO.getSpaceDimension().size() == 1 && reqVO.getSpaceDimension().contains(1)) {
            values.forEach(v -> {
                if (Objects.equals(p.getProblemType(), v.getProblemType())
                ) {
                    //问题数量
                    setValueByShowType(reqVO, total, v);
                }
            });
        } else {
            values.forEach(v -> {
                if (Objects.equals(p.getAnswerId(), v.getAnswerId()) && Objects.equals(p.getAnswerName(), v.getAnswerName()) &&
                        Objects.equals(p.getProblemType(), v.getProblemType())
                ) {
                    //问题数量
                    setValueByShowType(reqVO, total, v);
                }
            });
        }
    }

    private static void setValueByShowType(ProblemReportReqVO reqVO, AtomicReference<Long> total, ProblemReportRespVO v) {
        //问题数量
        if (Objects.equals(reqVO.getType(), 0)) {
            total.updateAndGet(v1 -> {
                if (null == v.getProblemSize()) {
                    return v1;
                }
                return v1 + v.getProblemSize().longValue();
            });
        }
        //回答数量
        if (Objects.equals(reqVO.getType(), 1)) {
            total.updateAndGet(v1 -> {
                if (null == v.getAnswerSize()) {
                    return v1;
                }
                return v1 + v.getAnswerSize().longValue();
            });
        }
        //获得评价数量
        if (Objects.equals(reqVO.getType(), 2)) {
            total.updateAndGet(v1 -> {
                if (null == v.getCommentSize()) {
                    return v1;
                }
                return v1 + v.getCommentSize().longValue();
            });
        }
        //回答评价总得分
        if (Objects.equals(reqVO.getType(), 3)) {
            total.updateAndGet(v1 -> {
                if (null == v.getAnswerCountSize()) {
                    return v1;
                }
                return v1 + v.getAnswerCountSize().longValue();
            });
        }
    }


}
