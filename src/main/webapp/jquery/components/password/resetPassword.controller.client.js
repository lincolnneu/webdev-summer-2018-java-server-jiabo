(function () {
    var $usernameFld, $newPasswordFld, $confirmNewPasswordFld;
    var $resetBtn;
    var userService = new UserServiceClient();
    $(main);

    function main() {
        $usernameFld = $("#usernameFld");
        $newPasswordFld = $("#newPasswordFld");
        $confirmNewPasswordFld = $("#confirmNewPasswordFld");
        $resetBtn = $("#resetBtn")
            .click(resetPassword);

    }
    function resetPassword() {
        if($newPasswordFld.val() != $confirmNewPasswordFld.val()){
            alert("Two password are different. Please make sure they are the same.");
        } else{
            var user = new User($usernameFld.val(),$newPasswordFld.val(),null,null,null,null,null,null);

            userService
                .resetPassword(user)
                .then(function(response){
                    if(response.status == 200){
                        success();
                    } else{
                        fail();
                    }
                });
        }

    }

    function success(){
        alert("Congratulations, the password has been reset successfully!");
    }

    function fail(){
        alert("Sorry, the username does not exist.");
    }
})();
