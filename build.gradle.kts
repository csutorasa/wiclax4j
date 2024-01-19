repositories {
    mavenCentral()
}

plugins {
    id("java-library")
    id("groovy")
    id("maven-publish")
    id("signing")
    id("jacoco")
    id("org.sonarqube").version("4.4.1.3373")
}

version = project.properties["wiclax4j.version"] as String

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
    withJavadocJar()
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    testImplementation("org.apache.groovy:groovy-all:4.0.18")
    testImplementation("org.spockframework:spock-core:2.3-groovy-4.0")
    testImplementation("net.bytebuddy:byte-buddy:1.14.11")
    testImplementation("org.objenesis:objenesis:3.3")
}

publishing {
    repositories {
        maven {
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = project.properties["nexus.username"] as String?
                password = project.properties["nexus.password"] as String?
            }
        }
    }
    publications {
        create("mavenJava", MavenPublication::class.java) {
            groupId = "com.github.csutorasa.wiclax4j"
            artifactId = "wiclax4j"
            version = project.version as String

            from(components["java"])

            pom {
                name.set("Wiclax4j")
                description.set("This is a pure java generic acquisition Wiclax server implementation")
                url.set("https://www.github.com/csutorasa/wiclax4j")
                licenses {
                    license {
                        name.set("The MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("csutorasa")
                        name.set("Ármin Csutorás")
                        email.set("csutorasa@gmail.com")
                        organizationUrl.set("https://github.com/csutorasa")
                    }
                }
                scm {
                    connection.set("https://github.com/csutorasa/wiclax4j.git")
                    developerConnection.set("https://github.com/csutorasa/wiclax4j.git")
                    url.set("https://github.com/csutorasa/wiclax4j")
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}

jacoco {
    toolVersion = "0.8.11"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
    }
}

tasks.sonarqube {
    dependsOn(tasks.jacocoTestReport)
}

sonarqube {
    properties {
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.organization", "csutorasa")
        property("sonar.branch.name", "master")
        property("sonar.login", System.getenv("SONAR_TOKEN"))
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.gradle.skipCompile", "true")
    }
}
