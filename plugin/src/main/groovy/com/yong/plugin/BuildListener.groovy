package com.yong.plugin

import com.android.build.gradle.AppPlugin
import org.gradle.BuildResult
import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle

/**
 * build监听
 */
class BuildListener implements org.gradle.BuildListener {

    private Project project;
    private File apkDir;
    private File[] apkFiles;

    private final String RELEASE_DIR = "release_apk";

    public BuildListener(Project project) {
        this.project = project;
    }

    @Override
    void buildStarted(Gradle gradle) {}

    @Override
    void projectsEvaluated(Gradle gradle) {}

    @Override
    void projectsLoaded(Gradle gradle) {}

    @Override
    void settingsEvaluated(Settings settings) {}

    @Override
    void buildFinished(BuildResult result) {
        handleapk();
    }

    /**
     * 处理build完成之后的apk
     */
    void handleapk() {
        if (project.plugins.hasPlugin(AppPlugin)) {
            printf project.buildDir.absolutePath
            apkDir = new File("${project.buildDir}/outputs/apk");
            if (!apkDir.exists() || !apkDir.isDirectory()) return;

            apkFiles = apkDir.listFiles(new FilenameFilter() {
                @Override
                boolean accept(File dir, String name) {
                    if (name.endsWith("release")) {
                        return true;
                    }
                    return false;
                }
            });

            File apkDestDir = new File("${project.rootDir}");
            if (!apkDir.exists()) {
                apkDestDir.mkdirs();
            }

            for (File file : apkFiles) {
                file.renameTo(new File(apkDestDir, file.name));
            }

        }
    }

}