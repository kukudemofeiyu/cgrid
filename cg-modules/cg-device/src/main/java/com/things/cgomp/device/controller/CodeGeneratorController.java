package com.things.cgomp.device.controller;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.device.dto.CodeGeneratorDTO;
import com.things.cgomp.device.service.CodeGeneratorService;
import com.things.cgomp.device.service.impl.CodeGeneratorServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/codeGenerator")
public class CodeGeneratorController {

    @PostMapping(value = "", name = "生成代码")
    public R<?> generate(@RequestBody CodeGeneratorDTO codeGeneratorDTO
    ) {
        CodeGeneratorService codeGeneratorService = new CodeGeneratorServiceImpl();
        codeGeneratorService.generate(codeGeneratorDTO);
        return R.ok();
    }

}
