package com.cgy.mianshiya.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cgy.mianshiya.common.ErrorCode;
import com.cgy.mianshiya.constant.CommonConstant;
import com.cgy.mianshiya.exception.BusinessException;
import com.cgy.mianshiya.exception.ThrowUtils;
import com.cgy.mianshiya.model.dto.question.QuestionAddRequest;
import com.cgy.mianshiya.model.dto.question.QuestionQueryRequest;
import com.cgy.mianshiya.model.dto.question.QuestionUpdateRequest;
import com.cgy.mianshiya.model.entity.Question;
import com.cgy.mianshiya.model.entity.User;
import com.cgy.mianshiya.service.QuestionService;
import com.cgy.mianshiya.mapper.QuestionMapper;
import com.cgy.mianshiya.service.UserService;
import com.cgy.mianshiya.utils.SqlUtils;
import com.google.gson.Gson;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author cgy
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2024-11-05 17:22:43
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

    @Resource
    private UserService userService;
    /**
     * 校验参数
     * @param question
     * @param add
     */
    @Override
    public void validQuestion(Question question, boolean add) {
        ThrowUtils.throwIf(question == null, ErrorCode.PARAMS_ERROR);
        String title = question.getTitle();
        String content = question.getContent();
        if(add){
            ThrowUtils.throwIf(StringUtils.isBlank(title),ErrorCode.PARAMS_ERROR);
        }
        if(StringUtils.isNotBlank(title)){
            ThrowUtils.throwIf(title.length()>80,ErrorCode.PARAMS_ERROR,"标题过长");
        }
        if(StringUtils.isNotBlank(content)){
            ThrowUtils.throwIf(content.length()>10240,ErrorCode.PARAMS_ERROR,"内容过长");
        }
    }

    @Override
    public QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest) {
        ThrowUtils.throwIf(questionQueryRequest == null,ErrorCode.PARAMS_ERROR);
        System.out.println("111");
        String content = questionQueryRequest.getContent();
        Long id = questionQueryRequest.getId();
        List<String> tagList = questionQueryRequest.getTags();
        String searchText = questionQueryRequest.getSearchText();
        String answer = questionQueryRequest.getAnswer();
        String title = questionQueryRequest.getTitle();
        Long userId = questionQueryRequest.getUserId();
        String sortField = questionQueryRequest.getSortField();
        String sortOrder = questionQueryRequest.getSortOrder();
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(searchText)){
            queryWrapper.and(qw->qw.like("title",searchText).or().like("content",searchText));
        }
        queryWrapper.like(StringUtils.isNotBlank(content),"content",content);
        queryWrapper.like(StringUtils.isNotBlank(answer),"answer",answer);
        queryWrapper.like(StringUtils.isNotBlank(title),"title",title);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId),"userId",userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id),"id",id);
        //遍历tagList依次拼接like查询
        if(CollUtil.isNotEmpty(tagList)){
            for (String tag : tagList) {
                queryWrapper.like("tags","\"" + tag + "\"");
            }
        }
        //排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),sortOrder.equals(CommonConstant.SORT_ORDER_ASC),sortField);
        return queryWrapper;

    }

    /**
     * 添加题目
     * @param questionAddRequest
     * @param request
     * @return
     */
    @Override
    public Long AddQuestion(QuestionAddRequest questionAddRequest, HttpServletRequest request) {
        Question question = new Question();
        List<String> tagList = questionAddRequest.getTags();
        Gson gson = new Gson();
        String json = gson.toJson(tagList);
        BeanUtils.copyProperties(questionAddRequest,question);
        User loginUser = userService.getLoginUser(request);
        question.setUserId(loginUser.getId());
        question.setTags(json);
        validQuestion(question,true);
        boolean save = this.save(question);
        if(save){
            return question.getId();
        }else{
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"添加题目失败");
        }
    }

    /**
     * 更新题目
     * @param questionUpdateRequest
     * @param request
     * @return
     */
    @Override
    public Boolean updateQuestion(QuestionUpdateRequest questionUpdateRequest, HttpServletRequest request) {
        Question question = new Question();
        BeanUtils.copyProperties(questionUpdateRequest,question);
        List<String> tagList = questionUpdateRequest.getTags();
        if(tagList!=null){
            question.setTags(JSONUtil.toJsonStr(tagList));
        }
        validQuestion(question,false);
        //判断题目是否存在
        Long id = questionUpdateRequest.getId();
        Question oldQuestion = this.getById(id);
        ThrowUtils.throwIf(ObjectUtils.isEmpty(oldQuestion),ErrorCode.OPERATION_ERROR,"该题目不存在");
        return this.updateById(question);
    }

    /**
     * 分页获取题目列表，仅管理员可用
     * @param questionQueryRequest
     * @return
     */
    @Override
    public Page<Question> listQuestionByPage(QuestionQueryRequest questionQueryRequest) {
        Question question = new Question();
        BeanUtils.copyProperties(questionQueryRequest,question);
        QueryWrapper<Question> queryWrapper = getQueryWrapper(questionQueryRequest);
        Page<Question> page = this.page(new Page<>(questionQueryRequest.getCurrent(), questionQueryRequest.getPageSize()), queryWrapper);
        return page;
    }

}




