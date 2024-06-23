package com.SmartMed_Connect.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("chat_message")
public class ChatMessage {

    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String role;

    private String content;



}
