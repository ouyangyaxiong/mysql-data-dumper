
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
            <h1>Mysql Data Dumper</h1>
        </div>
        <form class="form-horizontal" style="margin: 0 auto">
            <div class="control-group">
                <label class="control-label" for="slaveIp">Slave Ip</label>
                <div class="controls">
                    <input type="text" id="slaveIp" placeholder="slave ip">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="slavePort">Slave Port</label>
                <div class="controls">
                    <input type="password" id="slavePort" placeholder="slave port">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="sql">Sql statement</label>
                <div class="controls">
                    <textarea rows="8" class="field span6" id="sql" placeholder="sql statement"></textarea>
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <button type="button" class="btn btn-primary">execute</button>
                    <button type="button" class="btn btn-primary">execute&download</button>
                </div>
            </div>
        </form>

        <div id="executeResult" class="alert alert-success">
            <%--alert-error--%>
            <p>asdas</p>
        </div>
        <table class="table table-bordered">
            <caption>Execute Result</caption>
            <thead>
            <tr>
                <th>...</th>
                <th>...</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>...</td>
                <td>...</td>
            </tr>
            </tbody>
        </table>
    </div>

</div>

<div id="footer">
    <div class="container">
        <p class="muted credit">writen by <a href="http://me.wenchao.ren/">Rollen Holt</a>.</p>
    </div>
</div>



<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="/js/jquery.js"></script>
<script src="/js/bootstrap.js"></script>

</body>
</html>
