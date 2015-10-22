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

        <form class="form-horizontal" style="margin: 0 auto" id="dumpForm">
            <div class="control-group">
                <label class="control-label" for="database">Source database</label>
                <div class="controls">
                    <select id="database" name="sourceDatabase">
                    </select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="tableNames">
                    <p class="text-info">
                        Separated by
                        <blod><strong>","</strong></blod>
                        <br/>
                        dump database write
                        <blod><strong>"*"</strong></blod>
                    </p>
                </label>

                <div class="controls">
                    <textarea rows="8" class="field span6" id="tableNames" name="tableNames"
                              placeholder="table name"></textarea>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="destinationDatabase">destinationDatabase</label>
                <div class="controls">
                    <input type="text" name="destinationDatabase" id="destinationDatabase">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="destinationHost">destinationHost</label>
                <div class="controls">
                    <input type="text" name="destinationHost" id="destinationHost">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="destinationPort">destinationPort</label>
                <div class="controls">
                    <input type="text" name="destinationPort" id="destinationPort" value="3306">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="destinationDatabaseUser">destinationDatabaseUser</label>
                <div class="controls">
                    <input type="text" name="destinationDatabaseUser" id="destinationDatabaseUser">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="destinationDatabasePassword">destinationDatabasePassword</label>
                <div class="controls">
                    <input type="text" name="destinationDatabasePassword" id="destinationDatabasePassword">
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <button type="button" class="btn btn-primary" onclick="generateScript()">Generate Script</button>
                </div>
            </div>
        </form>

        <div>
            <pre class="pre-scrollable">
                <p id="scriptTag"></p>
            </pre>
        </div>
    </div>

</div>


<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="/js/jquery.js"></script>
<script src="/js/bootstrap.js"></script>

<script>
    $(document).ready(function () {
        $.ajax({
            type: "get",
            cache: false,
            url: "/executor/databases",
            error: function (request) {
                alert("can not get configed databases, please retry or contact system admin.")
            },
            success: function (data) {
                var databases = "";
                $.each(data, function (k, v) {
                    databases += "<option>" + v + "</option>"
                })
                $("#database").append(databases);
            }
        })
    });

    function generateScript() {
        $.ajax({
            type: "post",
            cache: false,
            url: "/dumper/generate",
            data: $("#dumpForm").serialize(),
            error: function (request) {
                alert("can not generate script, please retry or contact system admin.")
            },
            success: function (data) {
                console.log(data)
                if (data.ret) {
                    $("#scriptTag").html(data.data);
                }
            }
        })
    }
</script>

</body>
</html>
