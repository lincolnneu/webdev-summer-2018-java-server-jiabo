(function () {
    var $usernameFld, $passwordFld;
    var $loginBtn;
    var userService = new UserServiceClient();
    $(main);

    function main() {
        $usernameFld = $("#usernameFld");
        $passwordFld = $("#passwordFld");
        $loginBtn = $("#loginBtn")
            .click(login);

    }
    function login() {
        userService
            .login($usernameFld.val(), $passwordFld.val())
            .then(function(response){
                if(response.status == 200){
                    success();
                } else{
                    fail();
                }
            });
    }


    function success(response){
        alert("Congratulations! You have successfully logged in! Navigating to profile...");
        location.href="../profile/profile.template.client.html";
    }

    function fail(){
        alert("Sorry, the username or the password may be incorrect.");
    }
})();
