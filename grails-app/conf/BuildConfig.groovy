grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
grails.project.war.file = "target/${appName}.war"

def forkSettingsRun = [
        minMemory: 1536,
        maxMemory: 4096,
        maxPerm  : 384,
        debug    : false,
]
def forkSettingsOther = [
        minMemory: 256,
        maxMemory: 1024,
        maxPerm  : 384,
        debug    : false,
]

grails.project.fork = [
        test   : forkSettingsOther,
        run    : forkSettingsRun,
        war    : false,
        console: forkSettingsOther]


grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    // inherit Grails' default dependencies
    inherits("global") {}
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'


    repositories {
        mavenCentral()
        grailsCentral()

        mavenRepo "https://repo.transmartfoundation.org/content/repositories/public/"
        mavenRepo "https://repo.thehyve.nl/content/repositories/public/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.
        // runtime 'mysql:mysql-connector-java:5.1.27'
        // runtime 'org.postgresql:postgresql:9.3-1100-jdbc41'
        compile 'org.codehaus.groovy.modules.http-builder:http-builder:0.5.2', {
            excludes 'groovy', 'nekohtml', 'httpclient', 'httpcore'
        }
        test "org.grails:grails-datastore-test-support:1.0-grails-2.3"
    }

    plugins {
        // plugins for the build system only
        build ":tomcat:7.0.54"

        // plugins for the compile step
        compile ':rest-client-builder:2.1.1'
        compile ":quartz:1.0.2"
        compile ':hibernate:3.6.10.19'

        // plugins needed at runtime but not for compilation
        runtime ":jquery:1.11.1"
        runtime ":resources:1.2.8"
    }
}
