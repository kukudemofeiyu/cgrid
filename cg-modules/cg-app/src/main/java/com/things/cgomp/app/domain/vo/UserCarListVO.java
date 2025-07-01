package com.things.cgomp.app.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserCarListVO {
    private Long userId;
    private List<UserCarInfoVO> carInfoList;
}
