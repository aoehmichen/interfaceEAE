<!DOCTYPE html>
<html>
<head>
    <title>eAE Management</title>
</head>

<body>
<div id="index">
    <h1 class="txt" style="text-align: center"> Welcome to eAE Nodes Management.</h1><br/>

    <div id="registeredNodesDiv">
        <table id="registerednodestable" class="nodesTable"></table>
        <div id="emptyCache">There are no nodes registered </div>
        <br>
    </div>

    <form method="post" action="">
        Register Node IP:<br>
        <textarea id="nodeIP" rows="1" name="Node IP" placeholder="X.X.X.X"></textarea>
        <br>
        <input id="registerNode"
               type="button"
               value="Register"
               onclick="registerNode()"/>
        <div id="registrationResult"></div>
    </form>


</div>

<script>

    function registerNode(){
        var IP = $('#nodeIP').val();
        console.log(IP)

    }

</script>
</body>
</html>