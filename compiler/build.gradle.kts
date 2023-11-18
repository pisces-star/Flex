plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies{
    implementation("org.dom4j:dom4j:2.1.1")
    implementation("com.google.code.gson:gson:2.9.1")
    implementation("commons-cli:commons-cli:1.4")
}