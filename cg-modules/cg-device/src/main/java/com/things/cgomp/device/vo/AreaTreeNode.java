package com.things.cgomp.device.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class AreaTreeNode {

    /**
     * 节点ID
     */
    private Long id;

    /**
     * 父节点ID：顶级节点为0
     */
    private Long parentId;

    /**
     * 节点名称
     */
    private String label;

    /**
     * 地理编码
     */
    private String areaCode;

    /**
     * 级别
     */
    private String level;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 子节点
     */
    private List<AreaTreeNode> children;

}
