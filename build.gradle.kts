plugins {
    java
    `java-library`

    alias(libs.plugins.pluginyml)
    alias(libs.plugins.minotaur)
}

the<JavaPluginExtension>().toolchain {
    languageVersion.set(JavaLanguageVersion.of(21))
}

configurations.all {
    attributes.attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 21)
}

tasks.compileJava.configure {
    options.release.set(21)
}

version = "7.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven { url = uri("https://repo.mikeprimm.com/") }
    maven { url = uri("https://maven.enginehub.org/repo/") }
}

dependencies {
    implementation(platform("com.intellectualsites.bom:bom-newest:1.47"))
    compileOnly("com.intellectualsites.plotsquared:plotsquared-core") {
        exclude(group = "worldedit-core")
    }
    compileOnly("io.papermc.paper:paper-api")
    compileOnly(libs.worldedit)
    compileOnly(libs.dynmapCore) { isTransitive = false }
    compileOnly(libs.dynmapApi) { isTransitive = false }
}

bukkit {
    name = "Plot2Dynmap"
    main = "com.plotsquared.plot2dynmap.Plot2DynmapPlugin"
    authors = listOf("Empire92", "NotMyFault", "dordsor21")
    apiVersion = "1.20"
    description = "This plugin adds a marker around claimed PlotSquared plots in the dynmap interface"
    version = rootProject.version.toString()
    depend = listOf("PlotSquared", "dynmap")
    website = "https://www.spigotmc.org/resources/1292/"
}

val supportedVersions = listOf("1.20", "1.20.1")

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN"))
    projectId.set("plot2dynmap")
    versionName.set("${project.version}")
    versionNumber.set("${project.version}")
    versionType.set("release")
    uploadFile.set(file("build/libs/${rootProject.name}-${project.version}.jar"))
    gameVersions.addAll(supportedVersions)
    loaders.addAll(listOf("paper", "purpur", "spigot"))
    syncBodyFrom.set(rootProject.file("README.md").readText())
    changelog.set("The changelog is available on GitHub: https://github" +
            ".com/IntellectualSites/Plot2Dynmap/releases/tag/${project.version}")
    dependencies {
        required.project("dynmap")
    }
}
