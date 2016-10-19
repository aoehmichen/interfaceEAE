package com.eae

class UtilitiesService {

    def retrieveZipFile(String scriptDir, String zipName, String remoteHost, String localDataStore, String UUID) {
        def sout = new StringBuilder();
        def serr = new StringBuilder();
        def executeCommande = scriptDir + "RetieveZipFile.sh " + zipName  + " " + remoteHost + " " + localDataStore + " " + UUID;
        def proc = executeCommande.execute();
        proc.consumeProcessOutput(sout, serr);
        proc.waitForOrKill(1000);

        if(!serr.toString().isEmpty()){
            log.error("ERROR retrieving zip file: " + zipName)
            log.error("RemoteHost: " + remoteHost + " local data store: " + localDataStore)
            log.error(serr.toString())
        }

        return sout.toString();
    }

    def writeConfigFile(String workingDirectory, String configFileName, String configs) {
        File f = new File(workingDirectory, configFileName);

        f.withWriterAppend('utf-8') { writer ->
            writer.writeLine configs
        }

        f.createNewFile()

        String confFile = workingDirectory + configFileName;
        return confFile
    }
}
