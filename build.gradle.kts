plugins {
    id("net.fabricmc.fabric-loom-remap")
}

version = "${property("mod.version")}-mc${sc.current.version}"
group = "${property("mod.group")}"
base.archivesName = property("mod.name") as String

val requiredJava = when {
    sc.current.parsed >= "1.20.5" -> JavaVersion.VERSION_21
    sc.current.parsed >= "1.18" -> JavaVersion.VERSION_17
    sc.current.parsed >= "1.17" -> JavaVersion.VERSION_16
    else -> JavaVersion.VERSION_1_8
}

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.

    // https://masa.dy.fi/maven/sakura-ryoko/fi/dy/masa/
    maven("https://masa.dy.fi/maven/sakura-ryoko") // sakura-ryoko's fork maven
    // https://masa.dy.fi/maven/fi/dy/masa/
    maven("https://masa.dy.fi/maven") // masa's maven

    maven("https://maven.terraformersmc.com/releases") //mod menu

    maven("https://maven.fallenbreath.me/releases") // conditional mixin

//    maven ("https://jitpack.io") // jitpack, ppl not used
}

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${sc.current.version}")
    mappings("net.fabricmc:yarn:${property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")

    // mod menu
    modCompileOnly("com.terraformersmc:modmenu:${property("mod_menu_version")}")
    // for dev testing
//    modImplementation "com.terraformersmc:modmenu:${project.mod_menu_version}"

    // jitpack
//    // malilib
//    modImplementation("com.github.sakura-ryoko:malilib:${project.minecraft_version}-${project.malilib_version}")
//    // litematica
//    modImplementation("com.github.sakura-ryoko:litematica:${project.minecraft_version}-${project.litematica_version}")
//    // minihud
//    modImplementation("com.github.sakura-ryoko:minihud:${project.minecraft_version}-${project.minihud_version}")
//    // tweakeroo for compact test
//    modRuntimeOnly("com.github.sakura-ryoko:tweakeroo:${project.minecraft_version}-${project.tweakeroo_version}")

    // sr's from masa's maven
    // malilib
    modImplementation("fi.dy.masa.malilib:malilib-fabric-${property("minecraft_version")}:${property("malilib_version")}")
    // litematica
    modImplementation("fi.dy.masa.litematica:litematica-fabric-${property("minecraft_version")}:${property("litematica_version")}")
    // minihud
    modImplementation("fi.dy.masa.minihud:minihud-fabric-${property("minecraft_version")}:${property("minihud_version")}")
    // tweakeroo for compact test
    modRuntimeOnly("fi.dy.masa.tweakeroo:tweakeroo-fabric-${property("minecraft_version")}:${property("tweakeroo_version")}")

}

val accesswidener = when {
    sc.eval(sc.current.version, "1.21") -> "1.21.aw"
    sc.eval(sc.current.version, ">=1.21.10") -> "1.21.10.aw"

    else -> "empty.aw"
}

loom {
//    fabricModJsonPath = rootProject.file("src/main/resources/fabric.mod.json")
    accessWidenerPath = rootProject.file("src/main/resources/accesswideners/$accesswidener")

    runConfigs.all {
        ideConfigGenerated(true)
        vmArgs("-Dmixin.debug.export=true") // Exports transformed classes for debugging
        runDir = "../../run" // Shares the run directory between versions
    }
}

tasks {
    processResources {
        inputs.property("id", project.property("mod.id"))
        inputs.property("name", project.property("mod.name"))
        inputs.property("version", project.property("mod.version"))
        inputs.property("minecraft", project.property("mod.mc_dep"))

        val props = mapOf(
            "id" to project.property("mod.id"),
            "name" to project.property("mod.name"),
            "version" to project.property("mod.version"),
            "minecraft" to project.property("mod.mc_dep"),
            "aw_file" to accesswidener, // accesswidener
        )
        filesMatching("fabric.mod.json") { expand(props) }

        val mixinJava = "JAVA_${requiredJava.majorVersion}"
        filesMatching("*.mixins.json") { expand("java" to mixinJava) }
    }

    // Builds the version into a shared folder in `build/libs/${mod version}/`
    register<Copy>("buildAndCollect") {
        group = "build"
        from(remapJar.map { it.archiveFile }, remapSourcesJar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
        dependsOn("build")
    }

    // LICENSE
    jar {
        from(rootProject.file("LICENSE")) {
            rename { fileName -> "${fileName}_${archiveBaseName}" }
        }
    }

}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21

    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
//    withSourcesJar()
}
