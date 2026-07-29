import org.gradle.api.Plugin
import org.gradle.api.Project

class SetupAppPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Configure common Android settings for the app module
        project.extensions.findByType(com.android.build.gradle.AppExtension::class.java)?.apply {
            compileSdk = 34
            defaultConfig {
                minSdk = 24
                targetSdk = 34
                versionCode = 1
                versionName = "1.0.0"
            }
        }
    }
}