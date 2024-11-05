package com.cgy.mianshiya.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.cgy.mianshiya.model.dto.questionBank.QuestionBankQueryRequest;
import com.cgy.mianshiya.model.entity.QuestionBank;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author cgy
* @description 针对表【question_bank(题库)】的数据库操作Service
* @createDate 2024-11-05 17:22:43
*/
public interface QuestionBankService extends IService<QuestionBank> {

    void validQuestionBank(QuestionBank questionBank, boolean b);

    Wrapper<QuestionBank> getQueryWrapper(QuestionBankQueryRequest questionBankQueryRequest);
}
