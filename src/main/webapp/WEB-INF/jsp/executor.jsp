<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Mysql &middot; Dumper</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- CSS -->
    <link href="/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">

        /* Sticky footer styles
        -------------------------------------------------- */

        html,
        body {
            height: 100%;
            /* The html and body elements cannot have any padding or margin. */
        }

        /* Wrapper for page content to push down footer */
        #wrap {
            min-height: 100%;
            height: auto !important;
            height: 100%;
            /* Negative indent footer by it's height */
            margin: 0 auto -60px;
        }

        /* Set the fixed height of the footer here */
        #push,
        #footer {
            height: 60px;
        }

        #footer {
            background-color: #f5f5f5;
        }

        /* Lastly, apply responsive CSS fixes as necessary */
        @media (max-width: 767px) {
            #footer {
                margin-left: -20px;
                margin-right: -20px;
                padding-left: 20px;
                padding-right: 20px;
            }
        }

        /* Custom page CSS
        -------------------------------------------------- */
        /* Not required for template or sticky footer method. */

        .container {
            width: auto;
            max-width: 680px;
        }

        .container .credit {
            margin: 20px 0;
        }


    </style>
    <link href="/css/bootstrap-responsive.css" rel="stylesheet">
    <link rel="shortcut icon" href="../assets/ico/favicon.png">

</head>

<body>


<!-- Part 1: Wrap all page content here -->
<div id="wrap">

    <!-- Begin page content -->
    <div class="container">
        <div class="page-header">
            <h3>
                <a href="/executor/render">Query&Download</a>
                <a href="/dumper/render">DB&Table Dump</a>
            </h3>

        </div>

        <form class="form-horizontal" style="margin: 0 auto" id="dbForm" method="post", action="/executor/downloadCsv">
            <div class="control-group">
                <label class="control-label" for="database">database</label>

                <div class="controls">
                    <select id="database" name="database">
                    </select>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="sql">Sql statement</label>

                <div class="controls">
                    <textarea rows="8" class="field span6" id="sql" name="sqlStatement"
                              placeholder="sql statement"></textarea>
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <button type="button" class="btn btn-primary" onclick="checkAndDo(executeQuery)">Query</button>
                    <button type="button" class="btn btn-primary" onclick="checkAndDo(downloadData)">Download</button>
                </div>
            </div>
        </form>

        <div id="executeResult">
            <p id="executeFlag"></p>
        </div>
        <table class="table table-bordered">
            <caption>Execute Result</caption>
            <thead id="dataHead">
                <tr></tr>
            </thead>
            <tbody id="dataBody">
            </tbody>
        </table>
    </div>

</div>


<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="/js/jquery.js"></script>
<script src="/js/bootstrap.js"></script>

<script>
    $(document).ready(function() {
        $.ajax({
            type: "get",
            cache: false,
            url: "/executor/databases",
            error:function(request) {
                alert("can not get configed databases, please retry or contact system admin.")
            },
            success: function(data) {
                var databases = "";
                $.each(data, function(k, v){
                    databases += "<option>" + v + "</option>"
                })
                $("#database").append(databases);
            }
        })
    });

    function checkAndDo(callback){
        var sql = $("#sql").val();
        if(!sql){
            $("#executeResult").removeClass("alert alert-success").addClass("alert alert-error");
            $("#executeFlag").html("sql statement can not be null");
            return;
        }
        $.ajax({
            type: "post",
            cache: false,
            url: "/sql/checker",
            data: sql,
            error: function(){
                $("#executeResult").removeClass("alert alert-success").addClass("alert alert-error");
                $("#executeFlag").html("call sql check service error, please retry or contact system admin.")
            },
            success: function(data) {
                if(data.ret){
                    $("#executeResult").removeClass("alert alert-error").addClass("alert alert-success");
                    callback();
                } else {
                    $("#executeResult").removeClass("alert alert-success").addClass("alert alert-error");
                    $("#executeFlag").html(data.message)
                }
            }
        });
    }

    function executeQuery() {
        $.ajax({
            type: "post",
            cache: false,
            url: "/executor/query",
            data: $("#dbForm").serialize(),
            error: function (request) {
                $("#executeResult").removeClass("alert alert-success").addClass("alert alert-error");
                $("#executeFlag").html("form submit error, please retry or contact system admin.")
            },
            success: function (data) {
                if(data.ret){
                    $("#executeResult").removeClass("alert alert-error").addClass("alert alert-success");
                    $("#executeFlag").html("sql execute success.")
                    renderTable(data);
                } else {
                    $("#executeResult").removeClass("alert alert-success").addClass("alert alert-error");
                    $("#executeFlag").html(data.message)
                }
            }
        })
    }

    function downloadData (){
        $("#dbForm").submit();
    }


    function renderTable(data){
        if(data.data.length == 0){
            $("caption").html("Execute Result: <strong>count(*) = 0</strong>")
            return;
        }
        var titles=new Array()
        var firstElement = data.data[0]
        $("caption").html("Execute Result: <strong>count(*) = " + data.data.length + "</strong>")
        $.each(firstElement, function(k, v) {
            titles.push(k);
        })
        $.each(titles, function(k, v){
            $("#dataHead tr").append(
                    "<th>" + v + "</th>"
            );
        })
        $.each(data.data, function(key, value) {
            $("#dataBody").append("<tr>")
            $.each(value, function(k, v) {
                $("#dataBody").append("<td>" + v + "</td>");
            })
            $("#dataBody").append("</tr>");
        })
    }

    function csvDownload(){
        $.ajax({
            type: "post",
            cache: false,
            url: "/executor/downloadCsv",
            data: $("#dbForm").serialize(),
            error: function (request) {
                alert("csv download fild.")
            },
            success: function (data) {
            }
        })
    }
</script>

</body>
</html>
