package com.x.data.po;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
   * 评论回复
 * @author 老徐
 *
 */
@Data
@Document(collection="t_comment_reply")
public class CommentReply {

	@Id
    private String id;

    @DBRef
    private Long commentId;

    //评论者id
    private Long fromId;

    //评论者名字
    private String fromName;

    //评论者头像
    private String fromAvatar;

    //被评论者id
    private Long toId;

    //被评论者名字
    private String toName;

    //被评论者头像
    private String toAvatar;

    //评论内容
    private String content;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    private Integer deleted;
}
