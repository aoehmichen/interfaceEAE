import org.slf4j.LoggerFactory

class BootStrap {

    final static logger = LoggerFactory.getLogger(this)

    def grailsApplication

    def init = { servletContext ->

        // Set the scripts to executable
        String path = servletContext.getRealPath("") + '/Scripts/';
        File scriptsFolder = new File(path);
        if(scriptsFolder.exists() && scriptsFolder.isDirectory()){
            File[] files = scriptsFolder.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    if(!file.canExecute()){
                        file.setExecutable(true)
                    }
                }
            }
        }
        // Set the openLava variables
        def openLavaEnv = grailsApplication.config.com.eae.openLavaEnv
        def executeCommande = ". " + openLavaEnv
        executeCommande.execute()

        logger.info("Bootstrapping Completed. OpenLava Environment Set and Scripts are executable")
    }
    def destroy = {
    }
}
