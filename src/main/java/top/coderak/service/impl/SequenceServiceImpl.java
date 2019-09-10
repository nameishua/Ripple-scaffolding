package top.coderak.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.transaction.annotation.Transactional;
import top.coderak.core.base.constants.SequenceConstants;
import top.coderak.core.base.manager.impl.BaseCRUDManagerImpl;
import top.coderak.core.utils.GenerateSequenceUtil;
import top.coderak.entity.Sequence;
import top.coderak.mapper.SequenceMapper;
import top.coderak.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.coderak.core.base.constants.SequenceConstants;
import top.coderak.service.SequenceService;

import java.util.List;

@SuppressWarnings("ALL")
@Service
public class SequenceServiceImpl extends BaseCRUDManagerImpl<Sequence> implements SequenceService {

    @Autowired
    private SequenceMapper sequenceMapper;

    @Transactional
    public String getSequence(String type) {

        System.out.println(type);

        Sequence sequence = new Sequence();

        // 获取最大序号
        Integer suffix = sequenceMapper.selectTypeCount(type);

        // 设置id
        sequence.setId(GenerateSequenceUtil.generateSequenceNo());

        // 设置类型
        sequence.setType(type);

        // 设置序号
        sequence.setSequence(suffix + SequenceConstants.ADD_SEQUENCE);

        // 执行插入
        sequenceMapper.insert(sequence);

        // 计算需要填充的“0”的个数
        int count = SequenceConstants.LENGTH_SEQUENCE.intValue() - suffix.toString().length();

        // 返回的拼接字符串的补0前缀（如果需要补0）
        StringBuilder suffixZero = new StringBuilder(SequenceConstants.INIT_SEQUENCE);

        // 数值很小，所以可以用循环
        for (int i = 0; i < count; i++) {

            // 拼0
            suffixZero = suffixZero.append(SequenceConstants.SUPPLY_SEQUENCE);

        }

        return type + suffixZero.toString() + suffix.toString();
    }

    @Override
    public List<Sequence> list(String type) {

        Wrapper<Sequence> wrapper = new EntityWrapper<>();

        System.out.println(type);

        System.out.println(wrapper);

        wrapper.eq("type", type);

        return sequenceMapper.selectList(wrapper);
    }

}
