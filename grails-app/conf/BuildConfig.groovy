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
        grailsCentral()
        mavenCentral()

        mavenRepo "https://repo.transmartfoundation.org/content/repositories/public/"
        mavenRepo "https://repo.thehyve.nl/content/repositories/public/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.
        // runtime 'mysql:mysql-connector-java:5.1.27'
        // runtime 'org.postgresql:postgresql:9.3-1100-jdbc41'
        test "org.grails:grails-datastore-test-support:1.0-grails-2.3"
    }

    plugins {
        // plugins for the build system only
        build ":tomcat:7.0.54"
        build ':release:3.1.1'

        // plugins for the compile step
        compile ":scaffolding:2.0.3"
        compile ':cache:1.1.7'
        compile ':rest-client-builder:2.1.1'
        compile ':quartz:1.0-RC2'
        compile ':hibernate:3.6.10.19'

        // plugins needed at runtime but not for compilation
        runtime ":database-migration:1.4.0"
        runtime ":jquery:1.11.1"
        runtime ":resources:1.2.8"
        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0.1"
        //runtime ":cached-resources:1.1"
        //runtime ":yui-minify-resources:0.1.5"

        // An alternative to the default resources plugin is the asset-pipeline plugin
        //compile ":asset-pipeline:1.6.1"

        // Uncomment these to enable additional asset-pipeline capabilities
        //compile ":sass-asset-pipeline:1.5.5"
        //compile ":less-asset-pipeline:1.5.3"
        //compile ":coffee-asset-pipeline:1.5.0"
        //compile ":handlebars-asset-pipeline:1.3.0.1"
    }
}
