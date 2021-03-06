
/**
 *   Renders the Node Management page form
 */
function goToManagementPage() {
    jQuery.ajax({
        url:  pageInfo.basePath + '/EAEManagement/goToManagementPage' ,
        type: "POST",
        timeout: '600000'
    }).done(function(serverAnswer) {
        document.location = "EAEManagement/goToManagementPage";
    }).fail(function() {
        alert("The requested management page is not available!");
    });
}


/**
 * User Authentication
 */
function authenticate(userName, password) {
    jQuery.ajax({
        url:  pageInfo.basePath + '/EAEManagement/authenticate' ,
        type: "POST",
        timeout: '600000',
        data: {username: userName, password: password }
    }).done(function(serverAnswer) {
        if(serverAnswer.toString() != "NOK"){
            $('#page-body').html(serverAnswer);
        }else{
            document.getElementById("authenticationFailed").className = "failure";
        }
    }).fail(function() {
        alert("Something went wrong in the authentication process.\n Please contact your system administrator.");
    });
}

/**
 * Display the available clusters and their status
 */
function createClustersTable(){
    var _t = $('#clusters-table');
    _t.empty();
    _t.append($('<tr/>').attr("id", "headersRow"));

    var cacheTableHeaders = ["Name", "Type", "Nodes"];
    var _h = $('#headersRow');
    $.each(cacheTableHeaders, function(i, e){
        _h.append($('<th/>').text(e))
    });

    jQuery.ajax({
        url: pageInfo.basePath + '/EAEManagement/retrieveClusters',
        type: "GET"
    }).done(function(clusters) {
        var clustersJSONArray = $.parseJSON(clusters);
        $.each(clustersJSONArray, function (i, e) {
            var logoCluster = associateImage(e.type);
            var nodes = clusterNodes(e.hosts);
            _t.append($('<tr/>').append(
                $('<td/>').addClass("b").append(e.name)
            ).append(
                $('<td/>').addClass("centerLogo").append(logoCluster)
            ).append( nodes))
        })
    })
}


/**
 * Retrieve nodes for the cluster and their status
 */
function clusterNodes(hosts){
    var holder =  $('<td/>');
    jQuery.ajax({
        url: pageInfo.basePath + '/EAEManagement/retrieveNodesStatus',
        type: "POST",
        data: {nodes : hosts}
    }).done(function(nodesStatus) {
        var nodesStatusJSON = $.parseJSON(nodesStatus),
        keys = Object.keys(nodesStatusJSON), i, len = keys.length;
        keys.sort();
        for (i = 0; i < len; i++) {
            k = keys[i];
            holder.append(
                $('<span />').attr("name",k).addClass("status").addClass(nodesStatusJSON[k])
                    // .hover(function() {
                    //     // code here
                    //     alert("hello");
                    //     $(this)
                    //
                    // // Mouseleave
                    // }, function() {
                    //     $(this).removeClass("active").removeClass("out");
                    // })
                    .text(k)
            )
        }
    });

    return holder;
}


/**
 * Add the associated image to the cluster type
 */
function associateImage(clusterType){
    var _img = $('<img/>').addClass("imgSize");
    _img.attr("src", "images/" + clusterType + ".png");
    return _img;
}

/**
 * Update the status of the cluster Nodes
 */
function updateClusterStatus(){
    jQuery.ajax({
        url: pageInfo.basePath + '/EAEManagement/retrieveNodesStatus',
        type: "POST",
        data: {nodes : "All"}
    }).done(function(nodesStatus) {
        var nodesStatusJSON = $.parseJSON(nodesStatus),
            keys = Object.keys(nodesStatusJSON), i, len = keys.length;
        keys.sort();
        for (i = 0; i < len; i++) {
            k = keys[i];
            var _hosts = document.getElementsByName(k);
            $.each(_hosts, function(i, e){
                e.className="";
                e.className="status " + nodesStatusJSON[k];
            })
        }
    });
}

/**
 *
 */
