package com.by.judge.service;

/**
 * @author lzh
 */
public interface JudgeService {

    /**
     * 执行判题
     *
     * @param questionSubmitId 题目提交信息ID
     */
    void doJudge(long questionSubmitId);
}
