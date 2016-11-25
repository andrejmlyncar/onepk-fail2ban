
function displayRouters() {
    $("#acl-table").hide();
    $("#router-table").show();
    fillRoutersTable();
}

function displayAclTable() {
    $("#router-table").hide();
    $("#acl-table").show();
}


function fillRoutersTable() {
    apiCallGet('./routers', null, function (data) {
        $("#router-table-content").html("");
        for (var i = 0; i < data.length; i++) {
            $("#router-table-content").append("<tr><td>" + data[i].router_id + "</td><td>" + data[i].router_name + "</td><td>" + data[i].ip_address + "</td></tr>")
        }
    });
}

function addNewRouter() {
    var newRouter = new Object();
    newRouter.ip_address = $("#router-address").val();
    newRouter.username = $("#router-username").val();
    newRouter.password = $("#router-password").val();
    newRouter.name = $("#router-name").val();
    console.log(JSON.stringify(newRouter));
    apiCallPost('./routers', JSON.stringify(newRouter), function (data) {
        if (data.status === "success") {
            fillRoutersTable();
        }
    });
}
function fillAclTable() {

}
function apiCallGet(call_name, get_data, callback) {
    $.get(call_name, get_data, function (data) {
        if (callback !== null) {
            callback(data);
        }
    }, 'json');
}

function apiCallPost(call_name, post_data, callback) {
    $.post(call_name, post_data, function (data) {
        if (callback !== null) {
            callback(data);
        }
    }, 'json');
}

function routerShowModal() {
    $("#router-add-modal").modal('show');
}