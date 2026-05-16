package top.coderak.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.coderak.core.base.annotation.PrintOperateType;
import top.coderak.core.base.controller.BaseController;
import top.coderak.core.base.enums.PrintOperateTypeEnum;
import top.coderak.service.SequenceService;

@Api(tags = "序列号管理接口")
@RestController
@RequestMapping(value = "/sequence")
public class SequenceController extends BaseController {

    @Autowired
    private SequenceService sequenceService;

    @ApiOperation("查询序列号列表")
    @PrintOperateType(type = PrintOperateTypeEnum.SELECT)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void list() {
        String type = getParam("type");
        writeSuccessResult(sequenceService.list(type));
    }
}