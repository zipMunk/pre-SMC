package com.SmartMed_Connect.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import com.SmartMed_Connect.entity.User;

// @Repository 注解声明为一个仓库类，用于操作 user 实体对象的持久化操作。
@Repository
public interface UserDao extends BaseMapper<User> {
    //UserDao 接口直接使用 MyBatis-Plus 提供的一系列方法来操作 user 实体对应的数据表，如插入、更新、删除和查询等操作。
    //@Repository：标注为 Spring 的仓库类，表示这个接口是用来进行数据访问的组件，通常与数据访问层（DAO 层）的类一起使用。
    //Spring 会自动扫描并将其注册为 Spring 容器中的 Bean，可以直接在服务类中注入使用。
    //BaseMapper<User>：是 MyBatis-Plus 框架提供的通用 Mapper 接口，通过泛型 <user> 指定了要操作的实体类。
    //它提供了许多基本的数据库操作方法，如 insert、update、delete、select 等，避免了手动编写大量的 CRUD SQL。

}
