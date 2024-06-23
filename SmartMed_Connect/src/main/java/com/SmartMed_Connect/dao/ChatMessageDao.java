package com.SmartMed_Connect.dao;


import com.SmartMed_Connect.entity.ChatMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


import java.util.List;

@Mapper
public interface ChatMessageDao extends BaseMapper<ChatMessage> {

    @Select("select * from chat_message where user_id=#{userId} order by id asc")
    List<ChatMessage> findChatMessageByUserId(Integer userId);


    @Delete("delete * from chat_message where user_id=#{userId}")
    void clearChatMessageByUserId(Integer userId);

}
