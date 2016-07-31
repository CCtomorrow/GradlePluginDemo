import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

public class CustomTask extends DefaultTask {

    @TaskAction
    void output() {
        println "param is ${project.pluginExt.p}"
        println "param2 is ${project.pluginExt.p2}"
        println "param3 is ${project.pluginExt.p3}"
        println "nestparam is ${project.pluginExt.pluginExtInner.pp}"
        println "nestparam2 is ${project.pluginExt.pluginExtInner.pp2}"
        println "nestparam3 is ${project.pluginExt.pluginExtInner.pp3}"
    }
}