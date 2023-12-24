pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Go Food Clone"
include(":app")
include(":login:http")
include(":login:domain")
include(":register:http")
include(":register:domain")
include(":preference:domain")
include(":preference:cache")
include(":common")
include(":register:presentation")
include(":login:presentation")
