package com.things.cgomp.system.convert;

import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.enums.OrgTypeEnum;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.system.api.domain.SysOperator;
import com.things.cgomp.system.api.domain.SysOrg;
import com.things.cgomp.system.api.domain.SysUser;
import com.things.cgomp.system.domain.vo.SysOperatorChildVO;
import com.things.cgomp.system.domain.vo.SysOperatorSimpleVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author things
 * @date 2025/2/28
 */
@Mapper
public interface SysOperatorConvert {

    SysOperatorConvert INSTANCE = Mappers.getMapper(SysOperatorConvert.class);

    List<SysOperatorSimpleVO> convetList(List<SysOperator> list);

    @Mappings({
            @Mapping(source = "name", target = "operatorName"),
            @Mapping(source = "orgId", target = "operatorOrgId")
    })
    SysOperatorSimpleVO convertSimple(SysOperator bean);

    SysOperatorChildVO convert(SysOperator bean);

    SysUser convertUser(SysOperator bean);

   default SysOrg convertOrg(SysOperator bean){
       SysOrg org = new SysOrg();
       org.setParentId(SecurityConstants.ROOT_ORG_ID);
       org.setOrgName(bean.getName());
       org.setType(OrgTypeEnum.OPERATOR.getType());
       org.setCreateBy(SecurityUtils.getUserId());
       return org;
   }
}
