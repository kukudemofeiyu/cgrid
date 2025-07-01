package com.things.cgomp.system.convert;

import com.things.cgomp.system.domain.SysMenu;
import com.things.cgomp.system.domain.vo.MenuTreeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author things
 * @date 2025/3/5
 */
@Mapper
public interface MenuConvert {

    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    List<MenuTreeVO> convertList(List<SysMenu> list);

    MenuTreeVO convert(SysMenu bean);
}
