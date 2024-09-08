plugins {
    id("java")
    //这里是kotlin的版本
    id("org.jetbrains.intellij") version "1.9.0"
}
//插件的基本信息以及版本
group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
//    mavenCentral()
    //如果你gradle下载慢可以使用国内镜像
    maven { setUrl("https://maven.aliyun.com/repository/central/") }
    maven { setUrl("https://maven.aliyun.com/repository/central/") }
    maven { setUrl("https://maven.aliyun.com/repository/public/") }
    maven { setUrl("https://maven.aliyun.com/repository/google/") }
    maven { setUrl("https://maven.aliyun.com/repository/jcenter/") }
    maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2022.1.4")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
    //配置了编译的JDK版本
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }
    // 这里是很重要的配置，定义了gradle构建时依赖的idea版本，
    // 我们进行插件调试的时候，会使用这里定义的idea版本来进行测试的。
    patchPluginXml {
        sinceBuild.set("221")
        untilBuild.set("231.*")
    }

    //插件打包配置
    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
