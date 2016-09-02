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

        // 只有传递到Jenkins的平台上面的project才需要进行下面的操作
        // 这里通过判断gradle.properties文件里面的一些属性来确定是否是
        // 在Jenkins上面打包的
        File proFile = new File(project.rootDir, "gradle.properties");

        // 没有的话
        if (!proFile.exists()) return;

        Properties properties = new Properties();
        properties.load(proFile.newDataInputStream());
        def name = properties.getProperty("MAVEN_USERNAME");
        def pass = properties.getProperty("MAVEN_PASSWORD");

        // 不是打包的不执行下面的操作
        if (name == null || name.trim().length() <= 0 || !name.equals("")
                || pass == null || pass.trim().length() <= 0 || !pass.equals("")
        ) return;

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