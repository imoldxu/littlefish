package com.x.data.po;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.x.data.pojo.enums.CommentTargetType;

import lombok.Data;

/**
   * 主评论
 * @author 老徐
 *
 */
@Data
@Document(collection="t_comment")
public class Comment {

	@Id
    private String id;

    //评论类型。1文章评论，2商品评论
    private CommentTargetType type;

    //被评论者的id
    private Integer targetId;

    //评论者id
    private Long fromId;

    //评论者名字
    private String fromName;

    //评论者头像
    private String fromAvatar;

    //获得点赞的数量
    private Integer likeNum;

    //评论内容
    private String content;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;
    
    private Integer deleted;

}
