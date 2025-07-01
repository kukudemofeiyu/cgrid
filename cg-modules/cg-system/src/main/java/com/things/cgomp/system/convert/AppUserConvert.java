package com.things.cgomp.system.convert;

import com.things.cgomp.app.api.domain.AppUser;
import com.things.cgomp.system.domain.vo.AppUserSimpleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author things
 * @date 2025/3/7
 */
@Mapper
public interface AppUserConvert {

    AppUserConvert INSTANCE = Mappers.getMapper(AppUserConvert.class);

    List<AppUserSimpleVO> convertList(List<AppUser> list);

    AppUserSimpleVO convertSimple(AppUser bean);
}
