package com.cgy.mianshiya.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.cgy.mianshiya.model.dto.questionBankQuestion.QuestionBankQuestionQueryRequest;
import com.cgy.mianshiya.model.entity.QuestionBankQuestion;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author cgy
* @description 针对表【question_bank_question(题库题目)】的数据库操作Service
* @createDate 2024-11-05 17:22:43
*/
public interface QuestionBankQuestionService extends IService<QuestionBankQuestion> {

    void validQuestionBankQuestion(QuestionBankQuestion questionBankQuestion, boolean b);

    Wrapper<QuestionBankQuestion> getQueryWrapper(QuestionBankQuestionQueryRequest questionBankQuestionQueryRequest);
}
