<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Welcome to the eTRIKS Analytical Environment (eAE)</title>
        <style>
            * {
                box-sizing: border-box;
            }
            #page-body {
                text-align: center;
            }
        form {
            border: 3px solid #f1f1f1;
        }

        input[type=text], input[type=password] {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        button {
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 100%;
        }

        .cancelbtn {
            width: auto;
            padding: 10px 18px;
            background-color: #f44336;
        }

        .imgcontainer {
            display: inline-block;
            width: 300px;
            height: 300px;
            border-radius: 100%;
            background-color: #091e5b;
            background-image: url(${resource(dir: 'images', file: 'Nazca.JPG')});
            background-position: center;
            background-repeat: no-repeat;
            background-size: cover;
            margin: 20px 0;
        }

        .container {
            padding-left: 16px;
            padding-right: 16px;
        }

        </style>
	</head>
	<body>
        <r:script>
        var pageInfo = {
            basePath: "${request.getContextPath()}"
        }
        </r:script>
        <div id="page-body">
            %{--<div id="register-node">--}%
                %{--<h1>Register New Node</h1>--}%
                %{--<input--}%
                        %{--id="goToManagement"--}%
                        %{--class='txt flatbutton'--}%
                        %{--type="button"--}%
                        %{--value="Go to Management"--}%
                        %{--onclick="goToManagementPage()"/>--}%
            %{--</div>--}%
        <span class="imgcontainer"></span>

        <form action="action_page.php">
            <h2>Login</h2>
            <div class="container" style="color:black" align="left" >
                <label>Username</label>
                <input type="text" placeholder="Enter Username" name="uname" required>

                <label>Password</label>
                <input type="password" placeholder="Enter Password" name="psw" required>

                <button type="submit">Login</button>
                <input type="checkbox" checked="checked"> <span>Remember me</span>
            </div>
        </form>
        </div>
    <g:javascript library='jquery' />
    <g:javascript src='eAEInterface.js' />
	</body>
</html>
