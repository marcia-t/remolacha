package org.unq.compiler.remolacha.grammar;

import java.util.ArrayList;
import java.util.List;

public class Method {

    protected String id;
    protected List<String> parameters = new ArrayList<>();
    protected List<Expression> block = new ArrayList<>();

    public Method(String id, List<String> parameters, List<Expression> block) {
        this.id = id;
        this.parameters = parameters;
        this.block = block;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public List<Expression> getBlock() {
        return block;
    }

    public void setBlock(List<Expression> block) {
        this.block = block;
    }
}
