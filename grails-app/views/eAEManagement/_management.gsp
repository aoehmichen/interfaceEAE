<div id="clusters">
    <h1 class="txt" style="text-align: center"> Welcome to eAE Nodes Management.</h1><br/>

    <h2 class="txt" style="text-align: left"><b>Clusters Status</b></h2>
    <table id="clusters-table"/>
    <br/>
    <h2 class="txt" style="text-align: left"><b>Jobs Status</b></h2>
    <div id="openLavaJobsDiv">
        <table id="jobs-table"/>
        <div id="noUnfinishedJobs" class="unfinishedJobs">No unfinished job found</div>
    </div>
    <br/>
    <button type="button" onclick="goInsaneMode()" style="background-color: #90172d; width: 300px;">Insane Mode</button>
</div>

<script>
    createClustersTable();
    setInterval(function(){ updateClusterStatus(); }, 1000);

    getRunningJobs();
    setInterval(function(){ updateJobStatus(); }, 3000);

    function goInsaneMode() {
        var audio = new Audio('audio/On My Way.mp3');
        audio.play();
        setInterval(function(){ goInsane(); }, 100);
    }
</script>
