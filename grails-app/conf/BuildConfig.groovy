grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.dependency.resolution = {
	// inherit Grails' default dependencies
	inherits("global") {
		// uncomment to disable ehcache
		// excludes 'ehcache'
	}

	log "warn"

	repositories {
		grailsPlugins()
		grailsHome()
		grailsCentral()

		mavenLocal()
		mavenCentral()
		
		// Is it possible to do better than this?!
		// Replace with the maven repository where aqapi.jar is uploaded and uncomment!
        // mavenRepo "http://artifactory:8081/artifactory/repo"
	}

	dependencies {
		runtime('commons-logging:commons-logging:1.1.1')
		runtime('org.apache.activemq:activemq-core:5.5.1')
		runtime('org.apache.activemq:activemq-camel:5.5.1')
		runtime('org.apache.activemq:activemq-pool:5.5.1')
		runtime('org.apache.camel:camel-jms:2.9.0') {
			 excludes 'spring-aop', 'spring-beans', 'spring-core', 'spring-expression', 'spring-asm', 'spring-tx', 'spring-context'
		}
        runtime('oracle:aqapi:1.0.0')
	}

	plugins {
		build(":release:1.0.1") { 
            excludes "svn"
			export = false 
		}
		compile(":routing:1.2.0")
	}
}
