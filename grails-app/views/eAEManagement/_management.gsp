<div id="clusters">
    <h1 class="txt" style="text-align: center"> Welcome to eAE Nodes Management.</h1><br/>

    <table id="clusters-table"/>
    <button type="button" onclick="goInsaneMode()" style="background-color: #90172d; width: 300px;">Insane Mode</button>

</div>

<script>
    createClustersTable();
    setInterval(function(){ updateClusterStatus(); }, 3000);

    function goInsaneMode() {
        var audio = new Audio('audio/On My Way.mp3');
        audio.play();
        setInterval(function(){ goInsane(); }, 100);
    }
</script>