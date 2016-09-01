package com.yong.plugin

import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.DefaultDomainObjectSet

public class DemoPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        DefaultDomainObjectSet<ApplicationVariant> variants = project.android.applicationVariants;

        project.extensions.create('pluginExt', PluginExtension)
        project.pluginExt.extensions.create('pluginExtInner', PluginExtensionInner)
        project.task('customTask', type: CustomTask)
        project.gradle.addListener(new TimeListener())
        project.afterEvaluate {
            println 'afterEvaluate'
            variants.all { variant ->

                variant.outputs.each { output ->
                    if (output.outputFile != null && output.outputFile.name.endsWith('.apk')
                            && 'release'.equals(variant.buildType.name)) {
                        println '这里先修改最终我们需要的apk的文件夹的名称，最后打包出来的apk文件就是这个名字'
                        def apkfilename = "V${variant.versionName}" + "${variant.buildType.name}_hu.apk";
                        def apkFile = new File(output.outputFile.getParent(), apkfilename)
                        output.outputFile = apkFile
                    }
                }

            }

        }

    }

}