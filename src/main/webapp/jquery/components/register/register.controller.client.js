(function () {
    var $usernameFld, $passwordFld, $verifyPasswordFld;
    var $registerBtn;
    var userService = new UserServiceClient();
    var hasTaken = false;
    var self = this;
    $(main);

    function main() {
        $usernameFld = $("#usernameFld");
        $passwordFld = $("#passwordFld");
        $verifyPasswordFld = $("#verifyPasswordFld");
        $registerBtn = $("#registerBtn")
            .click(register);

    }
    function register() {

        if($passwordFld.val() != $verifyPasswordFld.val()){
            alert("The password and verify password don't match");
        } else{
            var user = new User($usernameFld.val(),$passwordFld.val(),null,null,null,null,null,null);
            console.log(user);

            userService
                    .register(user)
                    .then(success,fail);
        }
    }


    function success(){
        alert("Congratulations! You have successfully registered!");
    }

    function fail(){
        alert("Sorry, the username already exists. Please try other username");
    }


})();
