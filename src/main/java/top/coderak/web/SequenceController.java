package top.coderak.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.coderak.core.base.annotation.PrintOperateType;
import top.coderak.core.base.controller.BaseController;
import top.coderak.core.base.enums.PrintOperateTypeEnum;
import top.coderak.service.SequenceService;

/**
 * 排序号
 *
 * @author zyh
 */
@RestController
@RequestMapping(value = "/sequence")
public class SequenceController extends BaseController {

    @Autowired
    private SequenceService sequenceService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @PrintOperateType(type = PrintOperateTypeEnum.SELECT)
    public void list() {

        String type = getParam("type");

        writeSuccessResult(sequenceService.list(type));
    }
}
