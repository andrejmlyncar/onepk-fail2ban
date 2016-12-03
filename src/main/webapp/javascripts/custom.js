
function displayRouters() {
    $("#acl-tables").hide();
    $("#router-table").show();
    fillRoutersTable();
}

function displayAclTable() {
    $("#router-table").hide();
    displayAccessLists();
    $("#acl-tables").show();
}


function fillRoutersTable() {
    apiCallGet('./routers', null, function (data) {
        $("#router-table-content").html("");
        for (var i = 0; i < data.length; i++) {
            $("#router-table-content").append("<tr><td>" + data[i].router_id + "</td><td>" + data[i].router_name + "</td><td>" + data[i].ip_address + "</td></tr>");
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
        console.log(data);
        if (data === 'router registered') {
            if ($("#router-table").is(":visible")) {
               displayRouters();
            } else {
               displayAclTable();
            }
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

function displayAccessLists() {
    var aclTablesContent = $("#acl-tables");
    aclTablesContent.html("");
    aclTablesContent.append("<h2>Access Lists</h2>");
    apiCallGet('./accesslists', null, function (data) {
        for (var i = 0; i < data.length; i++) {
            aclTablesContent.append("<h4>Router ID: " + data[i].router_id + "</h4>");
            aclTablesContent.append("<table class=\"table table-striped\">" +
                    "<thead>" +
                    "<tr> " +
                    "<th>Sequence Number</th>" +
                    "<th>Permit</th>" +
                    "<th>Source</th>" +
                    "<th>Destination</th>" +
                    "</tr>" +
                    "</thead>" +
                    "<tbody id='body-" + data[i].router_id + "'>");

            for (var j = 0; j < data[i].aces.length; j++) {
                $("#body-" + data[i].router_id).append("<tr><td>" + data[i].aces[j].sequence + "</td><td>" + data[i].aces[j].permit + "</td><td>" + data[i].aces[j].source + "</td><td>" + data[i].aces[j].destination + "</td></tr>");
            }
            aclTablesContent.append("</tbody></table>");
        }
    });

}