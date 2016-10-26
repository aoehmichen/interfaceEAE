package com.eae

import org.apache.commons.lang.ArrayUtils
import org.json.JSONObject
import org.json.JSONArray

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

class OpenLavaService {

    /**
     * List of supported languages/frameworks. NB: it is important to put GPU before Spark to detect the type properly.
     */
    private List<String> supportedLanguages = ["GPU", "Spark", "Python", "R"]

    /**
     *
     * @param computationType
     * @param clusterName
     * @param scriptDir
     * @param jobName
     * @param scriptsZipName
     * @param mainFileName
     * @param configFileName
     * @return
     */
    def openLavaBsub(String computationType, String clusterName,  String scriptDir, String jobName, String scriptsZipName, String mainFileName, String configFileName, String  remoteHost){
        def sout = new StringBuilder();
        def serr = new StringBuilder();

        def executeCommande = scriptDir + "Job"+ computationType + ".sh " + clusterName + " " + jobName + " " + scriptsZipName  + " " + mainFileName + " " + configFileName +  " " + remoteHost;
        println executeCommande
        def proc = executeCommande.execute();
        proc.consumeProcessOutput(sout, serr);
        proc.waitForOrKill(1000);
        println "out> $sout err> $serr"

        if(serr.toString() == ""){
            return 0
        } else {
            log.error("The openLava submit has failed for job $jobName. Error: $serr")
            return 1
        }
    }

    /**
     *
     * @param scriptDir
     * @param openLavaEnv
     * @return
     */
    def retrieveClusters(String scriptDir, String openLavaEnv){
        def sout = new StringBuilder();
        def serr = new StringBuilder();
        def executeCommande = scriptDir + "Clusters.sh " + openLavaEnv;
        def proc = executeCommande.execute();
        proc.consumeProcessOutput(sout, serr);
        proc.waitForOrKill(1000);

        //println("stout:" + sout)
        return processQueues(sout.toString());
    }

    /**
     *
     * @param bqueues
     * @return
     */
    private def processQueues(String bqueues){
        String delimiters = "-{3,}";
        String[] clusters = bqueues.split(delimiters);
        JSONArray clustersJSON = new JSONArray();
        JSONObject cluster;
        for(int i=0; i<clusters.length; i++){
            cluster = processCluster(clusters[i].trim());
            clustersJSON.put(cluster);
        }
        return clustersJSON
    }

    /**
     *
     * @param openLavaClusterString
     * @return
     */
    private def processCluster(String openLavaClusterString){
        JSONObject cluster = new JSONObject();
        String[] elements = openLavaClusterString.split("\n");

        String clusterName = elements[0].split(":")[1].trim();
        String hosts = inferHostList(elements[15].split(":")[1].trim());
        String clusterType = inferClusterType(clusterName);

        cluster.put("name", clusterName);
        cluster.put("type", clusterType);
        cluster.put("hosts", hosts);

        return cluster;
    }

    /**
     *
     * @param clusterName
     * @return
     */
    private String inferClusterType(String clusterName){
        for(Iterator<String> str = supportedLanguages.iterator(); str.hasNext(); ){
            String language = str.next().toString();
            String[] clusterNameArray = clusterName.split("_");
            for (int i=0; i<clusterNameArray.length; i++){
                Boolean match = clusterNameArray[i].trim().toLowerCase().equals(language.toLowerCase());
                if(match){
                    return language;
                }
            }
        }
        return "Unknown";
    }

    /**
     *
     * @param hosts
     * @return
     */
    private String inferHostList(String hosts){
        String[] hostsArray = hosts.split();
        if(hostsArray[0].equals("all")){
            return "All"
        }else{
            return hosts
        }
    }

    /**
     *
     * @param scriptDir
     * @param openLavaEnv
     * @param hosts
     * @return
     */
    def JSONObject retrieveNodesStatus(String scriptDir, String openLavaEnv, String hosts){
        def sout = new StringBuilder();
        def serr = new StringBuilder();
        def executeCommande = scriptDir + "Nodes.sh " + openLavaEnv;
        def proc = executeCommande.execute();
        proc.consumeProcessOutput(sout, serr);
        proc.waitForOrKill(1000);

        String[] hostsArray = hosts.split();
        return getHostsStatus(sout.toString(), hostsArray);
    }

    /**
     *
     * @param nodesStatus
     * @param hosts
     * @return
     */
    private def getHostsStatus(String nodesStatus, String[] hosts){
        JSONObject hostsStatus;
        HashMap nodesStatusMap = new HashMap();
        String[] elements = nodesStatus.split("\n");
        elements = ArrayUtils.remove(elements, 0);

        String hostName;
        String hostStatus;
        String[] hostStatusArray;
        for(int i=0; i<elements.length; i++) {
            hostStatusArray = elements[i].split();
            hostName = hostStatusArray[0].trim();
            hostStatus = hostStatusArray[1].trim();
            nodesStatusMap.put(hostName, hostStatus)
        }

        if(hosts[0].equals("All")){
            return new JSONObject(nodesStatusMap);
        }else{
            hostsStatus = new JSONObject()
            for(int i=0; i<hosts.length; i++){
                hostsStatus.put(hosts[i], nodesStatusMap[hosts[i]])
            }
            return hostsStatus;
        }
    }

    /**
     *
     * @param scriptDir
     * @param openLavaEnv
     * @return
     */
    def retrieveJobsStatus(String scriptDir, String openLavaEnv){
        def sout = new StringBuilder();
        def serr = new StringBuilder();
        def executeCommande = scriptDir + "RunningJobs.sh " + openLavaEnv;
        def proc = executeCommande.execute();
        proc.consumeProcessOutput(sout, serr);
        proc.waitForOrKill(1000);

        if(serr.toString().contains("No unfinished job found")){
            return "None";
        }else {
            return processJobs(sout.toString());
        }
    }

    /**
     *
     * @param jobs
     * @return
     */
    private def processJobs(String jobs){
        String delimiters = "\n";
        String[] jobsArray = jobs.split(delimiters);
        JSONArray jobsJSONArray = new JSONArray();
        JSONObject job;
        for (int i = 1; i < jobsArray.length; i++) {
            int start, end, j = 0;
            int[] fieldsLength = [8, 8, 6, 11, 12, 12, 11, 12];
            String[] fields = ["id", "user", "status", "queue", "fromHost", "executionHost", "name", "submitTime"];
            job = new JSONObject();
            while (j < fields.length) {
                end = start + fieldsLength[j] - 1;
                job.put(fields[j], jobsArray[i].substring(start, end).trim());
                start += fieldsLength[j];
                j++;
            }
            jobsJSONArray.put(job);
        }
        return jobsJSONArray
    }

    /**
     *
     * @param path
     * @param encoding
     * @return
     * @throws IOException
     */
    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}
