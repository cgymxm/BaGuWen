package com.cgy.mianshiya.mapper;

import com.cgy.mianshiya.model.entity.QuestionBankQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author cgy
* @description 针对表【question_bank_question(题库题目)】的数据库操作Mapper
* @createDate 2024-11-05 17:22:43
* @Entity com.cgy.mianshiya.model.entity.QuestionBankQuestion
*/
@Mapper
public interface QuestionBankQuestionMapper extends BaseMapper<QuestionBankQuestion> {

}




