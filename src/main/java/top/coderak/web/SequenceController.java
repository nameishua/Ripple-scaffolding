package top.coderak.web;

import top.coderak.core.base.annotation.AnoEnum;
import top.coderak.core.base.annotation.InjectorProcessor;
import top.coderak.core.base.annotation.PrintOutAno;
import top.coderak.core.base.controller.BaseController;
import top.coderak.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.coderak.core.base.controller.BaseController;
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
    @PrintOutAno(Type = AnoEnum.SELECT)
    public void list() {

        System.out.println("开始list...");

        // 自定义注解测试
        InjectorProcessor processor = new InjectorProcessor();

        processor.process(SequenceController.this);

        String type = getParam("type");

        writeSuccessResult(sequenceService.list(type));
    }
}
