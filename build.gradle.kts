import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    java
    `java-library`

    alias(libs.plugins.pluginyml)
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
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven { url = uri("https://repo.mikeprimm.com/") }
    maven { url = uri("https://maven.enginehub.org/repo/") }
    mavenLocal()
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

paper {
    name = "Plot2Dynmap"
    main = "com.plotsquared.plot2dynmap.Plot2DynmapPlugin"
    authors = listOf("Empire92", "NotMyFault", "dordsor21")
    apiVersion = "1.21.6"
    description = "This plugin adds a marker around claimed PlotSquared plots in the dynmap interface"
    version = rootProject.version.toString()
    website = "https://www.spigotmc.org/resources/1292/"

    serverDependencies {
        register("PlotSquared") {
            required = true
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
        }
        register("dynmap") {
            required = true
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
        }
    }
}
