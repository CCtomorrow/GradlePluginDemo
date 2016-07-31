package com.yong.plugin

import groovy.transform.CompileStatic
import org.gradle.api.Project
import org.gradle.api.tasks.Input

@CompileStatic
class PluginExtension {

    @Input
    String p

    @Input
    String p2

    @Input
    String p3


    public static PluginExtension getConfig(Project project) {
        PluginExtension config = project.getExtensions().findByType(PluginExtension.class);
        if (config == null) config = new PluginExtension();
        return config;
    }

}
