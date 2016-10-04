
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
    console.log(userName, password)
    $('#authenticationFailed').className = "failure";
    // jQuery.ajax({
    //     url:  pageInfo.basePath + '/EAEManagement/autheticate' ,
    //     type: "POST",
    //     timeout: '600000'
    // }).done(function(serverAnswer) {
    //     // to be implemented
    // }).fail(function() {
    //     $('#authenticationFailed').className = "failure";
    // });
}


