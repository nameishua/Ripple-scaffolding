package top.coderak.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import top.coderak.entity.Sequence;
import org.apache.ibatis.annotations.Mapper;
import top.coderak.entity.Sequence;

/**
 * 功能描述
 *
 * @author zyh
 * @date 2019/7/21 0021
 */
@Mapper
public interface SequenceMapper extends BaseMapper<Sequence> {

    Integer selectTypeCount(String type);
}