function getRunningJobs(){
    var _t = $('#jobs-table');
    _t.empty();
    _t.append($('<tr/>').attr("id", "jobsHeadersRow"));

    var jobsTableHeaders = ["Job Name", "Job ID", "Status", "Queue", "Execution Host", "Submit Time"];
    var _h = $('#jobsHeadersRow');
    $.each(jobsTableHeaders, function(i, e){
        _h.append($('<th/>').text(e))
    });

    jQuery.ajax({
        url: pageInfo.basePath + '/EAEManagement/retrieveRunningJobs',
        type: "GET"
    }).done(function(jobs) {
        if(jobs == "None"){
            jQuery("#jobs-table").hide();
            jQuery("#noUnfinishedJobs").show();
        }else {
            jQuery("#jobs-table").show();
            jQuery("#noUnfinishedJobs").hide();
            var jobsJSONArray = $.parseJSON(jobs);
            $.each(jobsJSONArray, function (i, e) {
                _t.append($('<tr/>').attr("id", e.id).append(
                    $('<td/>').attr("id", "name-" + e.id).addClass("b").append(e.name)
                ).append(
                    $('<td/>').attr("id", "id-" + e.id).text(e.id)
                ).append(
                    $('<td/>').attr("id", "status-" + e.id).text(e.status)
                ).append(
                    $('<td/>').attr("id", "queue-" + e.id).text(e.queue)
                ).append(
                    $('<td/>').attr("id", "executionHost-" + e.id).text(e.executionHost)
                ).append(
                    $('<td/>').attr("id", "submitTime-" + e.id).text(e.submitTime)
                ))
            })
        }
    });
}

/**
 *
 */
function updateJobStatus(){
    var _t = $('#jobs-table');

    jQuery.ajax({
        url: pageInfo.basePath + '/EAEManagement/retrieveRunningJobs',
        type: "GET"
    }).done(function(jobs) {
        var jobsTableLength = document.getElementById("jobs-table").rows.length;
        if(jobs == "None"){
            jQuery("#jobs-table").hide();
            jQuery("#noUnfinishedJobs").show();
            if(jobsTableLength > 1){
                for(var k = 1; k < jobsTableLength; k++){
                    document.getElementById("jobs-table").deleteRow(k);
                }
            }
        }else{
            jQuery("#jobs-table").show();
            jQuery("#noUnfinishedJobs").hide();
            var jobsJSONArray = $.parseJSON(jobs);
            var i,j = 0;
            var jobsJSONArrayLength = jobsJSONArray.length;
            var rowsToBeDeleted = [];
            for (i = 1; i < jobsTableLength; i++) {
                if(j<jobsJSONArrayLength){
                    var _rowHolder = document.getElementById("jobs-table").rows[i];
                    if(_rowHolder.id.toString() ==  jobsJSONArray[j].id.toString()){
                        var _rowId = _rowHolder.id.toString();
                        var statusCellName = "status-" + _rowId;
                        var executionHostCellName = "executionHost-" + _rowId;
                        $("#" + statusCellName).text(jobsJSONArray[j].status.toString());
                        $("#" + executionHostCellName).text(jobsJSONArray[j].executionHost.toString());
                        j++;
                    }else{
                        rowsToBeDeleted.push(i)
                    }
                }else{
                    rowsToBeDeleted.push(i)
                }
            }

            $.each(rowsToBeDeleted, function(k, el){
                document.getElementById("jobs-table").deleteRow(el);
            });

            for(j; j<jobsJSONArrayLength; j++){
                var e = jobsJSONArray[j];
                _t.append($('<tr/>').attr("id", e.id).append(
                    $('<td/>').attr("id", "name-" + e.id).addClass("b").append(e.name)
                ).append(
                    $('<td/>').attr("id", "id-" + e.id).text(e.id)
                ).append(
                    $('<td/>').attr("id", "status-" + e.id).text(e.status)
                ).append(
                    $('<td/>').attr("id", "queue-" + e.id).text(e.queue)
                ).append(
                    $('<td/>').attr("id", "executionHost-" + e.id).text(e.executionHost)
                ).append(
                    $('<td/>').attr("id", "submitTime-" + e.id).text(e.submitTime)
                ))
            }
        }}
    )
}

/**
 * Update the status of the cluster Nodes to look like a christmas tree
 */
function goInsane(){
    jQuery.ajax({
        url: pageInfo.basePath + '/EAEManagement/retrieveNodesStatus',
        type: "POST",
        data: {nodes : "All"}
    }).done(function(nodesStatus) {
        var nodesStatusJSON = $.parseJSON(nodesStatus),
            keys = Object.keys(nodesStatusJSON), i, len = keys.length;
        keys.sort();
        for (i = 0; i < len; i++) {
            k = keys[i];
            var _hosts = document.getElementsByName(k);
            $.each(_hosts, function(i, e){
                e.className="";
                var rdn = Math.floor((Math.random() * 4) + 1);
                switch (rdn){
                    case 1 :
                        e.className="status closed";
                        break;
                    case 2 :
                        e.className="status ok";
                        break;
                    case 3 :
                        e.className="status unavail";
                        break;
                    default :
                        e.className="status unreach";
                        break;
                }
            })
        }
    });
}