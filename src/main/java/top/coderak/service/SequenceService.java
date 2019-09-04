package top.coderak.service;

import top.coderak.core.base.manager.BaseCRUDManager;
import top.coderak.entity.Sequence;
import top.coderak.core.base.manager.BaseCRUDManager;

import java.util.List;

public interface SequenceService extends BaseCRUDManager<Sequence> {

    String getSequence(String type);

    List<Sequence> list(String type);
}
