package com.things.cgomp.common.mq.record.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.common.mq.record.domain.MqProducerFailRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author things
 */
@Mapper
public interface MqProducerFailRecordMapper extends BaseMapper<MqProducerFailRecord> {

    default List<MqProducerFailRecord> selectListLimit(Integer limit){
        LambdaQueryWrapper<MqProducerFailRecord> wrapper = new LambdaQueryWrapper<MqProducerFailRecord>()
                .orderByAsc(MqProducerFailRecord::getCreateTime);
        if (limit != null) {
            wrapper.last("limit " + limit);
        }
        return selectList(wrapper);
    }

    void updateRecords(@Param("ids") List<Long> ids);
}
