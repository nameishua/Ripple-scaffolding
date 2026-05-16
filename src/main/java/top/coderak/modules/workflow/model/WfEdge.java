package top.coderak.modules.workflow.model;

import lombok.Data;

@Data
public class WfEdge {
    private String from;
    private String to;
    private String on;
}
