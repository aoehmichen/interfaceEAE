
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
                $('<span />').attr("name",k).addClass("status").addClass(nodesStatusJSON[k]).text(k)
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