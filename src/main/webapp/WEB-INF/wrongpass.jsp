 <!DOCTYPE html>
<html>
    <head>
        <style>
            body {
                font-style: italic;
                width: 50%;
                margin: 0px auto;
            }
            #login_form {

            }

            #f1 {
                background-color: #FFF;
                border-style: solid;
                border-width: 1px;
                padding: 23px 1px 20px 114px;
            }
            .f1_label {
                white-space: nowrap;
            }
            span {color: white;}

            .new {
                background: black;
                text-align: center;
            }


        </style>


    </head>

    <body>
    	The username of password you entered was incorrect.
        <div id="login_form">
            <div class="new"><span>enter login details</span></div>  <!-- This is your header text-->
            <form name="f1" method="post" action="Login" id="f1">
                <table>
                    <tr>
                        <td class="f1_label">User Name :</td> <!-- This is your first Input Box Label-->
                        <td><input type="text" name="username" value="" /><!-- This is your first Input Box-->
                        </td>
                    </tr>
                    <tr>
                        <td class="f1_label">Password  :</td><!-- This is your Second Input Box Label-->
                        <td><input type="password" name="password" value=""  /><!-- This is your Second Input Box -->
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="submit" name="login" value="Log In" style="font-size:18px; " /><!-- This is your submit button -->
                        </td>
                    </tr>
                </table>
            </form> 
        </div>
                <a href="<%=request.getContextPath()%>/SignUp">No Account? Sign Up!</a>
        
    </body>
</html>