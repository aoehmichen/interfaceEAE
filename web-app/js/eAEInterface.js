
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
        if(serverAnswer.toString() === "OK"){
            $('#page-body').html("");
        }else{
            document.getElementById("authenticationFailed").className = "failure";
        }
    }).fail(function() {
        alert("Something went wrong in the authentication process.\n Please contact your system administrator.");
    });
}


