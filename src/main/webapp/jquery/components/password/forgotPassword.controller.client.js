(function () {
    var $emailAddressFld;
    var $sendEmailBtn;
    var userService = new UserServiceClient();
    $(main);

    function main() {
        $emailAddressFld = $("#emailAddressFld");
        $sendEmailBtn = $("#sendEmailBtn")
            .click(sendEmail);

    }
    function sendEmail() {
        console.log($emailAddressFld.val());
        var user = new User(null,null,$emailAddressFld.val(),null,null,null,null,null);

        userService
            .sendEmail(user)
            .then(function(response){
                if(response.status == 200){
                    success();
                } else{
                    fail();
                }
            });
    }

    function success(){
        alert("Congratulations, the email has been sent! Check your mailbox!");
    }

    function fail(){
        alert("Sorry, the email does not match any record.");
    }
})();
