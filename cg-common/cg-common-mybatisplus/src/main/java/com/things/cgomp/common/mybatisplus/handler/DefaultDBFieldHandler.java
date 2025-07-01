package com.things.cgomp.common.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.things.cgomp.common.core.context.ISecurityContext;
import com.things.cgomp.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;
import java.util.Objects;

/**
 * 通用参数填充实现类
 *
 * 如果没有显式的对通用参数进行赋值，这里会对通用参数进行填充、赋值
 *
 * @author things
 */
@AllArgsConstructor
public class DefaultDBFieldHandler implements MetaObjectHandler {

    private ISecurityContext securityContext;

    @Override
    public void insertFill(MetaObject metaObject) {
        if (securityContext == null) {
            return;
        }
        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) metaObject.getOriginalObject();

            Date current = new Date();
            // 创建时间为空，则以当前时间为插入时间
            if (Objects.isNull(baseEntity.getCreateTime())) {
                baseEntity.setCreateTime(current);
            }
            Long userId = securityContext.getUserId();
            // 当前登录用户不为空，创建人为空，则当前登录用户为创建人
            if (Objects.nonNull(userId) && Objects.isNull(baseEntity.getCreateBy())) {
                baseEntity.setCreateBy(userId);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (securityContext == null) {
            return;
        }
        // 更新时间为空，则以当前时间为更新时间
        Object modifyTime = getFieldValByName("updateTime", metaObject);
        if (Objects.isNull(modifyTime)) {
            setFieldValByName("updateTime", new Date(), metaObject);
        }
        // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
        Object modifier = getFieldValByName("updateBy", metaObject);
        Long userId = securityContext.getUserId();
        if (Objects.nonNull(userId) && Objects.nonNull(modifier)) {
            setFieldValByName("updateBy", userId, metaObject);
        }
    }
}
