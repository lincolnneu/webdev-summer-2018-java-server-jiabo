(function(){
    $(init); // load when the document finishes loading

    var userService = new UserServiceClient();

    var $usernameFld, $phoneFld, $emailFld, $roleFld, $dobFld,$firstNameFld,$lastNameFld;
    var $updateBtn, $logoutBtn;


    function init(){
        // fetch admin by id
        $usernameFld = $("#usernameFld");
        $firstNameFld = $("#firstNameFld");
        $lastNameFld = $("#lastNameFld");
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
        userService
            .getProfileUser()
            .then(renderUser, failFindCurUser);
    }

    function updateProfile(){
        var user = new User($usernameFld.val(), null, $emailFld.val(), $firstNameFld.val(), $lastNameFld.val(), $phoneFld.val(), $roleFld.val(), $dobFld.val());
        userService
            .updateProfile(user)
            .then(function(response){
                if(response.status == 200){
                    alert("Profile update success!");
                } else{
                    alert('Unable to update the profile.');
                }
            });

    }

    function logout(){
        userService
            .logout()
            .then(logOutSuccess);
    }

    function logOutSuccess(){
        location.href="../login/login.template.client.html";

    }

    function findUserById(userId){
        userService.findUserById(userId).then(renderUser);
    }

    function renderUser(user){ // populate data into form
        $usernameFld.val(user.username); // grab to read only input field, change it to new value
        $firstNameFld.val(user.firstName);
        $lastNameFld.val(user.lastName);
        $phoneFld.val(user.phone);
        $emailFld.val(user.email);
        $roleFld.val(user.role);
        $dobFld.val(user.dateOfBirth);
    }

    function failFindCurUser(){
        alert("Please log in to view profile!");
        location.href="../login/login.template.client.html";
    }

})();