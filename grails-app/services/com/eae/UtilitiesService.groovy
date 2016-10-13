package com.eae

class UtilitiesService {

    def retrieveZipFile(String scriptDir, String zipName) {
        def sout = new StringBuilder();
        def serr = new StringBuilder();
        def executeCommande = scriptDir + "RetieveZipFile.sh " + zipName;
        def proc = executeCommande.execute();
        proc.consumeProcessOutput(sout, serr);
        proc.waitForOrKill(1000);

        if(serr.toString().contains("No file retrieved")){
            return serr.toString();
        }else {
            return sout.toString();
        }
    }
}
