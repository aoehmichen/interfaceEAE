class BootStrap {

    def init = { servletContext ->
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
    }
    def destroy = {
    }
}
