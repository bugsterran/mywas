package com.was.app.http.rule;

import com.was.app.http.servlet.HttpRequest;

import java.util.List;
import java.util.function.BiFunction;

public enum Rule {
    EXTENSION_RULE("EXTENSION_RULE",(httpRequest, extensions) -> {
        return extensions
                .parallelStream()
                .anyMatch(extension -> httpRequest.getPath().endsWith(extension));

    }),
    PATH_RULE("PATH_RULE",(httpRequest, pattens) -> {
        return pattens
                .parallelStream()
                .anyMatch(pattern -> httpRequest.getPath().indexOf(pattern) > -1);
    });

    private String name;
    private BiFunction<HttpRequest, List<String>, Boolean> func;

    Rule(String name, BiFunction<HttpRequest, List<String>, Boolean> func) {
        this.name = name;
        this.func = func;
    }

    public boolean isViolate(HttpRequest httpRequest, List<String> pattenList) {
        return func.apply(httpRequest, pattenList);
    }

    public String getName(){
        return name;
    }

}
