package com.cgy.mianshiya.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cgy.mianshiya.common.ErrorCode;
import com.cgy.mianshiya.constant.CommonConstant;
import com.cgy.mianshiya.exception.ThrowUtils;
import com.cgy.mianshiya.model.dto.questionBankQuestion.QuestionBankQuestionQueryRequest;
import com.cgy.mianshiya.model.entity.Question;
import com.cgy.mianshiya.model.entity.QuestionBank;
import com.cgy.mianshiya.model.entity.QuestionBankQuestion;
import com.cgy.mianshiya.service.QuestionBankQuestionService;
import com.cgy.mianshiya.mapper.QuestionBankQuestionMapper;
import com.cgy.mianshiya.service.QuestionBankService;
import com.cgy.mianshiya.service.QuestionService;
import com.cgy.mianshiya.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author cgy
* @description 针对表【question_bank_question(题库题目)】的数据库操作Service实现
* @createDate 2024-11-05 17:22:43
*/
@Service
public class QuestionBankQuestionServiceImpl extends ServiceImpl<QuestionBankQuestionMapper, QuestionBankQuestion>
    implements QuestionBankQuestionService{
    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionBankService questionBankService;
    @Override
    public void validQuestionBankQuestion(QuestionBankQuestion questionBankQuestion, boolean b) {
        ThrowUtils.throwIf(questionBankQuestion == null, ErrorCode.PARAMS_ERROR);
        // 题目和题库必须存在
        Long questionId = questionBankQuestion.getQuestionId();
        if (questionId != null) {
            Question question = questionService.getById(questionId);
            ThrowUtils.throwIf(question == null, ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        Long questionBankId = questionBankQuestion.getQuestionBankId();
        if (questionBankId != null) {
            QuestionBank questionBank = questionBankService.getById(questionBankId);
            ThrowUtils.throwIf(questionBank == null, ErrorCode.NOT_FOUND_ERROR, "题库不存在");
        }
    }

    @Override
    public Wrapper<QuestionBankQuestion> getQueryWrapper(QuestionBankQuestionQueryRequest questionBankQuestionQueryRequest) {
        QueryWrapper<QuestionBankQuestion> queryWrapper = new QueryWrapper<>();
        if (questionBankQuestionQueryRequest == null) {
            return queryWrapper;
        }
        // todo 从对象中取值
        Long id = questionBankQuestionQueryRequest.getId();
        Long notId = questionBankQuestionQueryRequest.getNotId();
        String sortField = questionBankQuestionQueryRequest.getSortField();
        String sortOrder = questionBankQuestionQueryRequest.getSortOrder();
        Long questionBankId = questionBankQuestionQueryRequest.getQuestionBankId();
        Long questionId = questionBankQuestionQueryRequest.getQuestionId();
        Long userId = questionBankQuestionQueryRequest.getUserId();
        // todo 补充需要的查询条件
        // 精确查询
        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionBankId), "questionBankId", questionBankId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }
}




