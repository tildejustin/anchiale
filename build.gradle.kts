plugins {
    alias(libs.plugins.fabric.loom)
    alias(libs.plugins.ploceus)
    alias(libs.plugins.maven.publish)
}

version = "${property("mod_version")}+${property("target_version")}"
group = property("maven_group") as String

base {
    archivesName = property("archives_name") as String
}

repositories {
    maven("https://jitpack.io")
}

loom {
    decompilers {
        named("vineflower") {
            options.putAll(mapOf("mark-corresponding-synthetics" to "1", "indent-string" to "    "))
        }
    }
}

ploceus {
    setIntermediaryGeneration(2)
}

dependencies {
    minecraft(libs.minecraft)
    mappings(ploceus.featherMappings(libs.versions.feather.build.get()))
    modImplementation(libs.fabric.loader)
}

tasks.named<JavaCompile>("compileJava") {
    options.encoding = "UTF-8"
    options.release = 8
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
}

tasks.jar {
    from(rootDir.resolve("licenses")) {
        into("META-INF/LICENSES")
    }
}

tasks.processResources {
    val fmjProperties = mapOf("version" to version)
    inputs.properties(fmjProperties)
    filesMatching("fabric.mod.json") {
        expand(fmjProperties)
    }
}

publishing {
    publications.create<MavenPublication>("mavenJava") {
        from(components["java"])
    }
    repositories {}
}
