/*
 * generated by Xtext 2.11.0.RC2
 */
package nl.cwi.reo


/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
class TreoStandaloneSetup extends TreoStandaloneSetupGenerated {

	def static void doSetup() {
		new TreoStandaloneSetup().createInjectorAndDoEMFRegistration()
	}
}
