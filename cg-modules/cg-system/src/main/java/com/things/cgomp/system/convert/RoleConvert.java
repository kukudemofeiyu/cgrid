package com.things.cgomp.system.convert;

import com.things.cgomp.system.api.domain.SysRole;
import com.things.cgomp.system.domain.vo.SysRoleSimpleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author things
 * @date 2025/3/5
 */
@Mapper
public interface RoleConvert {

    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);

    List<SysRoleSimpleVO> convertList(List<SysRole> list);

    SysRoleSimpleVO convert(SysRole bean);
}
