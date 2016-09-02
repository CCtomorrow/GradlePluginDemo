package com.yong.plugin

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin;
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * build完成之后把apk文件拷贝到对应的位置的插件
 */
public class BuildFileMovePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
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
        def targetTasks = project.tasks.findAll { task ->
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
        def targetTasks = project.tasks.findAll { task ->
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
        def targetTasks = project.tasks.findAll { task ->
            task.name.contains("Test")
        }
        //对满足条件的task，设置它为disable。如此这般，这个Task就不会被执行
        targetTasks.each {
            println "disable test task  :${it.name}"
            it.setEnabled false
        }
    }

}