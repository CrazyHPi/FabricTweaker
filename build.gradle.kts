plugins {
    id("net.fabricmc.fabric-loom-remap")
//    id("dev.kikugie.loom-back-compat")
}

version = "${property("mod.version")}-mc${sc.current.version}"
group = "${property("mod.group")}"
base.archivesName = property("mod.name") as String

val requiredJava = when {
    sc.current.parsed >= "26.1" -> JavaVersion.VERSION_25
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

    //https://github.com/RelativityMC/yarn
    maven("https://repo.codemc.io/repository/relativitymc/") // Modern Yarn

//    maven ("https://jitpack.io") // jitpack, ppl not used
}

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${sc.current.version}")
    if (sc.current.version >= "26.1") {
        mappings("org.relativitymc:modern-yarn:${property("yarn_mappings")}:v2")
    } else {
        mappings("net.fabricmc:yarn:${property("yarn_mappings")}:v2")
    }
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
    sc.eval(sc.current.version, "26.2") -> "26.2.aw"

    else -> "empty.aw"
}

loom {
    if (sc.current.parsed >= "26.1") {
        useIntermediateMappings = true
        intermediaryUrl =
            $$"https://repo.codemc.io/repository/relativitymc/org/relativitymc/intermediary/%1$s/intermediary-%1$s-v2.jar"
    }

//    fabricModJsonPath = rootProject.file("src/main/resources/fabric.mod.json")
    accessWidenerPath = rootProject.file("src/main/resources/accesswideners/$accesswidener")

    decompilerOptions.named("vineflower") {
        options.put("mark-corresponding-synthetics", "1") // Adds names to lambdas - useful for mixins
    }

    runConfigs.all {
        preferGradleTask = true
        generateRunConfig = true
        runDirectory = rootProject.file("run") // Shares the run directory between versions
        jvmArguments.add("-Dmixin.debug.export=true") // Exports transformed classes for debugging

        programArguments.add("--username=Crazy_H")
        programArguments.addAll("--width=1600", "--height=900")
    }
}

tasks {
    processResources {
        inputs.property("id", project.property("mod.id"))
        inputs.property("name", project.property("mod.name"))
        inputs.property("version", project.property("mod.version"))
        inputs.property("minecraft", project.property("mod.mc_dep"))

        val props = mapOf(
            "name" to project.property("mod.name"),
            "version" to project.property("mod.version"),
            "minecraft_version" to project.property("mod.mc_dep"),
            "aw_file" to accesswidener, // accesswidener
        )
        filesMatching("fabric.mod.json") { expand(props) }

        val mixinJava = "JAVA_${requiredJava.majorVersion}"
        filesMatching("*.mixins.json") { expand("java" to mixinJava) }
    }

    // Builds the version into a shared folder in `build/libs/${mod version}/`
    register<Copy>("buildAndCollect") {
        group = "build"

        // for loomx

//        inputs.property("version", project.property("mod.version"))
//        // loomx.mod(Sources)Jar returns the jar task for the applied loom variant
//        from(loomx.modJar.flatMap { it.archiveFile }, loomx.modSourcesJar.flatMap { it.archiveFile })
//        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))

        from(remapJar.map { it.archiveFile }, remapSourcesJar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
        dependsOn("build")
    }

    // LICENSE
    jar {
        val name = archiveBaseName

        from(rootProject.file("LICENSE")) {
            rename { fileName -> "${fileName}_${name}" }
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
