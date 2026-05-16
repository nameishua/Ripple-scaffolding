package top.coderak.modules.workflow.model;

import lombok.Data;

import java.util.List;

@Data
public class WfDefinitionModel {
    private List<WfNode> nodes;
    private List<WfEdge> edges;
}
