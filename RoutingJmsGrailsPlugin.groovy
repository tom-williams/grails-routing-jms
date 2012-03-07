class RoutingJmsGrailsPlugin {
	def version        = '1.2.1'
	def grailsVersion  = '2.0.1 > *'
	def author         = 'Matthias Hryniszak'
	def authorEmail    = 'padcom@gmail.com'
	def title          = 'JMS integration for the grails-routing plugin'
	def description    = 'Provides JMS integration for the grails-routing plugin'
	def documentation  = 'http://grails.org/plugin/routing-jms'

	def doWithSpring = {
		def config = application.config.grails.plugin.routing.jms
		
        // Transaction policy needed for transacting the Camel Routes - can be used in your XxxRoute.groovy class
        propagationRequired(org.apache.camel.spring.spi.SpringTransactionPolicy) {
            transactionManager = ref('transactionManager')
        }

        /*
         *  In your project that includes this plugin add the following in your Config.groovy file for each environment as appropriate:
         *  grails.plugin.routing.jms.aq.database.url = "jdbc:oracle:thin:@{database-host}:{port}:{database.instance}"
         *  grails.plugin.routing.jms.aq.database.username = "{username}"
         *  grails.plugin.routing.jms.aq.database.password = "{password}"
         *
         *  REMEMBER to include the appropriate Oracle driver jar either via a dependency or by placing them in your lib folder of the project.
         *      i.e.  ojdbc16-11.2.0.3.0.jar
         */
        QueueConnectionFactory connectionFactoryOracleAQQueue = AQjmsFactory.getQueueConnectionFactory(config.aq.database.url, null)

        aqQueueConnectionFactory(org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter) {
            targetConnectionFactory = connectionFactoryOracleAQQueue
            username = config.aq.database.username
            password= config.aq.database.password
        }

        oracleQueue(org.apache.camel.component.jms.JmsComponent) {
            connectionFactory = aqQueueConnectionFactory
            transacted = 'true'
        }

		activemq(org.apache.activemq.camel.component.ActiveMQComponent) {
			brokerURL = config.brokerURL ?: 'vm://LocalBroker'
		}
	}
}
