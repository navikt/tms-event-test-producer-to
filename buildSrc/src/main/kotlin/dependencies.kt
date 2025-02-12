import default.DependencyGroup

object TmsSoknadskvittering : DependencyGroup{
    override val groupId: String = "no.nav.tms.soknadskvittering"
    override val version: String = "0.1.0-alpha"
    val kotlinBuilder = dependency("kotlin-builder")
}