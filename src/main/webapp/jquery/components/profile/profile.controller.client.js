(function(){
    $(init); // load when the document finishes loading

    var userService = new UserServiceClient();

    var $usernameFld, $phoneFld, $emailFld, $roleFld, $dobFld;
    var $updateBtn, $logoutBtn;


    function init(){
        // fetch admin by id
        $usernameFld = $("#usernameFld");
        $phoneFld = $("#phoneFld");
        $roleFld = $("#roleFld");
        $dobFld = $("#dobFld");
        $emailFld = $("#emailFld");

        findCurUser();
        $updateBtn = $("#updateBtn")
            .click(updateProfile);

        $logoutBtn = $("#logoutBtn")
            .click(logout);
    }


    function findCurUser(){
        userService.getProfileUser().then(renderUser);
    }

    function updateProfile(){
        var user = new User($usernameFld.val(), null, $emailFld.val(), null, null, $phoneFld.val(), $roleFld.val(), $dobFld.val());

        userService
            .updateProfile(user)
            .then(updateSuccess);
    }

    function logout(){
        userService
            .logout()
            .then(logOutSuccess);
    }


    function updateSuccess(response){
        if(response == null){
            alert('Unable to update the profile.');
        } else{
            alert("Profile update success!");
        }
    }

    function logOutSuccess(response){
        if(response == null){
            alert('Unable to log out.');
        } else{
            alert("Log out success!");
            location.href="../login/login.template.client.html";
        }
    }

    function findUserById(userId){
        userService.findUserById(userId).then(renderUser);
    }

    function renderUser(user){ // populate data into form
        $usernameFld.val(user.username); // grab to read only input field, change it to new value
        $phoneFld.val(user.phone);
        $emailFld.val(user.email);
        $roleFld.val(user.role);
        $dobFld.val(user.dateOfBirth);
    }

})();