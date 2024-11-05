package com.cgy.mianshiya.model.dto.questionBank;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionBankAddRequest implements Serializable {
    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 图片
     */
    private String picture;

    private static final long serialVersionUID = 1L;
}
