pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "TimePlannerApp"
include(":app")
include(":core:data")
include(":core:domain")
include(":core:presentation")
include(":core:utils")
include(":features:home:api")
include(":features:home:impl")
include(":features:editor:api")
include(":features:editor:impl")
include(":features:analytics:api")
include(":features:analytics:impl")
include(":features:settings:api")
include(":features:settings:impl")
