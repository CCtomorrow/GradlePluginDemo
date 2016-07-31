package com.yong.plugin

import groovy.transform.CompileStatic
import org.gradle.api.Project
import org.gradle.api.tasks.Input

@CompileStatic
class PluginExtensionInner {

    @Input
    String pp

    @Input
    String pp2

    @Input
    String pp3


    public static PluginExtensionInner getConfig(Project project) {
        PluginExtensionInner config = project.getExtensions().findByType(PluginExtensionInner.class);
        if (config == null) config = new PluginExtensionInner();
        return config;
    }

}
