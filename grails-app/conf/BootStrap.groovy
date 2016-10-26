
class BootStrap {

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

        println("Bootstrapping Completed. Scripts are executable.")
    }
    def destroy = {
    }
}
