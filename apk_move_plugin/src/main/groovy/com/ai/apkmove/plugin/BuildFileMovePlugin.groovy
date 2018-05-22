package com.ai.apkmove.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * <b>Project:</b> GradlePluginDemo <br>
 * <b>Create Date:</b> 2018/5/22 <br>
 * <b>@author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b>  <br>
 */
class BuildFileMovePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def parms = project.getProperties()
        def name = parms.get("MAVEN_USERNAME")
        def pass = parms.get("MAVEN_PASSWORD")
        // 不是打包的不执行下面的操作
        if (name == null || name.trim().length() <= 0 || !name.equals("dabao")
                || pass == null || pass.trim().length() <= 0 || !pass.equals("linghit")
        ) return

        // app禁止debug的task，lib禁止release的task
        disableTestTask(project);
        if (project.plugins.hasPlugin(AppPlugin)) {
            disableDebugTask(project);
        } else if (project.plugins.hasPlugin(LibraryPlugin)) {
            disableReleaseTask(project);
        }

        project.gradle.addListener(new BuildListener(project));
    }

    static void disableReleaseTask(Project project) {
        //project.tasks包含了所有的tasks，下面的findAll是寻找那些名字中带debug的Task。
        //返回值保存到targetTasks容器中
        def targetTasks = project.tasks.findAll {
            task ->
                task.name.contains("Release")
        }
        //对满足条件的task，设置它为disable。如此这般，这个Task就不会被执行
        targetTasks.each {
            println "disable release task  :${it.name}"
            it.setEnabled false
        }
    }

    static void disableDebugTask(Project project) {
        //project.tasks包含了所有的tasks，下面的findAll是寻找那些名字中带debug的Task。
        //返回值保存到targetTasks容器中
        def targetTasks = project.tasks.findAll {
            task ->
                task.name.contains("Debug")
        }
        //对满足条件的task，设置它为disable。如此这般，这个Task就不会被执行
        targetTasks.each {
            println "disable debug task  :${it.name}"
            it.setEnabled false
        }
    }

    static void disableTestTask(Project project) {
        //project.tasks包含了所有的tasks，下面的findAll是寻找那些名字中带debug的Task。
        //返回值保存到targetTasks容器中
        def targetTasks = project.tasks.findAll {
            task ->
                task.name.contains("Test")
        }
        //对满足条件的task，设置它为disable。如此这般，这个Task就不会被执行
        targetTasks.each {
            println "disable test task  :${it.name}"
            it.setEnabled false
        }
    }
}
