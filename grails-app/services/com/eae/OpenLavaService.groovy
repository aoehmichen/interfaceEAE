package com.eae

import org.json.JSONObject
import org.json.JSONArray

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

class OpenLavaService {

    /**
     * List of supported lamnguages/frameworks. NB: it is important to put GPU before Spark to detect the type properly.
     */
    private List<String> supportedLanguages = ["GPU", "Spark", "Python", "R"]

    def createConfigFile(String FileName, JSONArray jsonParams){
        File f = new File(FileName);
        int numberOfParameters = jsonParams.length()
        for(int i=0; i<numberOfParameters; i++) {
            String line = jsonParams.get(i)
            f.withWriterAppend('utf-8') { writer ->
                writer.writeLine line
            }
        }
        return 0
    }

    def openLavaBsub(String computationType, String scriptDir, String jobName, String scriptsZipName, String mainFileName, String configFileName){
        def sout = new StringBuilder()
        def serr = new StringBuilder()

        def executeCommande = scriptDir + "/" + "Job"+ computationType + ".sh " + jobName + " " + scriptsZipName + " " + mainFileName + " " + configFileName
        def proc = executeCommande.execute()
        proc.consumeProcessOutput(sout, serr)
        proc.waitForOrKill(1000)
        println "out> $sout err> $serr"

        return 0
    }

    def retrieveClusters(String scriptDir, String openLavaEnv){
        def sout = new StringBuilder()
        def serr = new StringBuilder()
        def executeCommande = scriptDir + "Clusters.sh " + openLavaEnv
        def proc = executeCommande.execute()
        proc.consumeProcessOutput(sout, serr)

        //String content = readFile("C:\\Users\\aoehmich\\Workspace\\interfaceEAE\\toto.txt", StandardCharsets.UTF_8);
        return processQueues(sout.toString());
        //return processQueues(content)
    }

    private def processQueues(String bqueues){
        String delimiters = "-{3,}";
        String[] clusters = bqueues.split(delimiters);
        JSONArray clustersJSON = new JSONArray();
        JSONObject cluster;
        for(int i=0; i<clusters.length; i++){
            cluster = processCluster(clusters[i].trim())
            clustersJSON.put(cluster)
        }
        return clustersJSON

    }

    private def processCluster(String openLavaClusterString){
        JSONObject cluster = new JSONObject();
        String[] elements = openLavaClusterString.split("\n");

        String clusterName = elements[0].split(":")[1].trim();
        String hosts = inferHostList(elements[15].split(":")[1].trim());
        String clusterType = inferClusterType(clusterName)

        cluster.put("name", clusterName)
        cluster.put("hosts", hosts)
        cluster.put("type", clusterType)
        
        return cluster;
    }

    private String inferClusterType(String clusterName){
        for(Iterator<String> str = supportedLanguages.iterator(); str.hasNext(); ){
            String language = str.next().toString();
            String[] clusterNameArray = clusterName.split("_")
            for (int i=0; i<clusterNameArray.length; i++){
                Boolean match = clusterNameArray[i].trim().toLowerCase().equals(language.toLowerCase());
                if(match){
                    return language;
                }
            }
        }
        return "Unknown";
    }

    private String inferHostList(String hosts){
        String[] hostsArray = hosts.split();
        if(hostsArray[0].equals("all")){
            return "All"
        }else{
            return hosts
        }
    }

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}
