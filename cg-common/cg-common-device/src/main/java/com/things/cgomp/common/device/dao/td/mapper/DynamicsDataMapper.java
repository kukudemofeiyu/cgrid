package com.things.cgomp.common.device.dao.td.mapper;

import com.things.cgomp.common.device.dao.td.domain.SingleTsValue;
import com.things.cgomp.common.device.pojo.device.HistoryDataQueryReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author things
 */
@Mapper
public interface DynamicsDataMapper {

    List<SingleTsValue> selectHistoryDataByKey(HistoryDataQueryReq req);
}
