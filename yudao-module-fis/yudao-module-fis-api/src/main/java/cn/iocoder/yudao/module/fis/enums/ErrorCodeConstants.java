package cn.iocoder.yudao.module.fis.enums;


import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * pc 错误码枚举类
 * <p>
 * system 系统，使用 1-003-000-000 段
 */
public interface ErrorCodeConstants {
    // ========== 问题管理模块 1002003000 ==========
    ErrorCode PROBLEM_USER_NOT_EXISTS = new ErrorCode(1003000000, "指定用户不存在");
    ErrorCode PROBLEM_ANSWER_NOT_EXISTS = new ErrorCode(1003000001, "问题回答不存在");
    ErrorCode PROBLEM_INFO_NOT_EXISTS = new ErrorCode(1003000002, "问题不存在或已被删除");
    ErrorCode FILE_UPLOAD_ERROR = new ErrorCode(1003000003, "文件上传失败:{}");
    ErrorCode FILE_DOWNLOAD_ERROR = new ErrorCode(1003000004, "文件下载失败");
    ErrorCode PROBLEM_ALREADY_ANSWER = new ErrorCode(1003000005, "问题已回答");
    ErrorCode SPACE_DIMENSION_NOT_NULL = new ErrorCode(1003000006, "空间维度必选");
    ErrorCode FILE_CONTENT_NULL = new ErrorCode(1003000007, "文件内容不能为空");
    ErrorCode FILE_NOT_EXISTS = new ErrorCode(1003000008, "文件不存在或已被删除");
}
