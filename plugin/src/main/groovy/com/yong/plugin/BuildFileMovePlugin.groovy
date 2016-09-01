package com.yong.plugin;

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * build完成之后把apk文件拷贝到对应的位置的插件
 */
public class BuildFileMovePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.gradle.addListener(new BuildListener(project));
    }

}