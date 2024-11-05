package com.cgy.mianshiya.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuestionEditRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 推荐答案
     */
    private String answer;

}
