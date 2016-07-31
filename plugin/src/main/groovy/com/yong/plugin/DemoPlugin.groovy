package com.yong.plugin;

import org.gradle.api.Plugin
import org.gradle.api.Project

public class DemoPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.extensions.create('pluginExt', PluginExtension)
        project.pluginExt.extensions.create('pluginExtInner', PluginExtensionInner)
        project.task('customTask', type: CustomTask)
        project.gradle.addListener(new TimeListener());
    }

}