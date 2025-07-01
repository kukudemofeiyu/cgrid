package com.things.cgomp.pay.util;

import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.pay.enums.ErrorCodeConstants;

import java.util.Comparator;
import java.util.Set;

public class RuleModelIdUtils {

    private static final Integer MODEL_ID_START = 1;

    private static final Integer MODEL_ID_LENGTH = 9999;

    public static Integer generateNormalModelId(
            Set<Integer> modelIds
    ) {
        if(modelIds.isEmpty()){
            return MODEL_ID_START;
        }

        Integer maxModelId = modelIds.stream()
                .max(Comparator.naturalOrder())
                .orElse(MODEL_ID_START);

        if(MODEL_ID_LENGTH.equals(maxModelId)){
            throw new ServiceException(ErrorCodeConstants.MODEL_ID_USER_UP);
        }

        return maxModelId+1;
    }
}
