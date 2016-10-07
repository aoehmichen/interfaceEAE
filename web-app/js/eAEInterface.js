
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
        console.log(clustersJSONArray)
        $.each(clustersJSONArray, function (i, e) {
          //  var nodes = clusterNodes(e.hosts);
            _t.append($('<tr/>').append(
                $('<td/>').text(e.name)
            ).append(
                $('<td/>').text(e.type)
            ).append( $('<td/>').text(e.hosts)))
        })
    })
}


// /**
//  * Retrieve nodes for the cluster and their status
//  */
// function clusterNodes(hosts){
//     var holder =  $('<td/>');
//     $.each(job.customfield.split(' '), function (i, e) {
//         holder.append(
//             $('<span />').addClass('eae_genetag').text(e)
//         )
//     });
//
//     return {
//         holder: holder,
//         name: job.customfield
//     };
// }