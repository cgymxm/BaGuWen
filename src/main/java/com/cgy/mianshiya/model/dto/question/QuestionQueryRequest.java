package com.cgy.mianshiya.model.dto.question;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.cgy.mianshiya.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class QuestionQueryRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String searchText;

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
    /**
     * 题库ID
     */
    private long questionBankId;

    /**
     * 创建用户 id
     */
    private Long userId;

}
