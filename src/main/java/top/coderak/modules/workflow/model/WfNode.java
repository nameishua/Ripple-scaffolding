package top.coderak.modules.workflow.model;

import lombok.Data;

@Data
public class WfNode {
    private String key;
    private String name;
    private String type;
    private String assignee;
}
