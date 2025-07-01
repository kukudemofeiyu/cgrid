package com.things.cgomp.device.api.dto;

import com.things.cgomp.common.core.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


@Getter
@Setter
@Accessors(chain = true)
public class RuleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 模型编号（1-9999）
     */
    private Integer modelId;

    /**
     * 规则名称
     */
    private String name;

    private List<RuleFeeDTO> fees;

    private List<RuleTimeDTO> times;

    private LocalDateTime updateTime;

    private Long operatorId;

    public Map<Integer, List<RuleTimeDTO>> buildTimesMap() {
        if (times == null) {
            return new HashMap<>();
        }

        Map<Integer, List<RuleTimeDTO>> timesMap = new HashMap<>();
        for (RuleTimeDTO time : times) {
            List<RuleTimeDTO> subTimes = timesMap.computeIfAbsent(time.getType(), k -> new ArrayList<>());
            subTimes.add(time);
        }

        return timesMap;
    }

    private Map<Integer, RuleFeeDTO> buildFeeMap() {
        if (fees == null) {
            return new HashMap<>();
        }

        return fees.stream()
                .collect(Collectors.toMap(
                        RuleFeeDTO::getType,
                        a -> a,
                        (a, b) -> a
                ));
    }

    private List<RuleTimeDTO> splitHalfHour() {
        if(times == null){
            return new ArrayList<>();
        }

        return times.stream()
                .map(this::splitHalfHour)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<RuleTimeDTO> splitHalfHour(
            RuleTimeDTO time
    ) {
        LocalTime startTime = time.buildStartTime();
        LocalTime endTime = time.buildEndTime();
        if(
                !DateUtils.isFullOrHalfHour(startTime)
                        ||  !DateUtils.isFullOrHalfHour(endTime)
                        || startTime.equals(endTime)
        ){
            return new ArrayList<>();
        }

        LocalTime currentStartTime = startTime;
        List<RuleTimeDTO> subTimes = new ArrayList<>();
        do {
            LocalTime currentEndTime = currentStartTime.plusMinutes(30);
            RuleTimeDTO subTime = new RuleTimeDTO()
                    .setRuleId(time.getRuleId())
                    .setStartTime(currentEndTime.toString())
                    .setEndTime(currentEndTime.toString())
                    .setType(time.getType());
            subTimes.add(subTime);

            currentStartTime =  currentEndTime;
        }while (!currentStartTime.equals(endTime));

        return subTimes;
    }

    public Map<LocalTime, RuleFeeDTO> buildHalfHourFeeMap() {
        Map<Integer, RuleFeeDTO> feeMap = buildFeeMap();
        List<RuleTimeDTO> times = splitHalfHour();

        Map<LocalTime, RuleFeeDTO> halfHourFeeMap = new HashMap<>();
        for (RuleTimeDTO time : times) {
            RuleFeeDTO fee = feeMap.get(time.getType());
            halfHourFeeMap.put(time.buildStartTime(), fee);
        }

        return halfHourFeeMap;
    }

}
