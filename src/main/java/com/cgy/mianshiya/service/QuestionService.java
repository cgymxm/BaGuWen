package com.cgy.mianshiya.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cgy.mianshiya.model.dto.question.QuestionAddRequest;
import com.cgy.mianshiya.model.dto.question.QuestionQueryRequest;
import com.cgy.mianshiya.model.dto.question.QuestionUpdateRequest;
import com.cgy.mianshiya.model.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author cgy
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2024-11-05 17:22:43
*/
public interface QuestionService extends IService<Question> {

    void validQuestion(Question question,boolean add);

    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    /**
     * 添加题目
     * @param questionAddRequest
     * @param request
     * @return
     */
    Long AddQuestion(QuestionAddRequest questionAddRequest, HttpServletRequest request);

    /**
     * 更新题目
     * @param questionUpdateRequest
     * @param request
     * @return
     */
    Boolean updateQuestion(QuestionUpdateRequest questionUpdateRequest,HttpServletRequest request);

    /**
     * 分页获取题目列表（仅管理员可用）
     * @param questionQueryRequest
     * @return
     */
    Page<Question> listQuestionByPage(QuestionQueryRequest questionQueryRequest);
}
