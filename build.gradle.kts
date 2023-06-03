plugins {
    id("java")
}

group = "me.fan87"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    maven {
        url = uri("https://m2.dv8tion.net/releases")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }

    maven("https://jitpack.io")
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-beta.5")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.google.code.gson:gson:2.9.0")
//    implementation("org.jetbrains.pty4j:pty4j:0.12.5")
    implementation("org.reflections:reflections:0.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0-M1")
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    compileOnly("org.jetbrains:annotations:24.0.0")
}

tasks {
    register<JavaExec>("run") {
        doFirst {
            File("run/").mkdirs()
        }
        dependsOn("classes")
        mainClass.set("me.fan87.Main")
        workingDir("run/")
        classpath = files(*ArrayList<File>().also {
            it.addAll(project.extensions.getByType(JavaPluginExtension::class.java).sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).runtimeClasspath)
            it.add(File(project.buildDir, "classes"))
        }.toTypedArray())
    }
    compileJava {
        options.encoding = "UTF-8"
    }
}