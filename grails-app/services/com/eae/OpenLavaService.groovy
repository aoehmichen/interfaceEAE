package com.eae

import net.sf.json.JSONObject
import org.codehaus.groovy.grails.web.json.JSONArray

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.util.regex.Pattern

class OpenLavaService {

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
//        def executeCommande = scriptDir + "Clusters.sh " + openLavaEnv
//        def proc = executeCommande.execute()
//        proc.consumeProcessOutput(sout, serr)
//        println("stout:" + sout)
//        println("serr:" + serr)

        String content = readFile("C:\\Users\\axelo\\Workspace\\interfaceEAE\\toto.txt", StandardCharsets.UTF_8);
        //return processQueues(sout.toString());
        return processQueues(content)
    }

    private def processQueues(String bqueues){
        String delimiters = "-{3,}";
        String[] clusters = bqueues.split(delimiters);
        JSONArray clustersJSON = new JSONArray();
        JSONObject cluster;
        for(int i=0; i<clusters.length; i++){
            cluster = processCluster(clusters[i].trim())
            clustersJSON.add(cluster)
        }
        return clustersJSON

    }

    private def processCluster(String openLavaClusterString){
        JSONObject cluster = new JSONObject();
        String[] elements = openLavaClusterString.split("\n");

        String clusterName = elements[0].split(":")[1].trim();
        String hosts = elements[15].split(":")[1].trim();
        String clusterType = inferClusterType(clusterName)

        cluster.put("name", clusterName)
        cluster.put("hosts", hosts)
        cluster.put("type", clusterType)
        
        return cluster;
    }

    private inferClusterType(String clusterName){
        //TODO: to be done
        return "GPU"
    }

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}
