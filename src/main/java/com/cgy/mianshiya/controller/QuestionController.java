package com.cgy.mianshiya.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cgy.mianshiya.annotation.AuthCheck;
import com.cgy.mianshiya.common.BaseResponse;
import com.cgy.mianshiya.common.DeleteRequest;
import com.cgy.mianshiya.common.ErrorCode;
import com.cgy.mianshiya.common.ResultUtils;
import com.cgy.mianshiya.constant.UserConstant;
import com.cgy.mianshiya.exception.BusinessException;
import com.cgy.mianshiya.exception.ThrowUtils;
import com.cgy.mianshiya.model.dto.question.QuestionAddRequest;
import com.cgy.mianshiya.model.dto.question.QuestionQueryRequest;
import com.cgy.mianshiya.model.dto.question.QuestionUpdateRequest;
import com.cgy.mianshiya.model.entity.Question;
import com.cgy.mianshiya.model.entity.User;
import com.cgy.mianshiya.model.enums.UserRoleEnum;
import com.cgy.mianshiya.service.QuestionService;
import com.cgy.mianshiya.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/question")
@Slf4j
public class QuestionController {
    @Resource
    private QuestionService questionService;

    @Resource
    private UserService userService;

    /**
     * 添加题目
     * @param questionAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addQuestion(@RequestBody QuestionAddRequest questionAddRequest, HttpServletRequest request){
        if(questionAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long questionId = questionService.AddQuestion(questionAddRequest, request);
        return ResultUtils.success(questionId);
    }

    /**
     * 删除题目
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteQuestion(@RequestBody DeleteRequest deleteRequest,HttpServletRequest request){
        if(deleteRequest == null || deleteRequest.getId()<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        Long id = deleteRequest.getId();
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(ObjectUtils.isEmpty(oldQuestion),ErrorCode.OPERATION_ERROR,"该题目不存在");
        if(!oldQuestion.getUserId().equals(loginUser.getId()) && userService.isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = questionService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新题目
     * @param questionUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateQuestion(@RequestBody QuestionUpdateRequest questionUpdateRequest, HttpServletRequest request){
        if(questionUpdateRequest == null || questionUpdateRequest.getId()<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean result = questionService.updateQuestion(questionUpdateRequest, request);
        return ResultUtils.success(result);
    }
    /**
     * 分页获取题目列表
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Question>> listQuestionByPage(@RequestBody QuestionQueryRequest questionQueryRequest){
        ThrowUtils.throwIf(questionQueryRequest == null,ErrorCode.PARAMS_ERROR);
        Page<Question> questionPage = questionService.listQuestionByPage(questionQueryRequest);
        return ResultUtils.success(questionPage);
    }


}
